package com.gencode.issuetool.obj;

import java.util.ArrayList;
import java.util.List;

import com.gencode.issuetool.io.PageResultObj;

public class NoticeBoardWithFileList extends NoticeBoardEx {
	protected List<FileInfo> fileIdListToAdd = new ArrayList<FileInfo>();
	protected List<FileInfo> fileIdListToDelete = new ArrayList<FileInfo>();
	protected List<FileInfo> attachedFileList = new ArrayList<FileInfo>();
	protected List<FileInfo> embededFileList = new ArrayList<FileInfo>();
	
	//protected PageResultObj<List<BoardComment>> boardComments;
	public NoticeBoardWithFileList() {
		// TODO Auto-generated constructor stub
	}

	public NoticeBoardWithFileList(NoticeBoardEx ex) {
		this.setId(ex.getId());
		this.setTitle(ex.getTitle());
		this.setContent(ex.getContent());
		this.setRegisterId(ex.getRegisterId());
		this.setRegisterUserInfo(ex.getRegisterUserInfo());
		this.setPostType(ex.getPostType());
		this.setPostLevel(ex.getPostLevel());
		this.setContentType(ex.getContentType());
		this.setRefId(ex.getRefId());
		this.setDeleteYn(ex.getDeleteYn());
		this.setCommentCnt(ex.getCommentCnt());
		this.setReadCnt(ex.getReadCnt());
		this.setAddFileCnt(ex.getAddFileCnt());
		this.setAddFileType(ex.getAddFileType());
		this.setUpdatedDtm(ex.getUpdatedDtm());
		this.setCreatedDtm(ex.getCreatedDtm());
	}

	public List<FileInfo> getFileIdListToAdd() {
		return fileIdListToAdd;
	}

	public void setFileIdListToAdd(List<FileInfo> fileIdListToAdd) {
		this.fileIdListToAdd = fileIdListToAdd;
	}

	public List<FileInfo> getFileIdListToDelete() {
		return fileIdListToDelete;
	}

	public void setFileIdListToDelete(List<FileInfo> fileIdListToDelete) {
		this.fileIdListToDelete = fileIdListToDelete;
	}

	public List<FileInfo> getAttachedFileList() {
		return attachedFileList;
	}

	public void setAttachedFileList(List<FileInfo> attachedFileList) {
		this.attachedFileList = attachedFileList;
	}

	public List<FileInfo> getEmbededFileList() {
		return embededFileList;
	}

	public void setEmbededFileList(List<FileInfo> embededFileList) {
		this.embededFileList = embededFileList;
	}	
}
