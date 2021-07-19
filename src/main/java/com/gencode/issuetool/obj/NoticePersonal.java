package com.gencode.issuetool.obj;

public class NoticePersonal extends Pojo {
	protected long noticeId;
	protected long userId;

	public NoticePersonal() {
		super();
	}

	public NoticePersonal(long noticeId, long userId) {
		super();
		this.noticeId = noticeId;
		this.userId = userId;
	}

	public long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
