package com.gencode.issuetool.logpresso.io;

public class DataByTagReqObj {
	String plantType;
	String plantNo;
	String plantPart;
	String duration;
	String apikey;
	
	public DataByTagReqObj() {
		
	}

	public DataByTagReqObj(String plantType, String plantNo, String plantPart, String duration) {
		super();
		this.plantType = plantType;
		this.plantNo = plantNo;
		this.plantPart = plantPart;
		this.duration = duration;
	}

	public DataByTagReqObj(String plantType, String plantNo, String plantPart, String duration, String apikey) {
		super();
		this.plantType = plantType;
		this.plantNo = plantNo;
		this.plantPart = plantPart;
		this.duration = duration;
		this.apikey = apikey;
	}

	public String getPlantType() {
		return plantType;
	}

	public void setPlantType(String plantType) {
		this.plantType = plantType;
	}

	public String getPlantNo() {
		return plantNo;
	}

	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}

	public String getPlantPart() {
		return plantPart;
	}

	public void setPlantPart(String plantPart) {
		this.plantPart = plantPart;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}


}
