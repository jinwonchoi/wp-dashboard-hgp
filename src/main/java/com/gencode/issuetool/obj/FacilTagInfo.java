/**=========================================================================================
<overview>태그기준정보관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class FacilTagInfo extends Pojo {

	protected long id;
	protected String tagName;
	protected String tagDesc;
	protected String plantType;
	protected long facilityId;
	protected  FacilityInfo facilityInfo; 
	protected String alrmType;
	protected String alrmVal1;
	protected String alrmVal2;
	protected String tripType;
	protected String tripVal1;
	protected String tripVal2;
	protected String plantNo;
	protected String plantPartCode;
	protected String facilCode;
	protected String facilName;
	protected long scrSeq;
	protected String valType;
	protected long funcCd;
	protected String startRegister;
	protected String address;
	protected long dataLen;
	protected String tagDesc2;
	protected String tagPrintName;
	protected String redundancy;

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
	public String getTagDesc() {
		return tagDesc;
	}
	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	public String getPlantType() {
		return plantType;
	}
	public void setPlantType(String plantType) {
		this.plantType = plantType;
	}
	public long getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(long facilityId) {
		this.facilityId = facilityId;
	}
	public FacilityInfo getFacilityInfo() {
		return facilityInfo;
	}
	public void setFacilityInfo(FacilityInfo facilityInfo) {
		this.facilityInfo = facilityInfo;
	}
	public String getAlrmType() {
		return alrmType;
	}
	public void setAlrmType(String alrmType) {
		this.alrmType = alrmType;
	}
	public String getAlrmVal1() {
		return alrmVal1;
	}
	public void setAlrmVal1(String alrmVal1) {
		this.alrmVal1 = alrmVal1;
	}
	public String getAlrmVal2() {
		return alrmVal2;
	}
	public void setAlrmVal2(String alrmVal2) {
		this.alrmVal2 = alrmVal2;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public String getTripVal1() {
		return tripVal1;
	}
	public void setTripVal1(String tripVal1) {
		this.tripVal1 = tripVal1;
	}
	public String getTripVal2() {
		return tripVal2;
	}
	public void setTripVal2(String tripVal2) {
		this.tripVal2 = tripVal2;
	}
	public long getFuncCd() {
		return funcCd;
	}
	public void setFuncCd(long funcCd) {
		this.funcCd = funcCd;
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
	
	public String getFacilName() {
		return facilName;
	}
	public void setFacilName(String facilName) {
		this.facilName = facilName;
	}
	public long getScrSeq() {
		return scrSeq;
	}
	public void setScrSeq(long scrSeq) {
		this.scrSeq = scrSeq;
	}
	public String getValType() {
		return valType;
	}
	public void setValType(String valType) {
		this.valType = valType;
	}
	public String getStartRegister() {
		return startRegister;
	}
	public void setStartRegister(String startRegister) {
		this.startRegister = startRegister;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTagDesc2() {
		return tagDesc2;
	}
	public void setTagDesc2(String tagDesc2) {
		this.tagDesc2 = tagDesc2;
	}
	
	public String getTagPrintName() {
		return tagPrintName;
	}
	public void setTagPrintName(String tagPrintName) {
		this.tagPrintName = tagPrintName;
	}
	public String getRedundancy() {
		return redundancy;
	}
	public void setRedundancy(String redundancy) {
		this.redundancy = redundancy;
	}
	public long getDataLen() {
		return dataLen;
	}
	public void setDataLen(long dataLen) {
		this.dataLen = dataLen;
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
