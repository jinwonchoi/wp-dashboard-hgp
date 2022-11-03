package com.gencode.issuetool.obj;

public class TagFireIdxHistStat extends Pojo {
	protected String createdDtm;
	protected String timeMode;
	protected String plantNo;
	protected String plantPartCode;
	protected long avgFireIdx;
	protected long minFireIdx;
	protected long maxFireIdx;
	protected long alarmCnt;
	protected long criticalCnt;
	
	public TagFireIdxHistStat() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public TagFireIdxHistStat(String createdDtm, String timeMode, String plantNo, String plantPartCode, long avgFireIdx,
			long minFireIdx, long maxFireIdx, long alarmCnt, long criticalCnt) {
		super();
		this.createdDtm = createdDtm;
		this.timeMode = timeMode;
		this.plantNo = plantNo;
		this.plantPartCode = plantPartCode;
		this.avgFireIdx = avgFireIdx;
		this.minFireIdx = minFireIdx;
		this.maxFireIdx = maxFireIdx;
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
	public long getAvgFireIdx() {
		return avgFireIdx;
	}
	public void setAvgFireIdx(long avgFireIdx) {
		this.avgFireIdx = avgFireIdx;
	}
	public long getMinFireIdx() {
		return minFireIdx;
	}
	public void setMinFireIdx(long minFireIdx) {
		this.minFireIdx = minFireIdx;
	}
	public long getMaxFireIdx() {
		return maxFireIdx;
	}
	public void setMaxFireIdx(long maxFireIdx) {
		this.maxFireIdx = maxFireIdx;
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
