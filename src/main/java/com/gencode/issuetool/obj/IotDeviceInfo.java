/**=========================================================================================
<overview>기타장비기준정보관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class IotDeviceInfo extends Pojo {
	protected long id;
	protected String deviceId;
	protected String orgDeviceId;
	protected String deviceType;
	protected long interiorId;
	protected InteriorInfo interiorInfo; 
	protected String pmtNo;
	protected String rptrNo;
	protected String seq;
	protected String deviceDesc;

	protected String updatedDtm;
	protected String createdDtm;
	
	public IotDeviceInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getOrgDeviceId() {
		return orgDeviceId;
	}
	public void setOrgDeviceId(String orgDeviceId) {
		this.orgDeviceId = orgDeviceId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public long getInteriorId() {
		return interiorId;
	}
	public void setInteriorId(long interiorId) {
		this.interiorId = interiorId;
	}
	public InteriorInfo getInteriorInfo() {
		return interiorInfo;
	}
	public void setInteriorInfo(InteriorInfo interiorInfo) {
		this.interiorInfo = interiorInfo;
	}
	public String getPmtNo() {
		return pmtNo;
	}
	public void setPmtNo(String pmtNo) {
		this.pmtNo = pmtNo;
	}
	public String getRptrNo() {
		return rptrNo;
	}
	public void setRptrNo(String rptrNo) {
		this.rptrNo = rptrNo;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getDeviceDesc() {
		return deviceDesc;
	}
	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
	}
	public String getUpdatedDtm() {
		return updatedDtm;
	}
	public void setUpdatedDtm(String updatedDtm) {
		this.updatedDtm = updatedDtm;
	}
	public String getCreatedDtm() {
		return createdDtm;
	}
	public void setCreatedDtm(String createdDtm) {
		this.createdDtm = createdDtm;
	}

}
