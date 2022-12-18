/**=========================================================================================
<overview>커럼차트용 x축 항목객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.io.chart;

import java.util.List;
import com.gencode.issuetool.obj.Pojo;


public class ColumnChartSeriesItem extends Pojo {
	String name;
	List<String> data;
	
	public ColumnChartSeriesItem(String name, List<String> data) {
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
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	
}
