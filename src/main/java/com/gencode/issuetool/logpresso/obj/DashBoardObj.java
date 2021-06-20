package com.gencode.issuetool.logpresso.obj;

import java.util.List;

import com.gencode.issuetool.obj.Pojo;

public class DashBoardObj extends Pojo {
	String defaultMain;
	String facilMain09;
	String facilMain10;
	List<String> iotMain09;
	List<String> iotMain10;

	public DashBoardObj() {
		super();
	}
	public String getDefaultMain() {
		return defaultMain;
	}
	public void setDefaultMain(String defaultMain) {
		this.defaultMain = defaultMain;
	}
	public String getFacilMain09() {
		return facilMain09;
	}
	public void setFacilMain09(String facilMain09) {
		this.facilMain09 = facilMain09;
	}
	public String getFacilMain10() {
		return facilMain10;
	}
	public void setFacilMain10(String facilMain10) {
		this.facilMain10 = facilMain10;
	}
	public List<String> getIotMain09() {
		return iotMain09;
	}
	public void setIotMain09(List<String> iotMain09) {
		this.iotMain09 = iotMain09;
	}
	public List<String> getIotMain10() {
		return iotMain10;
	}
	public void setIotMain10(List<String> iotMain10) {
		this.iotMain10 = iotMain10;
	}
}
