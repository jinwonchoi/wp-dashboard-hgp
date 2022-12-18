/**=========================================================================================
<overview>센서화재지수집계관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class IotFireIdxHistStat extends Pojo {
	protected String createdDtm;
	protected String timeMode;
	protected String interiorCode;
	protected long avgFireIdx;
	protected long minFireIdx;
	protected long maxFireIdx;
	protected long alarmCnt;
	protected long criticalCnt;

	public IotFireIdxHistStat(String createdDtm, String timeMode, String interiorCode, long avgFireIdx, long minFireIdx,
			long maxFireIdx, long alarmCnt, long criticalCnt) {
		super();
		this.createdDtm = createdDtm;
		this.timeMode = timeMode;
		this.interiorCode = interiorCode;
		this.avgFireIdx = avgFireIdx;
		this.minFireIdx = minFireIdx;
		this.maxFireIdx = maxFireIdx;
		this.alarmCnt = alarmCnt;
		this.criticalCnt = criticalCnt;
	}

	public IotFireIdxHistStat() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getInteriorCode() {
		return interiorCode;
	}

	public void setInteriorCode(String interiorCode) {
		this.interiorCode = interiorCode;
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
