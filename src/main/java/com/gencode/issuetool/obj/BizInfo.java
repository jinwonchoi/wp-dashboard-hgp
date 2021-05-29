package com.gencode.issuetool.obj;

public class BizInfo extends Pojo {
	protected String id;
	protected String name;
	protected String country;
	protected String lang;
	protected String location;
	public BizInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BizInfo(String id, String name, String country, String lang, String location) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
		this.lang = lang;
		this.location = location;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	
}
