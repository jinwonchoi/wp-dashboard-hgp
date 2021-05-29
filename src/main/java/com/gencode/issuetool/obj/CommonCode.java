package com.gencode.issuetool.obj;

public class CommonCode extends Pojo {
	protected String groupId;
	protected String itemKey;
	protected String itemValue;
	protected String description;
	public CommonCode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommonCode(String groupId, String itemKey, String itemValue, String description) {
		super();
		this.groupId = groupId;
		this.itemKey = itemKey;
		this.itemValue = itemValue;
		this.description = description;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getItemKey() {
		return itemKey;
	}
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
