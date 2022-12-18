/**=========================================================================================
<overview>사용자 접근권한정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.etc;

public enum UserRole {
    USER("U"), MANAGER("M"), ADMIN("A");
	private UserRole(String str) {
		value = str;
	}
	
	private UserRole(int val) {
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
