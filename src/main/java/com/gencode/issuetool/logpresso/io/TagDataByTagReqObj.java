package com.gencode.issuetool.logpresso.io;

public class TagDataByTagReqObj {
	String tagname;
	String apikey;
	
	public TagDataByTagReqObj() {
		
	}
	
	public TagDataByTagReqObj(String tagname) {
		super();
		this.tagname = tagname;
	}

	public TagDataByTagReqObj(String tagname, String apikey) {
		super();
		this.tagname = tagname;
		this.apikey = apikey;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

}
