/**=========================================================================================
<overview>센서장바현황 집계 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.obj.IotData;
import com.gencode.issuetool.obj.IotDataHistStat;


public interface IotDataHistStatDao extends Dao<IotDataHistStat> {
	int[] register(List<IotDataHistStat> t);
	/** 4. 분단위 등록
		4.1. 분단위 등록시 기존등록 삭제
		hst테이블=>stat테이블
	 */
	String getCreatedDtmPreMinuteDataGen();
	int cleansePreMinuteDataGen(String createdDtm); 	
	/** 4. 분단위 등록
	4.2. 최종분이후 현재분 등록
	hst테이블=>stat테이블
	 */
	int generateMinuteData(String createdDtm);

	/**
	 * 5. 시간단위 등록
		5.1. 시간단위 등록시 기존등록 삭제
		stat테이블=>stat테이블
	 */
	String getCreatedDtmPreHourlyDataGen();
	int cleansePreHourlyDataGen(String createdDtm);
	/**
	 * 5. 시간단위 등록
		5.2. 최종시간이후 현재시간 등록
		stat테이블=>stat테이블
	 */
	int generateHourlyData(String createdDtm);
	/**
	 * 5-1. 6시간단위 등록
		5.1. 6시간단위 등록시 기존등록 삭제
		stat테이블=>stat테이블
	 */
	String getCreatedDtmPre6HourlyDataGen();
	int cleansePre6HourlyDataGen(String createdDtm);
	/**
	 * 5-1. 6시간단위 등록
		5.2. 최종시간이후 현재시간 등록
		stat테이블=>stat테이블
	 */
	int generate6HourlyData(String createdDtm);
	/**
	 * 6. 일단위 등록
		6.1. 일단위 등록시 기존등록 삭제
	 */
	String getCreatedDtmPreDailyDataGen();
	int cleansePreDailyDataGen(String createdDtm);
	/**
	 * 6. 일단위 등록
		6.2.최종일자 이후 오늘자 등록
		stat테이블=>stat테이블
	 */
	int generateDailyData(String createdDtm);
	/**
	 * 7. hst테이블 클랜징
	1시간전 데이터 삭제
	stat최종이 1시간전데이터이면 삭제안함
	==> IotDataHistDao에서 처리
	*/
	/**
	 * 8. stat테이블 클랜징
		8.1. 분단위 => 1일전 데이터 삭제
		  - 시간단위가 1일전이면 삭제안함
	 */
	int cleanseMinuteData();
	/**
	 * 8. stat테이블 클랜징
		8.2. 시간단위=> 30일전 데이터 삭제
	 */
	int cleanseHourlyData();

	/**
	 * 기간별  
	 * @param map
	 * @return
	 */
	Optional<List<IotDataHistStat>> getStatsCnt(PageRequest req);
	/**
	 * 기간별 최근 30건 
	 * @param map
	 * @return
	 */
	Optional<List<IotData>> getRealtimeChartData(PageRequest req);
}
