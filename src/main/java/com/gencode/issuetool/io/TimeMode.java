/**=========================================================================================
<overview>조회시 주기별 쿼리조건처리 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.io;

import java.util.Map;

public class TimeMode {
	String strTime;
	String strDateFrmt;
	String timeMode;
	
	/**
	 * timeMode=5S 기본실시간
	 * timeMode=1M 1분단위 칩계
	 * timeMode=1H 1시간단위 집계
	 * timeMode=6H 6시간단위 집계
	 * timeMode=1D 1일단위 집계
	 * timeMode=1m 1달단위 집계 ===> 미사용	 
	 * 단위: 1시간, 6시간, 1일, 1주, 1달
	 * 
	 * " where created_dtm >= date_format(DATE_SUB(NOW(), INTERVAL "+strTime+"),'%Y-%m-%d "+strDateFrmt+"') and time_mode='"+timeMode+"' " +
	 * @param map
	 * @return
	 */
	public TimeMode(Map map) {
		super();
		int realTimeCount = Integer.parseInt((String)map.get("realtimeCount"))+1;
		timeMode = (String)map.get("timeMode");
		if (timeMode.equals("1H")) {
			this.strTime = realTimeCount+" hour";
			this.strDateFrmt = "%H:00";
		} else if (timeMode.equals("6H")) {
			this.strTime = (realTimeCount*6)+" hour";
			this.strDateFrmt = "%H:00";
		} else if (timeMode.equals("1D")) {
			this.strTime = realTimeCount+" day";
			this.strDateFrmt = "00:00";
//		} else if (map.get("timeMode").equals("1m")) {
//			strTime = "1 month";
//			strDateFrmt = "%H:00";
//			timeMode="1D";
		} else  {//if (mode.equals("M")) {
			this.strTime = realTimeCount+" minute";
			this.strDateFrmt = "%H:%i";
		}
	}
	
	public TimeMode(String strTime, String strDateFrmt, String timeMode) {
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
