package com.gencode.issuetool.obj;

import java.time.LocalDateTime;

import com.gencode.issuetool.etc.Utils;

public class User extends Pojo {

	protected String userid;
	protected String password;
	protected String username;
	protected String email;
	protected String role;
	protected String description;
	protected String emailAuthKey;
	protected String emailAuthYn;
	protected String deleteYn;
	protected LocalDateTime createDate;
	protected LocalDateTime updateDate;

	public User() {
	}

	public User(String userid, String emailAuthKey) {
		super();
		this.userid = userid;
		this.emailAuthKey = emailAuthKey;
	}

	public User(String userid, String password, String username, String email, String role, String description) {
		super();
		this.userid = userid;
		this.password = password;
		this.username = username;
		this.email = email;
		this.role = role;
		this.description = description;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmailAuthKey() {
		return emailAuthKey;
	}

	public void setEmailAuthKey(String emailAuthKey) {
		this.emailAuthKey = emailAuthKey;
	}

	public String getEmailAuthYn() {
		return emailAuthYn;
	}

	public void setEmailAuthYn(String emailAuthYn) {
		this.emailAuthYn = emailAuthYn;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public String getUserid() {
		return userid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

}
