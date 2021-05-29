package com.gencode.issuetool.obj;

public class PasswdChange extends Pojo {
	protected long userId;
	protected String oldPassword;
	protected String newPassword;
	
	public PasswdChange() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public PasswdChange(long userId, String oldPassword, String newPassword) {
		super();
		this.userId = userId;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}	
}
