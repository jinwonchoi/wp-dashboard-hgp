/**=========================================================================================
<overview>센서화재지수관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class IotFireIdx extends Pojo {

	protected String createdDtm;
	protected String interiorCode;
	protected long fireIdx;
	protected long maxFireIdx;
	
	public IotFireIdx() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IotFireIdx(String createdDtm, String interiorCode, long fireIdx, long maxFireIdx) {
		super();
		this.createdDtm = createdDtm;
		this.interiorCode = interiorCode;
		this.fireIdx = fireIdx;
		this.maxFireIdx = maxFireIdx;
	}

	public String getCreatedDtm() {
		return createdDtm;
	}

	public void setCreatedDtm(String createdDtm) {
		this.createdDtm = createdDtm;
	}

	public String getInteriorCode() {
		return interiorCode;
	}

	public void setInteriorCode(String interiorCode) {
		this.interiorCode = interiorCode;
	}

	public long getFireIdx() {
		return fireIdx;
	}

	public void setFireIdx(long fireIdx) {
		this.fireIdx = fireIdx;
	}

	public long getMaxFireIdx() {
		return maxFireIdx;
	}

	public void setMaxFireIdx(long maxFireIdx) {
		this.maxFireIdx = maxFireIdx;
	}

}
