package com.gencode.issuetool.obj;

public class FacilTagInfo extends Pojo {

	protected long id;
	protected String tagName;
	protected long facilityId;
	protected String dataType;
	protected String tagDesc;
	protected String tagStyle;
	protected String updatedDtm;
	protected String createdDtm;
			
	public FacilTagInfo() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public long getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(long facilityId) {
		this.facilityId = facilityId;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getTagDesc() {
		return tagDesc;
	}
	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	public String getTagStyle() {
		return tagStyle;
	}
	public void setTagStyle(String tagStyle) {
		this.tagStyle = tagStyle;
	}
	public String getUpdatedDtm() {
		return updatedDtm;
	}
	public void setUpdatedDtm(String updatedDtm) {
		this.updatedDtm = updatedDtm;
	}
	public String getCreatedDtm() {
		return createdDtm;
	}
	public void setCreatedDtm(String createdDtm) {
		this.createdDtm = createdDtm;
	}
	
}
