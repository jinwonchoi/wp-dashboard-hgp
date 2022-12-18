/**=========================================================================================
<overview>센서장비현황상태값관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class IotData extends Pojo {
	protected String createdDtm;
	protected String interiorCode;
	protected String deviceId;
	protected long humidVal;
	protected long smokeVal;
	protected long tempVal;
	protected long coVal;
	protected long flame;
	
	public IotData() {
		super();
	}

	public IotData(String createdDtm, String interiorCode, String deviceId, long humidVal, long smokeVal, long tempVal,
			long coVal, long flame) {
		super();
		this.createdDtm = createdDtm;
		this.interiorCode = interiorCode;
		this.deviceId = deviceId;
		this.humidVal = humidVal;
		this.smokeVal = smokeVal;
		this.tempVal = tempVal;
		this.coVal = coVal;
		this.flame = flame;
	}

	public String getCreatedDtm() {
		return createdDtm;
	}

	public void setCreatedDtm(String createdDtm) {
		this.createdDtm = createdDtm;
	}

	public String getInteriorCode() {
		return interiorCode;
	}

	public void setInteriorCode(String interiorCode) {
		this.interiorCode = interiorCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getHumidVal() {
		return humidVal;
	}

	public void setHumidVal(long humidVal) {
		this.humidVal = humidVal;
	}

	public long getSmokeVal() {
		return smokeVal;
	}

	public void setSmokeVal(long smokeVal) {
		this.smokeVal = smokeVal;
	}

	public long getTempVal() {
		return tempVal;
	}

	public void setTempVal(long tempVal) {
		this.tempVal = tempVal;
	}

	public long getCoVal() {
		return coVal;
	}

	public void setCoVal(long coVal) {
		this.coVal = coVal;
	}

	public long getFlame() {
		return flame;
	}

	public void setFlame(long flame) {
		this.flame = flame;
	}	
}
