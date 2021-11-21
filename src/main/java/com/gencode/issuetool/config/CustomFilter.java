package com.gencode.issuetool.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * https://www.techiedelight.com/add-custom-header-to-all-responses-spring-boot/
 * 
 * @author jinno
 *
 */
@Component
public class CustomFilter implements Filter {
	@Value("${vuejs.version}") String vuejsVersion;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
 
        /**
         * https://stackoverflow.com/questions/66985738/i-cant-get-header-from-backend-in-vuejs
         */
        res.addHeader("Access-Control-Expose-Headers", "Vuejs-Version");
        res.addHeader("Vuejs-Version", vuejsVersion);
        chain.doFilter(req, res);
	}

}
