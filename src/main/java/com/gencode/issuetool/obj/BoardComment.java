package com.gencode.issuetool.obj;

import java.time.LocalDateTime;

public class BoardComment extends Pojo {
	protected long id;
	protected long level;
	protected String sortKey;
	protected String content;
	protected long registerId;
	protected UserInfo registerUserInfo;
	protected long refBoardId;
	protected String boardType;
	protected long refCommentId;
	protected String deleteYn;
	protected String updatedDtm;
	protected String createdDtm;

	public BoardComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardComment(long level, String sortKey, String content, long registerId, UserInfo registerUserInfo, long refBoardId,
			String boardType, long refCommentId) {
		super();
		this.level = level;
		this.sortKey = sortKey;
		this.content = content;
		this.registerId = registerId;
		this.registerUserInfo = registerUserInfo;
		this.refBoardId = refBoardId;
		this.boardType = boardType;
		this.refCommentId = refCommentId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}
	
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getRegisterId() {
		return registerId;
	}
	public void setRegisterId(long registerId) {
		this.registerId = registerId;
	}
	public UserInfo getRegisterUserInfo() {
		return registerUserInfo;
	}
	public void setRegisterUserInfo(UserInfo registerUserInfo) {
		this.registerUserInfo = registerUserInfo;
	}
	public long getRefBoardId() {
		return refBoardId;
	}
	public void setRefBoardId(long refBoardId) {
		this.refBoardId = refBoardId;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public long getRefCommentId() {
		return refCommentId;
	}
	public void setRefCommentId(long refCommentId) {
		this.refCommentId = refCommentId;
	}	
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
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

