/**=========================================================================================
<overview>태그/센서 실시간발생이벤트 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class TagDvcPushEvent extends Pojo {
	protected String createdDtm;
	protected String deviceType;
	protected String deviceId;
	protected String valType;
	protected String eventLevel;
	protected String eventVal;
	protected String eventDesc;
	protected String interiorCode;
	protected String plantNo;
	protected String plantPartCode;
	protected String facilCode;
	public TagDvcPushEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TagDvcPushEvent(String createdDtm, String deviceType, String deviceId, String valType, String eventLevel,
			String eventVal, String eventDesc, String interiorCode, String plantNo, String plantPartCode,
			String facilCode) {
		super();
		this.createdDtm = createdDtm;
		this.deviceType = deviceType;
		this.deviceId = deviceId;
		this.valType = valType;
		this.eventLevel = eventLevel;
		this.eventVal = eventVal;
		this.eventDesc = eventDesc;
		this.interiorCode = interiorCode;
		this.plantNo = plantNo;
		this.plantPartCode = plantPartCode;
		this.facilCode = facilCode;
	}
	public String getCreatedDtm() {
		return createdDtm;
	}
	public void setCreatedDtm(String createdDtm) {
		this.createdDtm = createdDtm;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getValType() {
		return valType;
	}
	public void setValType(String valType) {
		this.valType = valType;
	}
	public String getEventLevel() {
		return eventLevel;
	}
	public void setEventLevel(String eventLevel) {
		this.eventLevel = eventLevel;
	}
	public String getEventVal() {
		return eventVal;
	}
	public void setEventVal(String eventVal) {
		this.eventVal = eventVal;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public String getInteriorCode() {
		return interiorCode;
	}
	public void setInteriorCode(String interiorCode) {
		this.interiorCode = interiorCode;
	}
	public String getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}
	public String getPlantPartCode() {
		return plantPartCode;
	}
	public void setPlantPartCode(String plantPartCode) {
		this.plantPartCode = plantPartCode;
	}
	public String getFacilCode() {
		return facilCode;
	}
	public void setFacilCode(String facilCode) {
		this.facilCode = facilCode;
	}

}
