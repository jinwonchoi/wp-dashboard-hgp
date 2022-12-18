/**=========================================================================================
<overview>설비태그화재지수관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.obj.TagFireIdx;

public interface TagFireIdxHistDao extends Dao<TagFireIdx> {
	void register(List<TagFireIdx> t);
	void deleteOld();
	Optional<List<TagFireIdx>> getRealtimeChartData(PageRequest req);
	//Optional<List<TagFireIdx>> getDailyAverageByMonth(Map<String, String> map);
	Optional<List<TagFireIdx>> getCountOverStableByMonth();
	/**
	 * 7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	int cleanseData();

}
