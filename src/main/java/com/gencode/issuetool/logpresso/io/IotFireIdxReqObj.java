package com.gencode.issuetool.logpresso.io;

public class IotFireIdxReqObj extends ReqObj {
	String interior;
	String duration;
	
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
}
