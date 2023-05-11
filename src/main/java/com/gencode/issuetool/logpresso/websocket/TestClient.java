package com.gencode.issuetool.logpresso.websocket;

import java.net.URI;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClient {

	private static Logger logger = LoggerFactory.getLogger(TestClient.class);
	private static EventWebSocketClient wsc;
	private static boolean isSocketConnected = false;
	private static final String URL_WS_SERVER = "ws://dt.rozetatech.com:3000/hg/ws/event";
	private static final String apikey = "K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6"; // 媛� 湲곌�蹂� 諛쒓툒�맂 APIKEY濡� 援먯껜

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//	
//		initWsClient(URL_WS_SERVER, apikey);
//		
//	}

	/**
	 * WebSocket �꽌踰� �뿰寃� initiator
	 * @param url_ws_server WebSocket �꽌踰� �뿰寃� url
	 * @param apikey 媛� 湲곌�蹂� 諛쒓툒�맂 api key 
	 */
	public static void initWsClient(String url_ws_server, String apikey) {
		try {
			
			//event Web Socket Server �젒�냽 url
			String EVENT_WEBSOCKET_SERVER = url_ws_server;
			
			//�씤利앹슜 apikey 媛�
			 String APIKEY = apikey;
			 
			 if(APIKEY==null || APIKEY.length()==0) {
				 throw new Exception("There is no apikey...");
			 }
			 
			/**
			 *  event �닔�떊 web socket �뿰寃�
			 */
			
			URI ws_uri = new URI(EVENT_WEBSOCKET_SERVER);
			HashMap<String, String> headers = new HashMap();
			headers.put("apikey", APIKEY);
			
			//websocket �꽌踰� �뿰寃�
			wsc = new EventWebSocketClient(ws_uri, headers);

			//message handler interface 援ы쁽
			wsc.addMessageHandler(new EventWebSocketClient.MessageHandler() {
				
				public void handleSessionClose() {
					// TODO Auto-generated method stub
					handleClose();
				}
				
				public void handleMessage(String message) {
					// TODO Auto-generated method stub
					handleMsg(message);
				}
			});

			if (wsc.connectBlocking()) {

				isSocketConnected = true;
			}

		} catch (Exception ex) {
			logger.error("error", ex);
		}
	}

	/**
	 * websocket 硫붿떆吏� 泥섎━ �빖�뱾�윭 �븿�닔
	 * @param msg
	 */
	public static void handleMsg(String msg) {
		logger.info("invoked handleMsg()");
		logger.info("msg -> [" + msg + "]");
	}

	/**
	 * websocket �꽌踰� �뿰寃� 醫낅즺�떆 �옱�뿰寃� �떆�룄 �븿�닔
	 */
		public static void handleClose() {
			
			logger.info("invoked handleClose()");
			
			isSocketConnected = false;
			wsc = null;

			while (true) { // Server���쓽 �뿰寃� �걡�뼱吏� �떆 2珥덈쭏�떎 �옱�뿰寃� �떆�룄

				try {

					Thread.sleep(2000);

					if (isSocketConnected == false) {

						initWsClient(URL_WS_SERVER, apikey);

					} else {
						break;
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error("error", e);
				} catch (Exception e) {
					logger.error("error", e);
				}
			}
		}

}
