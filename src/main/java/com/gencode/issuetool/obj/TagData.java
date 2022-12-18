/**=========================================================================================
<overview>설비태그현황 상태값 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class TagData extends Pojo {
	protected String createdDtm;
	protected String plantNo;
	protected String plantPartCode;
	protected String facilCode;
	protected String tagName;
	protected int scrSeq;
	protected int tagVal;
	protected int maxTagVal;
		
	public TagData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TagData(String createdDtm, String plantNo, String plantPartCode, String facilCode, String tagName,
			int scrSeq, int tagVal, int maxTagVal, String status) {
		super();
		this.createdDtm = createdDtm;
		this.plantNo = plantNo;
		this.plantPartCode = plantPartCode;
		this.facilCode = facilCode;
		this.tagName = tagName;
		this.scrSeq = scrSeq;
		this.tagVal = tagVal;
		this.maxTagVal = maxTagVal;
		this.status = status;
	}	
	public String getCreatedDtm() {
		return createdDtm;
	}
	public void setCreatedDtm(String createdDtm) {
		this.createdDtm = createdDtm;
	}
	public String getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}
	public String getPlantPartCode() {
		return plantPartCode;
	}
	public void setPlantPartCode(String plantPartCode) {
		this.plantPartCode = plantPartCode;
	}
	public String getFacilCode() {
		return facilCode;
	}
	public void setFacilCode(String facilCode) {
		this.facilCode = facilCode;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public int getScrSeq() {
		return scrSeq;
	}
	public void setScrSeq(int scrSeq) {
		this.scrSeq = scrSeq;
	}
	public int getTagVal() {
		return tagVal;
	}
	public void setTagVal(int tagVal) {
		this.tagVal = tagVal;
	}
	public int getMaxTagVal() {
		return maxTagVal;
	}
	public void setMaxTagVal(int maxTagVal) {
		this.maxTagVal = maxTagVal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	protected String status;
	
}
