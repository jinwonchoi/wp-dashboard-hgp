package com.gencode.issuetool.obj;

import java.math.BigDecimal;
import java.math.MathContext;

public class StatsGoal extends Pojo {
	long resolvedCount;
	long totalCount;
	double statsRate;
	
	public StatsGoal() {
		super();
	}
	
	public StatsGoal(long resolvedCount, long totalCount) {
		super();
		this.resolvedCount = resolvedCount;
		this.totalCount = totalCount;
		statsRate = BigDecimal.valueOf((float)(totalCount>0?(resolvedCount/totalCount):0)).round(new MathContext(2)).doubleValue();
	}

	public long getResolvedCount() {
		return resolvedCount;
	}

	public void setResolvedCount(long resolvedCount) {
		this.resolvedCount = resolvedCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public double getStatsRate() {
		return statsRate;
	}

	public void setStatsRate(double statsRate) {
		this.statsRate = statsRate;
	}
	
}
