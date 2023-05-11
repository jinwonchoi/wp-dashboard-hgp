package com.gencode.issuetool.config;

import java.io.IOException;
import java.lang.annotation.Target;
import java.net.URI;
import java.util.HashMap;

import org.java_websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.gencode.issuetool.logpresso.websocket.EventWebSocketClient;
import com.gencode.issuetool.logpresso.websocket.LogpressoWebSocketProvider;

@Configuration
public class WebSocketClientConfig {
	private final static Logger logger = LoggerFactory.getLogger(WebSocketClientConfig.class);
	private static boolean isSocketConnected = false;
	private static final String URL_WS_SERVER = "ws://dt.rozetatech.com:3000/hg/ws/event";
	private static final String apikey = "K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6"; // 媛� 湲곌�蹂� 諛쒓툒�맂 APIKEY濡� 援먯껜

//	@Bean
//	public LogpressoWebSocketProvider logpressoWebSocketProvider() {
//		LogpressoWebSocketProvider logpressoWebSocketProvider = new LogpressoWebSocketProvider();
//		logpressoWebSocketProvider.openConnection();
//		return logpressoWebSocketProvider;
//	}

}
