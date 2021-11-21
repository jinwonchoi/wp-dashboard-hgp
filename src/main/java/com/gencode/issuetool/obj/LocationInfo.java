package com.gencode.issuetool.obj;

public class LocationInfo extends Pojo {
	protected long id;
	protected String locType;
	protected float sizeX;
	protected float sizeY;
	protected float sizeZ;
	protected long parentLocId;
	protected LocationInfo parentLocationInfo;
	public LocationInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLocType() {
		return locType;
	}
	public void setLocType(String locType) {
		this.locType = locType;
	}
	public float getSizeX() {
		return sizeX;
	}
	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}
	public float getSizeY() {
		return sizeY;
	}
	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}
	public float getSizeZ() {
		return sizeZ;
	}
	public void setSizeZ(float sizeZ) {
		this.sizeZ = sizeZ;
	}
	public long getParentLocId() {
		return parentLocId;
	}
	public void setParentLocId(long parentLocId) {
		this.parentLocId = parentLocId;
	}
	public LocationInfo getParentLocationInfo() {
		return parentLocationInfo;
	}
	public void setParentLocationInfo(LocationInfo parentLocationInfo) {
		this.parentLocationInfo = parentLocationInfo;
	}

}
