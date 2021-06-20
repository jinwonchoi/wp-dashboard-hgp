package com.gencode.issuetool.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;



//public class JwtTokenFilter extends GenericFilterBean {
public class JwtTokenFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @Override
//    public void doFilter(HttpServletRequest  req, HttpServletResponse res, FilterChain filterChain)
//        throws IOException, ServletException {
//    	try {
//	        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
//	        log.info("token:"+token);
//	        if (token != null && jwtTokenProvider.validateToken(token, res)) {
//	            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
//	            log.info("auth:"+auth);
//	            SecurityContextHolder.getContext().setAuthentication(auth);
//	        }
//		} catch (ExpiredJwtException ex) {
//
//			String isRefreshToken = ((HttpServletRequest)req).getHeader("isRefreshToken");
//			String requestURL = ((HttpServletRequest)req).getRequestURL().toString();
//			// allow for Refresh Token creation if following conditions are true.
//			if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshToken")) {
//				allowForRefreshToken(ex, (HttpServletRequest)req);
//			} else
//				req.setAttribute("exception", ex);
//
//		} catch (BadCredentialsException ex) {
//			req.setAttribute("exception", ex);
//		}
//        log.info(req.toString());
//    	filterChain.doFilter(req, res);
//    }
    /**
     * 토큰 리프레시 기능 참고: 
     *  https://www.javainuse.com/webseries/spring-security-jwt/chap7 
     */
	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());

	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
    	try {
	        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
	        log.info("token:"+token);
	        if (token != null && jwtTokenProvider.validateToken(token, res)) {
	            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
	            //log.info("auth:"+auth);
	            SecurityContextHolder.getContext().setAuthentication(auth);
	        }
		} catch (ExpiredJwtException ex) {
			String requestURL = req.getRequestURL().toString();
			// allow for Refresh Token creation if following conditions are true.
			if (requestURL.contains("refreshToken")) {
				allowForRefreshToken(ex, (HttpServletRequest)req);
			} else
				req.setAttribute("exception", ex);

		} catch (BadCredentialsException ex) {
			req.setAttribute("exception", ex);
		}
        log.info(req.toString());
    	filterChain.doFilter(req, res);

	}
}