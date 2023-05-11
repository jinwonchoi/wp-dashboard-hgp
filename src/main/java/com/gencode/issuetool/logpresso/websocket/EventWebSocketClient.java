package com.gencode.issuetool.logpresso.websocket;

import java.net.URI;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This example demonstrates how to create a websocket connection to a server.
 * Only the most important callbacks are overloaded.
 */
public class EventWebSocketClient extends WebSocketClient {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private MessageHandler messageHandler;
	private static boolean isOpened = false;

	public EventWebSocketClient(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}

	public EventWebSocketClient(URI serverURI) {
		super(serverURI);
	}

	public EventWebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
		super(serverUri, httpHeaders);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		logger.info("opened connection");
		//isOpened = true;
		// if you plan to refuse connection based on ip or httpfields overload:
		// onWebsocketHandshakeReceivedAsClient
	}

	@Override
	public void onMessage(String message) {
		logger.info("received: " + message);
		if (this.messageHandler != null) {
			this.messageHandler.handleMessage(message);
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		// The close codes are documented in class org.java_websocket.framing.CloseFrame
		isOpened = false;

		logger.info(
				"Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
		if (this.messageHandler != null) {
			this.messageHandler.handleSessionClose();
		}
		
	}

	@Override
	public void onError(Exception ex) {
		logger.error("error", ex);
		// if the error is fatal then onClose will be called additionally
	}

	public void healthCheck() {
		logger.info("I'M ALIVE!!");
	}
	
	public boolean getOpenStatus() {
		return isOpened;
	}
	
	public void addMessageHandler(MessageHandler msgHandler) {
		this.messageHandler = msgHandler;
	}

	public static interface MessageHandler {
		public void handleMessage(String message);
		public void handleSessionClose();
	}
}
