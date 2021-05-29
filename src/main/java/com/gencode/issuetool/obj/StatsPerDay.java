package com.gencode.issuetool.obj;

public class StatsPerDay extends Pojo {
	String statsDate;
	int cnt;
	
	public StatsPerDay() {
		super();
	}
	
	public StatsPerDay(String statsDate, int cnt) {
		super();
		this.statsDate = statsDate;
		this.cnt = cnt;
	}

	public String getStatsDate() {
		return statsDate;
	}
	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}
