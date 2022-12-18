/**=========================================================================================
<overview>설비태그 화재지수 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class TagFireIdx extends Pojo {

	protected String createdDtm;
	protected String plantNo;
	protected String plantPartCode;
	protected long fireIdx;
	protected long maxFireIdx;
			
	public TagFireIdx() {
		super();
	}

	public TagFireIdx(String createdDtm, String plantNo, String plantPartCode, long fireIdx,
			long maxFireIdx) {
		super();
		this.createdDtm = createdDtm;
		this.plantNo = plantNo;
		this.plantPartCode = plantPartCode;
		this.fireIdx = fireIdx;
		this.maxFireIdx = maxFireIdx;
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
