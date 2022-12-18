/**=========================================================================================
<overview>설비태그현황 상태값 집계 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class TagDataHistStat extends Pojo {
	protected String createdDtm;
	protected String timeMode;
	protected String plantNo;
	protected String plantPartCode;
	protected String facilCode;
	protected String tagName;
	protected long scrSeq;
	protected long avgTagVal;
	protected long minTagVal;
	protected long maxTagVal;
	protected long alarmCnt;
	protected long criticalCnt;
	
	
	public TagDataHistStat() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TagDataHistStat(String createdDtm, String timeMode, String plantNo, String plantPartCode, String facilCode,
			String tagName, long scrSeq, long avgTagVal, long minTagVal, long maxTagVal, long alarmCnt,
			long criticalCnt) {
		super();
		this.createdDtm = createdDtm;
		this.timeMode = timeMode;
		this.plantNo = plantNo;
		this.plantPartCode = plantPartCode;
		this.facilCode = facilCode;
		this.tagName = tagName;
		this.scrSeq = scrSeq;
		this.avgTagVal = avgTagVal;
		this.minTagVal = minTagVal;
		this.maxTagVal = maxTagVal;
		this.alarmCnt = alarmCnt;
		this.criticalCnt = criticalCnt;
	}
	public String getCreatedDtm() {
		return createdDtm;
	}
	public void setCreatedDtm(String createdDtm) {
		this.createdDtm = createdDtm;
	}
	public String getTimeMode() {
		return timeMode;
	}
	public void setTimeMode(String timeMode) {
		this.timeMode = timeMode;
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
	public long getScrSeq() {
		return scrSeq;
	}
	public void setScrSeq(long scrSeq) {
		this.scrSeq = scrSeq;
	}
	public long getAvgTagVal() {
		return avgTagVal;
	}
	public void setAvgTagVal(long avgTagVal) {
		this.avgTagVal = avgTagVal;
	}
	public long getMinTagVal() {
		return minTagVal;
	}
	public void setMinTagVal(long minTagVal) {
		this.minTagVal = minTagVal;
	}
	public long getMaxTagVal() {
		return maxTagVal;
	}
	public void setMaxTagVal(long maxTagVal) {
		this.maxTagVal = maxTagVal;
	}
	public long getAlarmCnt() {
		return alarmCnt;
	}
	public void setAlarmCnt(long alarmCnt) {
		this.alarmCnt = alarmCnt;
	}
	public long getCriticalCnt() {
		return criticalCnt;
	}
	public void setCriticalCnt(long criticalCnt) {
		this.criticalCnt = criticalCnt;
	}
}
