/**=========================================================================================
<overview>시계열차트 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.io.chart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gencode.issuetool.obj.Pojo;

public class RealtimeChartObj extends Pojo {

	List<String> categories;
	List<RealtimeChartSeriesItem> series;
	String maxValLimit;
	Map<String, String> maxValLimitMap = new HashMap<String, String>();
	
	public RealtimeChartObj() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public RealtimeChartObj(List<String> categories, List<RealtimeChartSeriesItem> series, String maxValLimit) {
		super();
		this.categories = categories;
		this.series = series;
		this.maxValLimit = maxValLimit;
	}

	public RealtimeChartObj(List<String> categories, List<RealtimeChartSeriesItem> series, Map<String, String> maxValLimitMap) {
		super();
		this.categories = categories;
		this.series = series;
		this.maxValLimitMap = maxValLimitMap;
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
	public String getMaxValLimit() {
		return maxValLimit;
	}
	public void setMaxValLimit(String maxValLimit) {
		this.maxValLimit = maxValLimit;
	}

	public Map<String, String> getMaxValLimitMap() {
		return maxValLimitMap;
	}

	public void setMaxValLimitMap(Map<String, String> maxValLimitMap) {
		this.maxValLimitMap = maxValLimitMap;
	}	
	
}
