package com.gencode.issuetool.obj;

public class IotMainHistStat extends Pojo {
	protected String createdDtm;
	protected String timeMode;
	protected String interiorCode;
	protected long avgHumidVal;
	protected long avgSmokeVal;
	protected long avgTempVal;
	protected long avgCoVal;
	protected long minHumidVal;
	protected long minSmokeVal;
	protected long minTempVal;
	protected long minCoVal;
	protected long maxHumidVal;
	protected long maxSmokeVal;
	protected long maxTempVal;
	protected long maxCoVal;
	protected long flameCnt;
	
	public IotMainHistStat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public IotMainHistStat(String createdDtm, String timeMode, String interiorCode, long avgHumidVal,
			long avgSmokeVal, long avgTempVal, long avgCoVal, long minHumidVal, long minSmokeVal, long minTempVal,
			long minCoVal, long maxHumidVal, long maxSmokeVal, long maxTempVal, long maxCoVal, long flameCnt) {
		super();
		this.createdDtm = createdDtm;
		this.timeMode = timeMode;
		this.interiorCode = interiorCode;
		this.avgHumidVal = avgHumidVal;
		this.avgSmokeVal = avgSmokeVal;
		this.avgTempVal = avgTempVal;
		this.avgCoVal = avgCoVal;
		this.minHumidVal = minHumidVal;
		this.minSmokeVal = minSmokeVal;
		this.minTempVal = minTempVal;
		this.minCoVal = minCoVal;
		this.maxHumidVal = maxHumidVal;
		this.maxSmokeVal = maxSmokeVal;
		this.maxTempVal = maxTempVal;
		this.maxCoVal = maxCoVal;
		this.flameCnt = flameCnt;
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
	public long getAvgHumidVal() {
		return avgHumidVal;
	}
	public void setAvgHumidVal(long avgHumidVal) {
		this.avgHumidVal = avgHumidVal;
	}
	public long getAvgSmokeVal() {
		return avgSmokeVal;
	}
	public void setAvgSmokeVal(long avgSmokeVal) {
		this.avgSmokeVal = avgSmokeVal;
	}
	public long getAvgTempVal() {
		return avgTempVal;
	}
	public void setAvgTempVal(long avgTempVal) {
		this.avgTempVal = avgTempVal;
	}
	public long getAvgCoVal() {
		return avgCoVal;
	}
	public void setAvgCoVal(long avgCoVal) {
		this.avgCoVal = avgCoVal;
	}
	public long getMinHumidVal() {
		return minHumidVal;
	}
	public void setMinHumidVal(long minHumidVal) {
		this.minHumidVal = minHumidVal;
	}
	public long getMinSmokeVal() {
		return minSmokeVal;
	}
	public void setMinSmokeVal(long minSmokeVal) {
		this.minSmokeVal = minSmokeVal;
	}
	public long getMinTempVal() {
		return minTempVal;
	}
	public void setMinTempVal(long minTempVal) {
		this.minTempVal = minTempVal;
	}
	public long getMinCoVal() {
		return minCoVal;
	}
	public void setMinCoVal(long minCoVal) {
		this.minCoVal = minCoVal;
	}
	public long getMaxHumidVal() {
		return maxHumidVal;
	}
	public void setMaxHumidVal(long maxHumidVal) {
		this.maxHumidVal = maxHumidVal;
	}
	public long getMaxSmokeVal() {
		return maxSmokeVal;
	}
	public void setMaxSmokeVal(long maxSmokeVal) {
		this.maxSmokeVal = maxSmokeVal;
	}
	public long getMaxTempVal() {
		return maxTempVal;
	}
	public void setMaxTempVal(long maxTempVal) {
		this.maxTempVal = maxTempVal;
	}
	public long getMaxCoVal() {
		return maxCoVal;
	}
	public void setMaxCoVal(long maxCoVal) {
		this.maxCoVal = maxCoVal;
	}
	public long getFlameCnt() {
		return flameCnt;
	}
	public void setFlameCnt(long flameCnt) {
		this.flameCnt = flameCnt;
	}
	
}
