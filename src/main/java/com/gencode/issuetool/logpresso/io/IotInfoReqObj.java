package com.gencode.issuetool.logpresso.io;

public class IotInfoReqObj {
	String apikey;
	
	public IotInfoReqObj() {
		
	}
	
	public IotInfoReqObj(String apikey) {
		super();
		this.apikey = apikey;
	}
	
	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

}
