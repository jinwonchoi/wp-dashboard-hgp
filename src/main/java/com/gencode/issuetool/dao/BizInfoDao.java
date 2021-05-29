package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.obj.BizInfo;

public interface BizInfoDao extends Dao<BizInfo> {
	Optional<List<BizInfo>> loadAll(String langFlag);
	Optional<List<BizInfo>> search(String langFlag, Map<String, String> map);
}
