package com.gencode.issuetool.obj;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gencode.issuetool.etc.Utils;

public class UserInfo extends Pojo {

	protected long id;
	protected String loginId;
	protected String level;
	protected String role;
	protected String position;
	protected String groupId;
	protected String userName;
	protected String userEmail;
	protected String mobileNo;
	protected String phoneNo;
	protected String passwd;
	protected String authKey;
	protected String useYn;
	protected String passwdUpdateDate;
	protected String userProfile;
	protected String userStatus;
	protected String profileUrl;
	protected String avatarUrl;
	protected String accessToken;
	protected long noticeId;
	protected String registeredDtm;
	protected String updatedDtm;
	protected String createdDtm;
	
	public UserInfo() {}
	
	public UserInfo(String loginId, String passwd) {
		super();
		this.loginId = loginId;
		this.passwd = passwd;
	}
	
	public UserInfo(String loginId,String level,String role,String position,String groupId,
			String userName,String userEmail,String mobileNo,String phoneNo,String passwd,
			String authKey,String useYn,String passwdUpdateDate,String userProfile,
			String userStatus,String profileUrl, String avatarUrl, String accessToken) {
		super();
		this.loginId=           loginId;
		this.level=            level;
		this.role=             role;
		this.position=          position;
		this.groupId=           groupId;
		this.userName=          userName;
		this.userEmail=         userEmail;
		this.mobileNo=          mobileNo;
		this.phoneNo=           phoneNo;
		this.passwd=            passwd;
		this.authKey=           authKey;
		this.useYn=             useYn;
		this.passwdUpdateDate=  passwdUpdateDate;
		this.userProfile=       userProfile;
		this.userStatus=        userStatus;
		this.profileUrl = 		profileUrl;
		this.avatarUrl = 		avatarUrl;
		this.accessToken=	    accessToken;	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getPasswdUpdateDate() {
		return passwdUpdateDate;
	}

	public void setPasswdUpdateDate(String passwdUpdateDate) {
		this.passwdUpdateDate = passwdUpdateDate;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}

	public String getRegisteredDtm() {
		return registeredDtm;
	}

	public void setRegisteredDtm(String registeredDtm) {
		this.registeredDtm = registeredDtm;
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
