package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.obj.TagFireIdx;

public interface TagFireIdxHistDao extends Dao<TagFireIdx> {
	void register(List<TagFireIdx> t);
	void deleteOld();
	Optional<List<TagFireIdx>> getDailyAverageByMonth(Map<String, String> map);
	Optional<List<TagFireIdx>> getCountOverStableByMonth();
}
