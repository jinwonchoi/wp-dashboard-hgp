package com.gencode.issuetool.io.chart;

import java.util.List;
import com.gencode.issuetool.obj.Pojo;


public class RealtimeChartSeriesItem extends Pojo {
	String name;
	List<List<String>> data;
	
	public RealtimeChartSeriesItem(String name, List<List<String>> data) {
		super();
		this.name = name;
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<List<String>> getData() {
		return data;
	}
	public void setData(List<List<String>> data) {
		this.data = data;
	}
	
}
