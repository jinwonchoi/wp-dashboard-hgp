package com.gencode.issuetool.obj;

public class FacilityInfo extends Pojo {
	
	protected long id;
	protected String facilCode;
	protected String facilName;
	protected String facilName2;
	protected long plantPartId;
	protected PlantPartInfo plantPartInfo;  
	protected String description;
	
	public FacilityInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFacilCode() {
		return facilCode;
	}

	public void setFacilCode(String facilCode) {
		this.facilCode = facilCode;
	}

	public String getFacilName() {
		return facilName;
	}

	public void setFacilName(String facilName) {
		this.facilName = facilName;
	}

	public String getFacilName2() {
		return facilName2;
	}

	public void setFacilName2(String facilName2) {
		this.facilName2 = facilName2;
	}

	public long getPlantPartId() {
		return plantPartId;
	}

	public void setPlantPartId(long plantPartId) {
		this.plantPartId = plantPartId;
	}

	public PlantPartInfo getPlantPartInfo() {
		return plantPartInfo;
	}

	public void setPlantPartInfo(PlantPartInfo plantPartInfo) {
		this.plantPartInfo = plantPartInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
}
