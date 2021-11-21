package com.gencode.issuetool.obj;

public class FacilityInfo extends Pojo {
	
	protected long id;
	protected String facilCode;
	protected String facilName;
	protected long areaId;
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

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	protected String description;
	
	public FacilityInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
