package com.gencode.issuetool.logpresso.io;

public class IotDataReqObj {
	String interior;
	String apikey;
	
	public IotDataReqObj() {
		
	}
	public IotDataReqObj(String interior) {
		super();
		this.interior = interior;
	}

	public IotDataReqObj(String interior, String apikey) {
		super();
		this.interior = interior;
		this.apikey = apikey;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
}
