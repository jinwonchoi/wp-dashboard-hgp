package com.gencode.issuetool.obj;

public class FileInfo extends Pojo {
	protected long id;
	protected String fileName;
	protected String fileType;
	protected long fileSize;
	protected String fileExt;
	protected String mimeType;
	protected String storageDir;
	protected String fileOrgName;
	protected String fileDesc;
	protected String tagInfo;
	protected String status;
	protected long registerId;
	protected UserInfo registerUserInfo;
	protected String deleteYn;
	protected String updatedDtm;
	protected String createdDtm;	
	
	public FileInfo() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public FileInfo(String fileName, String fileType, long fileSize, String fileExt, String mimeType, String storageDir, String fileOrgName, String fileDesc,
			String tagInfo, String status, UserInfo registerUserInfo) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.mimeType = mimeType;
		this.storageDir = storageDir;
		this.fileOrgName = fileOrgName;
		this.fileDesc = fileDesc;
		this.tagInfo = tagInfo;
		this.status = status;
		this.registerId = registerUserInfo.getId();
		this.registerUserInfo = registerUserInfo;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getStorageDir() {
		return storageDir;
	}
	public void setStorageDir(String storageDir) {
		this.storageDir = storageDir;
	}
	public String getFileOrgName() {
		return fileOrgName;
	}
	public void setFileOrgName(String fileOrgName) {
		this.fileOrgName = fileOrgName;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
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
