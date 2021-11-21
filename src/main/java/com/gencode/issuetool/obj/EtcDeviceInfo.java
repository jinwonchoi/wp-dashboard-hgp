package com.gencode.issuetool.obj;

public class EtcDeviceInfo extends Pojo {
	protected long id;
	protected String deviceId;
	protected String deviceType;
	protected String deviceName;
	protected String deviceSerno;
	protected String deviceDesc;
	protected long interiorId;
	protected InteriorInfo interiorInfo; 
	
	protected float posX;
	protected float posY;
	protected float posZ;
	protected float dirX;
	protected float dirY;
	protected float dirZ;
	
	protected String registerDate;
	protected String installDate;
	protected String termDate;
	protected String updatedDtm;
	protected String createdDtm;
	
	public EtcDeviceInfo() {
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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceSerno() {
		return deviceSerno;
	}

	public void setDeviceSerno(String deviceSerno) {
		this.deviceSerno = deviceSerno;
	}

	public String getDeviceDesc() {
		return deviceDesc;
	}

	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
	}

	public String getRegisterDate() {
		return registerDate;
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

	public float getDirX() {
		return dirX;
	}

	public void setDirX(float dirX) {
		this.dirX = dirX;
	}

	public float getDirY() {
		return dirY;
	}

	public void setDirY(float dirY) {
		this.dirY = dirY;
	}

	public float getDirZ() {
		return dirZ;
	}

	public void setDirZ(float dirZ) {
		this.dirZ = dirZ;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public String getTermDate() {
		return termDate;
	}

	public void setTermDate(String termDate) {
		this.termDate = termDate;
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
