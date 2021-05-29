package com.gencode.issuetool.obj;

import java.time.LocalDateTime;

public class LoginHistory {
	protected long id;
	protected long userId;
	protected String loginDtm;
	protected String logoutDtm;
	protected String logoutType;

	public LoginHistory() {
		// TODO Auto-generated constructor stub
	}

	public LoginHistory(long userId) {
		super();
		this.userId = userId;
	}

	public LoginHistory(long userId, String loginDtm, String logoutDtm, String logoutType) {
		super();
		this.userId = userId;
		this.loginDtm = loginDtm;
		this.logoutDtm = logoutDtm;
		this.logoutType = logoutType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLoginDtm() {
		return loginDtm;
	}

	public void setLoginDtm(String loginDtm) {
		this.loginDtm = loginDtm;
	}

	public String getLogoutDtm() {
		return logoutDtm;
	}

	public void setLogoutDtm(String logoutDtm) {
		this.logoutDtm = logoutDtm;
	}

	public String getLogoutType() {
		return logoutType;
	}

	public void setLogoutType(String logoutType) {
		this.logoutType = logoutType;
	}

}
