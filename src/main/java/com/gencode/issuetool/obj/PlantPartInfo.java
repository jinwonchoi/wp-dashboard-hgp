/**=========================================================================================
<overview>메인구성부품기준정보관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class PlantPartInfo {

	protected long id;
	protected String plantPartCode;
	protected String plantPartName;
	protected long plantId;
	protected String description;

	public PlantPartInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlantPartCode() {
		return plantPartCode;
	}

	public void setPlantPartCode(String plantPartCode) {
		this.plantPartCode = plantPartCode;
	}

	public String getPlantPartName() {
		return plantPartName;
	}

	public void setPlantPartName(String plantPartName) {
		this.plantPartName = plantPartName;
	}

	public long getPlantId() {
		return plantId;
	}

	public void setPlantId(long plantId) {
		this.plantId = plantId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
