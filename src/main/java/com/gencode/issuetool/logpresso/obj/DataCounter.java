package com.gencode.issuetool.logpresso.obj;

import com.gencode.issuetool.obj.Pojo;

public class DataCounter extends Pojo {
	int maxCount;
	int count;
	String minTime;
	
	public DataCounter(int maxCount) {
		super();
		this.maxCount = maxCount;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public int getCount() {
		return count;
	}
	public String getMinTime() {
		return minTime;
	}
	public void setMinTime(String minTime) {
		this.count++;
		if (this.count >= this.maxCount || this.count == 0) {
			this.minTime = minTime;
		}
	}
	
	

}
