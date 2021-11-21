package com.gencode.issuetool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.gencode.issuetool.io.StompObj;
import com.gencode.issuetool.util.JsonUtils;

@Service
public class PushService {
	private final static Logger logger = LoggerFactory.getLogger(PushService.class);
    @Autowired
    private SimpMessagingTemplate template;

    final String destination = "/chat";
    
    public <T> void sendMsg(String receiver, String msgType,  T payload) {
    	template.convertAndSendToUser(receiver,  destination, JsonUtils.toJson(new StompObj(msgType, JsonUtils.toJson(payload))));
    }
    public <T> void sendMsg(String receiver, String msgType,  String payload) {
    	template.convertAndSendToUser(receiver,  destination, JsonUtils.toJson(new StompObj(msgType, payload)));
    }
    public <T> void sendMsgAll(String msgType,  T payload) {
    	sendMsg("all", msgType, payload);
    }
}
