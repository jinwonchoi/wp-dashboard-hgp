package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.obj.IotData;

public interface IotDataHistDao extends Dao<IotData> {
	int[] register(List<IotData> t);
	Optional<List<IotData>> getRealtimeChartData(PageRequest req);
	/**
	 * 7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	int cleanseData();
}
