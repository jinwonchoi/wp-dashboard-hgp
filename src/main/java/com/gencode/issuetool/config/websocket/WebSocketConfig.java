/**=========================================================================================
<overview>웹소켓 요청시 인증처리 및 웹소켓 전달자 지정기능
  </overview>
==========================================================================================*/
package com.gencode.issuetool.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.gencode.issuetool.config.JwtTokenProvider;
import com.gencode.issuetool.dao.LoginUserDao;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.websocket.obj.UserPrincipal;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin(origins = "${cors_url_stomp}")
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);
    @Autowired
    private MyHandshakeInterceptor myHandshakeInterceptor;

    @Autowired
    LoginUserDao loginUserDao;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/any-socket").addInterceptors(myHandshakeInterceptor).setHandshakeHandler(new DefaultHandshakeHandler() {
            @Nullable
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            	String token = jwtTokenProvider.resolveToken(request);
            	UserInfo userInfo = null;
            	if (token != null && jwtTokenProvider.validateToken(token, null)) {
            		userInfo = jwtTokenProvider.getUserInfo(token);
            	}
            	log.info(userInfo.toString());
            	return new UserPrincipal(userInfo);
            }
        })
        .setAllowedOrigins("*")
        .withSockJS();
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {

        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
        messageBrokerRegistry.enableSimpleBroker("/queue", "/topic");
        //指定用户频道前缀
        messageBrokerRegistry.setUserDestinationPrefix("/queue");
    }

    @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        String agentId = session.getPrincipal().getName();
                        log.info("online:" + agentId);
                        loginUserDao.register(agentId);
                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
                            throws Exception {
                        String agentId = session.getPrincipal().getName();
                        log.info("offline:" + agentId);
                        loginUserDao.delete(agentId);
                        super.afterConnectionClosed(session, closeStatus);
                    }
                };
            }
        });
    }
}
