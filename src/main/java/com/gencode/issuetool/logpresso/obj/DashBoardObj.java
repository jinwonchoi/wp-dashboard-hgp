package com.gencode.issuetool.logpresso.obj;

import java.util.List;

import com.gencode.issuetool.obj.Pojo;

public class DashBoardObj extends Pojo {
	String defaultMain;
	String facilMain09;
	String facilMain10;
	String facilMain00;
	List<String> iotMain09;
	List<String> iotMain10;
	List<String> iotMain00;
	List<String> iotMain09Pilot;
	List<String> iotMain10Pilot;

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
	public List<String> getIotMain09Pilot() {
		return iotMain09Pilot;
	}
	public void setIotMain09Pilot(List<String> iotMain09Pilot) {
		this.iotMain09Pilot = iotMain09Pilot;
	}
	public List<String> getIotMain10Pilot() {
		return iotMain10Pilot;
	}
	public void setIotMain10Pilot(List<String> iotMain10Pilot) {
		this.iotMain10Pilot = iotMain10Pilot;
	}
	public String getFacilMain00() {
		return facilMain00;
	}
	public void setFacilMain00(String facilMain00) {
		this.facilMain00 = facilMain00;
	}
	public List<String> getIotMain00() {
		return iotMain00;
	}
	public void setIotMain00(List<String> iotMain00) {
		this.iotMain00 = iotMain00;
	}
	
}
