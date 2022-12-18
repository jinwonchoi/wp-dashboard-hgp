/**=========================================================================================
<overview>공간기준정보관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class InteriorInfo extends Pojo {
	
	protected long id;
	protected String interiorCode ;
	protected String interiorName;
	protected long areaId;
	protected float posX;
	protected float posY;
	protected float posZ;
	protected float sizeX;
	protected float sizeY;
	protected float sizeZ;
	protected String description;

	public InteriorInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getInteriorCode() {
		return interiorCode;
	}

	public void setInteriorCode(String interiorCode) {
		this.interiorCode = interiorCode;
	}

	public String getInteriorName() {
		return interiorName;
	}

	public void setInteriorName(String interiorName) {
		this.interiorName = interiorName;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
