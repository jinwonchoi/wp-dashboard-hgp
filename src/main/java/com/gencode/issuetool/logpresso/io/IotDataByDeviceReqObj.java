package com.gencode.issuetool.logpresso.io;

public class IotDataByDeviceReqObj extends ReqObj {
	String deviceId;
	
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
}
