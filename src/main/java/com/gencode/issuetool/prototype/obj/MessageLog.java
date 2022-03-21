package com.gencode.issuetool.prototype.obj;

public class MessageLog extends Pojo {
	protected long id;
	protected long chatId;
	protected long senderId;
	protected long receiverId;
	protected String status;
	protected String message;
	protected String updatedDtm;
	protected String createdDtm;
	
	public MessageLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageLog(long chatId, long senderId, long receiverId, String status, String message) {
		super();
		this.chatId = chatId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.status = status;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
