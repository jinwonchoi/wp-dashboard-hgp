package com.gencode.issuetool.logpresso.io;

public class IotDataByDeviceReqObj {
	String deviceId;
	String apikey;
	
	public IotDataByDeviceReqObj() {
		
	}
	public IotDataByDeviceReqObj(String deviceId) {
		super();
		this.deviceId = deviceId;
	}

	public IotDataByDeviceReqObj(String deviceId, String apikey) {
		super();
		this.deviceId = deviceId;
		this.apikey = apikey;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
}
