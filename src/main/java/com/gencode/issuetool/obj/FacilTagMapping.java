package com.gencode.issuetool.obj;

public class FacilTagMapping extends Pojo {
	
	protected long facilRuleId;
	protected long tagId;
	
	public FacilTagMapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getFacilRuleId() {
		return facilRuleId;
	}
	public void setFacilRuleId(long facilRuleId) {
		this.facilRuleId = facilRuleId;
	}
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}	
}
