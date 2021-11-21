package com.gencode.issuetool.obj;

public class IotRuleInfo extends Pojo {

	protected long id;
	protected String iotRuleCode;
	protected String iotRuleName;
	protected String dataType;
		
	protected long interiorId;
	protected float criticVal;
	protected String ruleDesc;
	protected String tipDesc;
	protected String updatedDtm;
	protected String createdDtm;
	
	public IotRuleInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getIotRuleCode() {
		return iotRuleCode;
	}

	public void setIotRuleCode(String iotRuleCode) {
		this.iotRuleCode = iotRuleCode;
	}

	public String getIotRuleName() {
		return iotRuleName;
	}

	public void setIotRuleName(String iotRuleName) {
		this.iotRuleName = iotRuleName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public long getInteriorId() {
		return interiorId;
	}

	public void setInteriorId(long interiorId) {
		this.interiorId = interiorId;
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
