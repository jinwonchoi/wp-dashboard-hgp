package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.obj.IotFireIdx;

public interface IotFireIdxHistDao extends Dao<IotFireIdx> {
	void register(List<IotFireIdx> t);
	void deleteOld();
	Optional<List<IotFireIdx>> getDailyAverageByMonth();
	Optional<List<IotFireIdx>> getCountOverStableByMonth();
}
