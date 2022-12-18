/**=========================================================================================
<overview>컬럼차트관련 주기선택처리
  </overview>
==========================================================================================*/
package com.gencode.issuetool.io;

import java.util.Map;

import com.gencode.issuetool.etc.Constant;

public class ColumnChartTimeMode {
	String strTime;
	String timeMode;
	String strDateFrmt;
	/**
	 * 최종건 삭제 후 재생성하기 위해 최종집계생성시간 리턴
	 * 최종건 없으면 삭제 스킵하도록 현재시간-1 리턴 
	 * timeMode=1D 1일단위 집계
	 * timeMode=1W 1주단위 집계
	 * timeMode=1m 1달단위 집계 ===> 미사용	 
	 * 단위: 1시간, 6시간, 1일, 1주, 1달
	 * 
	 * @param map
	 * @return
	 */
	public ColumnChartTimeMode(String timeMode) {
		super();
		this.timeMode=timeMode;
		if (timeMode.equals(Constant.DASHBOARD_STATS_TIME_MODE_1DAY.get())) {
			strTime= "1 day";
			strDateFrmt = "00:00";
		}else if (timeMode.equals(Constant.DASHBOARD_STATS_TIME_MODE_1WEEK.get())) {
			strTime= "1 week";
			strDateFrmt = "00:00";
			timeMode="1D";
		}else {//if (timeMode.equals("1m")) {
			strTime= "1 month";
			strDateFrmt = "00:00";
		}	
	}
	public String getStrDateFrmt() {
		return strDateFrmt;
	}
	public void setStrDateFrmt(String strDateFrmt) {
		this.strDateFrmt = strDateFrmt;
	}
	public String getStrTime() {
		return strTime;
	}
	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}
	public String getTimeMode() {
		return timeMode;
	}
	public void setTimeMode(String timeMode) {
		this.timeMode = timeMode;
	}

}
