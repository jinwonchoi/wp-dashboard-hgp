package com.gencode.issuetool.prototype.obj;

public class ChatInfo extends Pojo {
	protected long id;
	protected long senderId;
	protected long receiverId;
	protected long lastMessageId;
	protected String lastMessage;
	protected long unreadCnt;
	protected String status;
	protected String updatedDtm;
	protected String createdDtm;
	
	
	
	public ChatInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ChatInfo(long senderId, long receiverId, long lastMessageId, String lastMessage, long unreadCnt,
			String status) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.lastMessageId = lastMessageId;
		this.lastMessage = lastMessage;
		this.unreadCnt = unreadCnt;
		this.status = status;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}
	public long getLastMessageId() {
		return lastMessageId;
	}
	public void setLastMessageId(long lastMessageId) {
		this.lastMessageId = lastMessageId;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	public long getUnreadCnt() {
		return unreadCnt;
	}
	public void setUnreadCnt(long unreadCnt) {
		this.unreadCnt = unreadCnt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdatedDtm() {
		return updatedDtm;
	}
	public void setUpdatedDtm(String updatedDtm) {
		this.updatedDtm = updatedDtm;
	}
	public String getCreatedDtm() {
		return createdDtm;
	}
	public void setCreatedDtm(String createdDtm) {
		this.createdDtm = createdDtm;
	}


}
