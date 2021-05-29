package com.gencode.issuetool.obj;

import java.util.List;

import com.gencode.issuetool.io.PageResultObj;

public class NoticeBoardEx extends NoticeBoard {
	protected long addFileCnt;
	protected String addFileType;
	protected UserInfo registerUserInfo;
	//protected PageResultObj<List<BoardComment>> boardComments;
	
	public NoticeBoardEx(String title, String content, UserInfo registerUserInfo, String postType, int postLevel, long refId) {
		super();
		this.title = title;
		this.content = content;
		this.registerId = registerUserInfo.id;
		this.registerUserInfo = registerUserInfo;
		this.postType = postType;
		this.postLevel= postLevel;
		this.refId = refId;
	}
	public NoticeBoardEx() {
		// TODO Auto-generated constructor stub
	}
	public UserInfo getRegisterUserInfo() {
		return registerUserInfo;
	}
	public void setRegisterUserInfo(UserInfo registerUserInfo) {
		this.registerUserInfo = registerUserInfo;
	}
	public long getAddFileCnt() {
		return addFileCnt;
	}
	public void setAddFileCnt(long addFileCnt) {
		this.addFileCnt = addFileCnt;
	}
	public String getAddFileType() {
		return addFileType;
	}
	public void setAddFileType(String addFileType) {
		this.addFileType = addFileType;
	}
	
//	public PageResultObj<List<BoardComment>> getBoardComments() {
//		return boardComments;
//	}
//	public void setBoardComments(PageResultObj<List<BoardComment>> boardComments) {
//		this.boardComments = boardComments;
//	}
}
