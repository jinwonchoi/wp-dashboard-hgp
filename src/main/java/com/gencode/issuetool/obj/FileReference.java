package com.gencode.issuetool.obj;

public class FileReference extends Pojo {
		
	protected long id;
	protected long fileId;
	protected long refId;
	protected String refType;
	protected String refDesc;
	protected String storageDir;
	protected String tagInfo;
	protected String status;
	protected long registerId;
	protected UserInfo registerUserInfo;
	protected String deleteYn;
	protected String updatedDtm;
	protected String createdDtm;
		
	public FileReference() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public FileReference(long fileId, long refId, String refType, String refDesc, String storageDir, String tagInfo, String status,
			long registerId) {
		super();
		this.fileId = fileId;
		this.refId = refId;
		this.refType = refType;
		this.refDesc = refDesc;
		this.storageDir = storageDir;
		this.tagInfo = tagInfo;
		this.status = status;
		this.registerId = registerId;
	}

	public FileReference(long fileId, long refId, String refType, String refDesc, String storageDir, String tagInfo, String status,
			UserInfo registerUserInfo) {
		super();
		this.fileId = fileId;
		this.refId = refId;
		this.refType = refType;
		this.refDesc = refDesc;
		this.storageDir = storageDir;
		this.tagInfo = tagInfo;
		this.status = status;
		this.registerId = registerUserInfo.getId();
		this.registerUserInfo = registerUserInfo;
	}
	public FileReference(long fileId, long refId) {
		super();
		this.fileId = fileId;
		this.refId = refId;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public long getRefId() {
		return refId;
	}
	public void setRefId(long refId) {
		this.refId = refId;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getRefDesc() {
		return refDesc;
	}
	public void setRefDesc(String refDesc) {
		this.refDesc = refDesc;
	}
	public String getStorageDir() {
		return storageDir;
	}
	public void setStorageDir(String storageDir) {
		this.storageDir = storageDir;
	}
	public String getTagInfo() {
		return tagInfo;
	}
	public void setTagInfo(String tagInfo) {
		this.tagInfo = tagInfo;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
