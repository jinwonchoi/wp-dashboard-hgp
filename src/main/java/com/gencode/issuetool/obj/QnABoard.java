package com.gencode.issuetool.obj;

public class QnABoard {
	protected long id;
	protected String title;
	protected String content;
	protected String registerId;
	protected String postType;
	protected int postLevel;
	protected long refId;
	protected String deleteYn;
	protected int commentCnt;
	protected String updatedDtm;
	protected String createdDtm;

	public QnABoard() {
		// TODO Auto-generated constructor stub
	}

	public QnABoard(String title, String content, String registerId, String postType, int postLevel, long refId) {
		super();
		this.title = title;
		this.content = content;
		this.registerId = registerId;
		this.postType = postType;
		this.postLevel= postLevel;
		this.refId = refId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
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

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	public int getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(int postLevel) {
		this.postLevel = postLevel;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	
}
