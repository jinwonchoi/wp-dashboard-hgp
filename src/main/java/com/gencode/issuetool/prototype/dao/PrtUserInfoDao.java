package com.gencode.issuetool.prototype.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.prototype.obj.UserInfo;

public interface PrtUserInfoDao extends PrtDao<UserInfo> {
 	Optional<UserInfo> login(String loginId);
	void activate(UserInfo userInfo);
	long forceDelete(long id);
	Optional<List<UserInfo>> searchExtrict(Map<String, String> map);
}
