/**=========================================================================================
<overview>로그프레소연동 대시보드 초기화면 처리객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.logpresso.obj;

import java.util.List;

import com.gencode.issuetool.obj.Pojo;

public class DashboardObj extends Pojo {
	String defaultMain;
	String tagMain;
	String tagFireIdx;
	
	String iotMain;
	String iotData;
	String iotFireIdx;
	String itemList;
	public String getDefaultMain() {
		return defaultMain;
	}
	public void setDefaultMain(String defaultMain) {
		this.defaultMain = defaultMain;
	}

	public String getTagMain() {
		return tagMain;
	}
	public void setTagMain(String tagMain) {
		this.tagMain = tagMain;
	}
	public String getTagFireIdx() {
		return tagFireIdx;
	}
	public void setTagFireIdx(String tagFireIdx) {
		this.tagFireIdx = tagFireIdx;
	}
	public String getIotMain() {
		return iotMain;
	}
	public void setIotMain(String iotMain) {
		this.iotMain = iotMain;
	}
	
	public String getIotData() {
		return iotData;
	}
	public void setIotData(String iotData) {
		this.iotData = iotData;
	}
	public String getIotFireIdx() {
		return iotFireIdx;
	}
	public void setIotFireIdx(String iotFireIdx) {
		this.iotFireIdx = iotFireIdx;
	}
	public String getItemList() {
		return itemList;
	}
	public void setItemList(String itemList) {
		this.itemList = itemList;
	}
}
