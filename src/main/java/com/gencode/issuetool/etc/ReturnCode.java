package com.gencode.issuetool.etc;

public enum ReturnCode {
	SUCCESS("0001"),
	OK_FORCED_UNSUBSCRIBE("0101"),
	OK_FORCED_TASK_REMOVE("0102"),
	OK_USER_UPDATED("0103"),
	
	ALREADY_EXISTS("1001"),
	ALREADY_EXISTS_TASK("1002"),
	ALREADY_EXISTS_SCHEDULE("1003"),
	ALREADY_EXISTS_USERID("1004"),
	ALREADY_EXISTS_EMAIL("1005"),

	USER_NOT_FOUND("4001"),
	INVALID_AUTH("4002"),
	AUTH_EXPIRED("4003"),
	ERROR_FILEUP("4004"),
	INVALID_PASSWD("4005"),
	NOT_LOGGED_IN("4006"),
	FILE_MAX_SIZE_OVER("4007"),
	PASSWORD_NOT_MATCH("4008"),
	
	TASK_NOT_FOUND("4101"),
	SCHEDULE_NOT_FOUND("4201"),
	TODAY_NOT_FOUND("4301"),
	DATA_NOT_FOUND("4999"),
	ERROR_MAIL_SEND("5001"),
	ERROR_UNKNOWN("9999"),
	/*
	0001 Success
	4001 UserId Not Found
	4002 Invalid authentication id
	4003 authentication date expired
	9999 Unknown Error
	1001 Already Exists
	 */
	STR_SUCCESS("Success"),
	STR_ALREADY_EXISTS("Already  Exists"),
	STR_ALREADY_EXISTS_TASK("Tasks Exists"),
	STR_ALREADY_EXISTS_SCHEDULE("Schedules Exists"),
	STR_ALREADY_EXISTS_USERID("Userid Exists"),
	STR_ALREADY_EXISTS_EMAIL("Email Exists"),
	STR_OK_FORCED_UNSUBSCRIBE("Unsubscribed with %s tasks"),
	STR_OK_FORCED_TASK_REMOVE("Task removed with %s schedules"),
	STR_OK_USER_UPDATED("Existing user updated"),
	STR_USER_NOT_FOUND("User Not Found"),
	STR_INVALID_AUTH("Invalid Authentication Id"),
	STR_AUTH_EXPIRED("Authentication Date Expired"),
	STR_ERROR_FILEUP("Error in File upload"),
	STR_INVALID_PASSWD("Wrong password"),
	STR_NOT_LOGGED_IN("Not logged in"),
	STR_FILE_MAX_SIZE_OVER("File size exceeds max %d bytes"),
	STR_PASSWORD_NOT_MATCH("Passwd not match"),
	STR_TASK_NOT_FOUND("Task Not Found"),
	STR_SCHEDULE_NOT_FOUND("Schedule Not Found"),
	STR_TODAY_NOT_FOUND("Today Not Found"),
	STR_DATA_NOT_FOUND("Data Not Found"),
	STR_ERROR_MAIL_SEND("Mail Send Error"),
	STR_ERROR_UNKNOWN("Unknown Error"),
	;
	private ReturnCode(String str) {
		value = str;
	}
	
	private ReturnCode(int val) {
		intValue = val;
	}
	
	private int intValue;
	private String value;
	
	public boolean equalValues(String str) {
		return (str == null)?false:value.equals(str);
	}
	
	public String get() {
		return value;
	}
	
	public int val() {
		return intValue;
	}

}
