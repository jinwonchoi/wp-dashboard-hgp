package com.gencode.issuetool.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.gencode.issuetool.config.JwtTokenProvider;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.service.MyUserDetailsService;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {
	

    /**
     * 握手前拦截，往attributes存储用户信息，后续握手连接时使用
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest req, ServerHttpResponse res, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        boolean result = false;
//        HttpSession session =getSession(req);
//        HttpHeaders headers = req.getHeaders();
//        if (session != null){
//        	String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
//        	if (token != null && jwtTokenProvider.validateToken(token, (ServletResponse)res)) {
//        		UserInfo userInfo = jwtTokenProvider.getUserInfo(token);
//        		attributes.put("user", userInfo);
//        		result = true;
//        	}
//        }
        result = true;
        return result;
    }
    @Nullable
    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession();
        }
        return null;
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}
