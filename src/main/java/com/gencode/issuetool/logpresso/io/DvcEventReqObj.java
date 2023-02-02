package com.gencode.issuetool.logpresso.io;

public class DvcEventReqObj {
	String dvcType;
	String frData;
	String toDate;
	String evtLevel;
	String apikey;
	
	public DvcEventReqObj() {
		
	}
	
	public DvcEventReqObj(String dvcType, String frData, String toDate, String evtLevel) {
		super();
		this.dvcType = dvcType;
		this.frData = frData;
		this.toDate = toDate;
		this.evtLevel = evtLevel;
	}

	public DvcEventReqObj(String dvcType, String frData, String toDate, String evtLevel, String apikey) {
		super();
		this.dvcType = dvcType;
		this.frData = frData;
		this.toDate = toDate;
		this.evtLevel = evtLevel;
		this.apikey = apikey;
	}
	
	public String getDvcType() {
		return dvcType;
	}

	public void setDvcType(String dvcType) {
		this.dvcType = dvcType;
	}

	public String getFrData() {
		return frData;
	}

	public void setFrData(String frData) {
		this.frData = frData;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEvtLevel() {
		return evtLevel;
	}

	public void setEvtLevel(String evtLevel) {
		this.evtLevel = evtLevel;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

}
