/**=========================================================================================
<overview>센서별상태갑처리 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.obj.IotMain;

public interface IotMainHistDao extends Dao<IotMain> {
	int[] register(List<IotMain> t);
	Optional<List<IotMain>> getRealtimeChartData(PageRequest req);
	Optional<List<IotMain>> getRealtimeChartDataByInteriorList(PageRequest req);
	
	/**
	 * 7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	int cleanseData();
}
