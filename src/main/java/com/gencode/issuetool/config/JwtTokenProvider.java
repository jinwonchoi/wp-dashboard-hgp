package com.gencode.issuetool.config;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.service.MyUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	private final static Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMilliseconds = 3600000; // 1h

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@PostConstruct
	protected void init() {
	    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken(String username, String role) {
	    Claims claims = Jwts.claims().setSubject(username);
	    claims.put("roles", role);
	    Date now = new Date();
	    Date validity = new Date(now.getTime() + validityInMilliseconds);
	    return Jwts.builder()//
	        .setClaims(claims)//
	        .setIssuedAt(now)//
	        .setExpiration(validity)//
	        .signWith(SignatureAlgorithm.HS256, secretKey)//
	        .compact();
	}
	
	public Authentication getAuthentication(String token) {
	    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
	    return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
	}
	
	public UserInfo getUserInfo(String token) {
	    Optional<UserInfo> optUserInfo = this.userDetailsService.load(getUsername(token));
	    return optUserInfo.get();
	}

	public String getUsername(String token) {
	    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest req) {
	    String bearerToken = req.getHeader("Authorization");
	    String userAgent = req.getHeader("user-agent");
	    String token = req.getParameter("token");
	    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	        return bearerToken.substring(7, bearerToken.length());
	    } else if (token != null){
	    	return req.getParameter("token");
	    }
	    return null;
	}
	
	public String resolveToken(ServerHttpRequest req) {
	    String bearerToken = req.getHeaders().getFirst("Authorization");
	    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	        return bearerToken.substring(7, bearerToken.length());
	    }
	    return null;
	}
	
	public boolean validateToken(String token,ServletResponse res) {
	    try {
	        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
	        if (claims.getBody().getExpiration().before(new Date())) {
	            return false;
	        }
	        return true;
	        /*
	         * UnsupportedJwtException - if the claimsJws argument does not represent an Claims JWS 
	         * MalformedJwtException - if the claimsJws string is not a valid JWS 
	         * SignatureException - if the claimsJws JWS signature validation fails
	         * ExpiredJwtException - if the specified JWT is a Claims JWT and the Claims has an expiration time
	         *                        before the time this method is invoked.
	         * IllegalArgumentException - if the claimsJws string is null or empty or only whitespace
	         */
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
            //res.setContentType(type);
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }   
//	        
//	    } catch (JwtException | IllegalArgumentException e) {
//	        throw new JwtException("Expired or invalid JWT token");
//	    }
	    return false;
	}
}
