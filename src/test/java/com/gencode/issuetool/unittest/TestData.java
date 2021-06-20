package com.gencode.issuetool.unittest;

import org.apache.commons.lang3.RandomStringUtils;

import com.gencode.issuetool.obj.UserInfo;

public class TestData {

	public final String URL = "http://localhost:8082/wpdashboard";
	//public final String URL = "http://13.125.27.90:8090/issuetool";
	
	public UserInfo getUserInfoAdmin() {
		return new UserInfo("admin","passwd");
	}
	public UserInfo getUserInfoManager() {
		return new UserInfo("manager","passwd");
	}
	public UserInfo getUserInfoAgent() {
		return new UserInfo("agent01","passwd");
	}
	
	public String getRandomPhoneNumber() {
    	return RandomStringUtils.randomNumeric(10);
    }

}
