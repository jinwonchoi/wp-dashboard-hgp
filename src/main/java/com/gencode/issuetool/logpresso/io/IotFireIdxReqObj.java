package com.gencode.issuetool.logpresso.io;

public class IotFireIdxReqObj {
	String interior;
	String duration;
	String apikey;
	
	public IotFireIdxReqObj() {
		
	}
	public IotFireIdxReqObj(String interior, String duration) {
		super();
		this.interior = interior;
		this.duration = duration;
	}

	public IotFireIdxReqObj(String interior, String duration, String apikey) {
		super();
		this.interior = interior;
		this.duration = duration;
		this.apikey = apikey;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
}
