package com.gencode.issuetool.logpresso.io;

public class TagFireIdxReqObj {
	String plantType;
	String plantNo;
	String duration;
	String apikey;
	
	public TagFireIdxReqObj() {
		
	}

	public TagFireIdxReqObj(String plantType, String plantNo, String duration) {
		super();
		this.plantType = plantType;
		this.plantNo = plantNo;
		this.duration = duration;
	}

	public TagFireIdxReqObj(String plantType, String plantNo, String duration, String apikey) {
		super();
		this.plantType = plantType;
		this.plantNo = plantNo;
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
