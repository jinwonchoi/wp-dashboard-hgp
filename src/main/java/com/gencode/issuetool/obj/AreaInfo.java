/**=========================================================================================
<overview>구역기준정보관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class AreaInfo {

	protected long id;
	protected String areaCode;
	protected String areaName;
	protected long plantId;
	protected float sizeX;
	protected float sizeY;
	protected float sizeZ;
	protected String description;

	public AreaInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public long getPlantId() {
		return plantId;
	}

	public void setPlantId(long plantId) {
		this.plantId = plantId;
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
