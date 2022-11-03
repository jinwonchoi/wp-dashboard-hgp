package com.gencode.issuetool.obj;

public class IotMain extends Pojo {
	protected String createdDtm;
	protected String interiorCode;
	protected long avgHumidVal;
	protected long avgSmokeVal;
	protected long avgTempVal;
	protected long avgCoVal;
	protected long avgFlame;
	protected long maxHumidVal;
	protected long maxSmokeVal;
	protected long maxTempVal;
	protected long maxCoVal;
	protected long maxFlame;
	
	public IotMain() {
		super();
	}
	
	public IotMain(String createdDtm, String interiorCode, long avgHumidVal, long avgSmokeVal, long avgTempVal,
			long avgCoVal, long avgFlame, long maxHumidVal, long maxSmokeVal, long maxTempVal, long maxCoVal,
			long maxFlame) {
		super();
		this.createdDtm = createdDtm;
		this.interiorCode = interiorCode;
		this.avgHumidVal = avgHumidVal;
		this.avgSmokeVal = avgSmokeVal;
		this.avgTempVal = avgTempVal;
		this.avgCoVal = avgCoVal;
		this.avgFlame = avgFlame;
		this.maxHumidVal = maxHumidVal;
		this.maxSmokeVal = maxSmokeVal;
		this.maxTempVal = maxTempVal;
		this.maxCoVal = maxCoVal;
		this.maxFlame = maxFlame;
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
	public long getAvgFlame() {
		return avgFlame;
	}
	public void setAvgFlame(long avgFlame) {
		this.avgFlame = avgFlame;
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
	public long getMaxFlame() {
		return maxFlame;
	}
	public void setMaxFlame(long maxFlame) {
		this.maxFlame = maxFlame;
	}

}
