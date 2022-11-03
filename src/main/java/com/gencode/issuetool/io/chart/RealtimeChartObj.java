package com.gencode.issuetool.io.chart;

import java.util.List;

import com.gencode.issuetool.obj.Pojo;

public class RealtimeChartObj extends Pojo {

	List<String> categories;
	List<RealtimeChartSeriesItem> series;
	
	
	public RealtimeChartObj() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public RealtimeChartObj(List<String> categories, List<RealtimeChartSeriesItem> series) {
		super();
		this.categories = categories;
		this.series = series;
	}

	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<RealtimeChartSeriesItem> getSeries() {
		return series;
	}
	public void setSeries(List<RealtimeChartSeriesItem> series) {
		this.series = series;
	}
	
}
