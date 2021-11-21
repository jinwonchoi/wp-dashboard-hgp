package com.gencode.issuetool.obj;

public class FacilRuleInfo extends Pojo {

	protected long id;
	protected String facilRuleCode;
	protected String facilRuleName;
	protected String dataType;
	protected long facilityId;
	protected float criticVal;
	protected String ruleDesc;
	protected String tipDesc;
	protected String updatedDtm;
	protected String createdDtm;

	public FacilRuleInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFacilRuleCode() {
		return facilRuleCode;
	}

	public void setFacilRuleCode(String facilRuleCode) {
		this.facilRuleCode = facilRuleCode;
	}
	public String getFacilRuleName() {
		return facilRuleName;
	}

	public void setFacilRuleName(String facilRuleName) {
		this.facilRuleName = facilRuleName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(long facilityId) {
		this.facilityId = facilityId;
	}

	public float getCriticVal() {
		return criticVal;
	}

	public void setCriticVal(float criticVal) {
		this.criticVal = criticVal;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getTipDesc() {
		return tipDesc;
	}

	public void setTipDesc(String tipDesc) {
		this.tipDesc = tipDesc;
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
