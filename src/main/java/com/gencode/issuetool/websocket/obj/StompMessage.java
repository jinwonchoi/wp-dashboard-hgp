package com.gencode.issuetool.websocket.obj;

import java.io.Serializable;

import com.gencode.issuetool.obj.Pojo;

/**
 **/
public class StompMessage extends Pojo implements Serializable {
	private String username;
	private String content;
	private String sendTime;
	private String receiver;

	public StompMessage() {
		super();
	}
	
	public StompMessage(String username, String content, String sendTime, String receiver) {
		super();
		this.username = username;
		this.content = content;
		this.sendTime = sendTime;
		this.receiver = receiver;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
}
