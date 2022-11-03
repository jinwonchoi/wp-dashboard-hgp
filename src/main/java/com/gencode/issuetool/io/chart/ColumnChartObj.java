package com.gencode.issuetool.io.chart;

import java.util.List;

import com.gencode.issuetool.obj.Pojo;

public class ColumnChartObj extends Pojo {

	List<Object> categories;
	List<ColumnChartSeriesItem> series;
	
	
	public ColumnChartObj() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public ColumnChartObj(List<Object> categories, List<ColumnChartSeriesItem> series) {
		super();
		this.categories = categories;
		this.series = series;
	}

	public List<Object> getCategories() {
		return categories;
	}
	public void setCategories(List<Object> categories) {
		this.categories = categories;
	}
	public List<ColumnChartSeriesItem> getSeries() {
		return series;
	}
	public void setSeries(List<ColumnChartSeriesItem> series) {
		this.series = series;
	}
	
}
