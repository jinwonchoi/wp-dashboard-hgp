/**=========================================================================================
<overview>설비태그현황상태값관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.obj.TagData;

public interface TagDataHistDao extends Dao<TagData> {
	int[] register(List<TagData> t);
	Optional<List<TagData>> getRealtimeChartData(PageRequest req);
	/**
	 * 7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	int cleanseData();
}
