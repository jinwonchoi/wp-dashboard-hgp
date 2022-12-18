/**=========================================================================================
<overview>발전호기기준정보관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class PlantInfo extends Pojo {
	protected long id;
	protected String plantNo;
	protected String plantName;
	protected String description;
	public PlantInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
