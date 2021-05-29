package com.gencode.issuetool.obj;

public class AuthUserInfo extends Pojo {

	protected UserInfo userInfo;
	protected String token;
	
	public AuthUserInfo(UserInfo userInfo, String token) {
		this.userInfo = userInfo;
		this.token = token;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
