/**=========================================================================================
<overview>통계테이터 차트조회용 주기정보객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.io;

import java.util.Map;

import com.gencode.issuetool.etc.Constant;

public class StatsGenTimeMode {
	String strTime;
	String strDateFrmt;
	String timeMode;
	
	/**
	 * 최종건 삭제 후 재생성하기 위해 최종집계생성시간 리턴
	 * 최종건 없으면 삭제 스킵하도록 현재시간-1 리턴 

	 * timeMode=1M 1분단위 칩계
	 * timeMode=1H 1시간단위 집계
	 * timeMode=6H 6시간단위 집계
	 * timeMode=1D 1일단위 집계
	 * timeMode=1m 1달단위 집계 ===> 미사용	 
	 * 단위: 1시간, 6시간, 1일, 1주, 1달
	 * 
	 * @param map
	 * @return
	 */
	public StatsGenTimeMode(String timeMode) {
		super();
		this.timeMode=timeMode;
		if (timeMode.equals(Constant.DASHBOARD_STATS_TIME_MODE_1DAY.get())) {
			strTime= "1 day";
			strDateFrmt="00:00";
		}else if (timeMode.equals(Constant.DASHBOARD_STATS_TIME_MODE_6HOUR.get())) {
			strTime= "6 hour";
			strDateFrmt="%H:00";
		}else if (timeMode.equals(Constant.DASHBOARD_STATS_TIME_MODE_1HOUR.get())) {
			strTime= "1 hour";
			strDateFrmt="%H:00";
		}else {//if (timeMode.equals("1M")) {
			strTime= "1 minute";
			strDateFrmt="%H:%i";
		}	
	}
	
	public StatsGenTimeMode(String strTime, String strDateFrmt, String timeMode) {
		super();
		this.strTime = strTime;
		this.strDateFrmt = strDateFrmt;
		this.timeMode = timeMode;
	}
	public String getStrTime() {
		return strTime;
	}
	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}
	public String getStrDateFrmt() {
		return strDateFrmt;
	}
	public void setStrDateFrmt(String strDateFrmt) {
		this.strDateFrmt = strDateFrmt;
	}
	public String getTimeMode() {
		return timeMode;
	}
	public void setTimeMode(String timeMode) {
		this.timeMode = timeMode;
	}

}
