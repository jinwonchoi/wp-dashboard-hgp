/**=========================================================================================
<overview>센서장비화재지수관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.obj.IotFireIdx;

public interface IotFireIdxHistDao extends Dao<IotFireIdx> {
	void register(List<IotFireIdx> t);
	void deleteOld();
	Optional<List<IotFireIdx>> getRealtimeChartData(PageRequest req);;
	Optional<List<IotFireIdx>> getCountOverStableByMonth();
	/**
	 * 7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	int cleanseData();
}
