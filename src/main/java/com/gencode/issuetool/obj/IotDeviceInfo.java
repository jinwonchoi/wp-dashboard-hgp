package com.gencode.issuetool.obj;

public class IotDeviceInfo extends Pojo {
	protected long id;
	protected String deviceId;
	protected String deviceType;
	protected long interiorId;
	protected InteriorInfo interiorInfo; 

	protected float posX;
	protected float posY;
	protected float posZ;
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
	public String getUpdatedDtm() {
		return updatedDtm;
	}

	
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public float getPosZ() {
		return posZ;
	}
	public void setPosZ(float posZ) {
		this.posZ = posZ;
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
