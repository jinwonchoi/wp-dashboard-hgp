package com.gencode.issuetool.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.TransportRequest;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import com.gencode.issuetool.io.StompObj;
import com.gencode.issuetool.util.JsonUtils;
import com.gencode.issuetool.websocket.obj.StompMessage;

import java.lang.reflect.Type;


import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertNotNull;


//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-dev.properties")
public class MyStompClient {
	
	private static final Logger logger = LoggerFactory.getLogger(MyStompClient.class);
    String URL = "ws://{host}:{port}/issuetool/any-socket";
    String HOST = "localhost";
    String QUE_END_POINT = "/queue/%s/chat";
    String APP_END_POINT = "/app/sendmsg";
    int PORT = 8090;
    String userId = null;
	String token = null;

	String strLastReceivedMsg;
    public MyStompClient(String userId, String token) {
    	this.userId = userId;
    	this.token = token;
    }
    
    public boolean isMessageReceived(String msg) {
    	return strLastReceivedMsg.equals(msg);
    }
	
    ListenableFuture<StompSession> connect() {

        Transport webSocketTransport = new WebSocketTransport(new MyWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Bearer "+ token );
        
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

		return stompClient.connect(URL, headers, new MyHandler(), HOST, PORT);
    }

    void subscribe(StompSession stompSession, String queueId, CompletableFuture<String> future, String endStr) throws ExecutionException, InterruptedException {
        stompSession.subscribe(String.format(QUE_END_POINT, queueId), new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
            	   return byte[].class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object o) {
            	try {
                	logger.info(o.toString());
                    StompObj retMsg=JsonUtils.toObject((new String((byte[]) o)), StompObj.class);
                    logger.info(String.format("Received message [%s]:[%s]=>[%s][%s]", queueId, endStr,retMsg.getType(), retMsg.getPayload()));
                	future.obtrudeValue(JsonUtils.toJson(retMsg));
            	} catch(Exception e) {
            		logger.error("dadsfasd", e);
            		throw e;
            	}
//            	StompMessage retMsg = JsonUtils.toObject((new String((byte[]) o)), StompMessage.class);
//                logger.info(String.format("Received message [%s]:[%s]=>[%s]", queueId, endStr, retMsg.getContent()));
                //future.complete(retMsg.getContent());
            }
        });
    }
    
    void _sendmsg(StompSession stompSession, StompMessage message) {
        stompSession.send(APP_END_POINT, JsonUtils.toJson(message).getBytes());
    }

    private class MyHandler extends StompSessionHandlerAdapter {
    	@Override
    	public void handleFrame(StompHeaders headers, @Nullable Object payload) {
    	}
    	@Override
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            logger.info("Now connected");
        }
    }
    
    private class MyWebSocketClient extends StandardWebSocketClient {
    	@Override
    	protected ListenableFuture<WebSocketSession> doHandshakeInternal(WebSocketHandler webSocketHandler,
    			HttpHeaders headers, final URI uri, List<String> protocols,
    			List<WebSocketExtension> extensions, Map<String, Object> attributes) {
    		attributes = new HashMap<String, Object>();
    		attributes.putIfAbsent("user", "jinnon12");
    		return super.doHandshakeInternal(webSocketHandler, headers, uri, protocols, extensions, attributes);
    	}
    }
    
    public StompSession getSession() throws InterruptedException, ExecutionException {
        ListenableFuture<StompSession> f = connect();
        return  f.get();
    }
    
    public CompletableFuture<String> subscribeQueue(StompSession stompSession, String queueId, String endStr) throws ExecutionException, InterruptedException {
    	CompletableFuture<String> completableFuture = new CompletableFuture<String>();
    	this.subscribe(stompSession, queueId, completableFuture, endStr);
    	return completableFuture;
    }
	
    public void sendMsg(StompSession stompSession, String destQue, String message) {
        StompMessage msg = new StompMessage(this.userId, message,"", destQue);
        stompSession.send(APP_END_POINT, JsonUtils.toJson(msg).getBytes());
    }
    
    @Test
    public void runSendHello() throws InterruptedException, ExecutionException, TimeoutException {
    	String queueId = "jinnon-adfasdf";
    	String queueId2 = "jinnon-adfasdfaaaa";

    	String message1st = "Hello!!!";
    	String message2nd = "2ndMessage";
        StompMessage msg = new StompMessage(userId, message1st,"", queueId);
        StompMessage msg2 = new StompMessage(userId,message2nd,"", queueId2);

        /** 1st queue 등록 */
        StompSession stompSession = getSession();
        
        CompletableFuture<String> completableFuture1 = subscribeQueue(stompSession, queueId, message1st);

        /** 2nd queue 등록 */
        StompSession stompSession2 = getSession();
        CompletableFuture<String> completableFuture2 = subscribeQueue(stompSession2, queueId2, message2nd);

        
        logger.info("Sending hello message" + stompSession);
        _sendmsg(stompSession, msg);

        logger.info("Sending hello message" + stompSession);
        _sendmsg(stompSession, msg2);
        
        if (completableFuture1.get(3,SECONDS).equals(message1st)) {
            stompSession.disconnect();
            logger.info("stompSession.disconnect()");
        }
        if (completableFuture2.get(3,SECONDS).equals(message2nd)) {
            stompSession2.disconnect();
            logger.info("stompSession2.disconnect()");
        }
        Thread.sleep(5000);
    }   
    
}
