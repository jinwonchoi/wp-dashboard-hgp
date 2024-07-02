package com.gencode.issuetool.logpresso.websocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gencode.issuetool.dao.TagDvcPushEventHistDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.FakeDataUtil;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.logpresso.io.DvcEventReqObj;
import com.gencode.issuetool.obj.TagDvcPushEvent;
import com.gencode.issuetool.service.CacheMapManager;
import com.gencode.issuetool.service.PushService;

@Service
public class LogpressoWebSocketProvider {
	
	private static Logger logger = LoggerFactory.getLogger(LogpressoWebSocketProvider.class);
	private EventWebSocketClient wsc;
	@Value("${logpresso.api.key:K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6}")
	String apiKey;
	@Value("${logpresso.websocket.url:ws://dt.rozetatech.com:3000/hg/ws/event}")
	String apiUrl;

	boolean isConnected=false;

	@Autowired private TagDvcPushEventHistDao tagDvcPushEventHistDao;
	@Autowired
	private PushService pushService;
	@Autowired
	private CacheMapManager cacheMapManager;

	public void openConnection() {
		try {
			URI ws_uri = new URI(apiUrl);
			HashMap<String, String> headers = new HashMap();
			headers.put("apikey", apiKey);
		
			//websocket �꽌踰� �뿰寃�
			wsc = new EventWebSocketClient(ws_uri, headers);

			//message handler interface 援ы쁽
			wsc.addMessageHandler(new EventWebSocketClient.MessageHandler() {
				
				public void handleSessionClose() {
					logger.info("LOGPRESSO WEBSOCKET SESSION CLOSED");
					// TODO Auto-generated method stub
					//handleClose();
					closeConnection();
					openConnection();
				}
				
				public void handleMessage(String message) {
					try {
						logger.info("LOGPRESSO WEBSOCKET MESSAGE RECEIVED:"+message);
						TagDvcPushEvent tagDvcPushEvent = 
								FakeDataUtil.getObjTabDvcPushEvent(cacheMapManager, message);
						tagDvcPushEventHistDao.register(tagDvcPushEvent);
						pushService.sendMsgAll(Constant.PUSH_TAG_TAG_DVC_PUSH_EVENT_ADD.get(), tagDvcPushEvent);
					} catch (Exception e) {
						logger.error("LOGPRESSO WEBSOCKET HANDLE FAILED",e);
					}
					
				}
			});			
			isConnected = wsc.connectBlocking();
			logger.info("LOGPRESSO WEBSOCKET CONNECTED");
		} catch (Exception e) {
			logger.error("LOGPRESSO WEBSOCKET FAILED",e);
		}
		
	}
	
	public boolean isConnected() {
		return this.isConnected;
	}
	
	public void closeConnection() {
		this.isConnected=false;
		if (wsc!=null && !wsc.isClosed()) {
			try {
				wsc.closeBlocking();
				logger.info("LOGPRESSO WEBSOCKET CLOSED");
			} catch (InterruptedException e) {
				logger.info("LOGPRESSO WEBSOCKET CLOSING FAILED");
			}
			wsc=null;
		}
	}
}
