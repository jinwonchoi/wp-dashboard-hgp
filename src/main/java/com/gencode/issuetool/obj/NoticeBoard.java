package com.gencode.issuetool.obj;

public class NoticeBoard extends Pojo {
	protected long id;
	protected String title;
	protected String content;
	protected long registerId;
	protected String postType;
	protected int postLevel;
	protected String contentType;
	protected long refId;
	protected String deleteYn;
	protected int commentCnt;
	protected long readCnt;
	protected String updatedDtm;
	protected String createdDtm;

	public NoticeBoard() {
		// TODO Auto-generated constructor stub
	}

	public NoticeBoard(String title, String content, long registerId, String postType, int postLevel, String contentType, long refId) {
		super();
		this.title = title;
		this.content = content;
		this.registerId = registerId;
		this.postType = postType;
		this.postLevel = postLevel;
		this.contentType = contentType;
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

	public long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(long registerId) {
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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

	public long getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(long readCnt) {
		this.readCnt = readCnt;
	}

}
