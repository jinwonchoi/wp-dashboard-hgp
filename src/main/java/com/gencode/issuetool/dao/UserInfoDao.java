/**=========================================================================================
<overview>사용자기준정보관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.obj.UserInfo;

public interface UserInfoDao extends Dao<UserInfo> {
 	Optional<UserInfo> login(String loginId);
	void activate(UserInfo userInfo);
	long forceDelete(long id);
	Optional<List<UserInfo>> searchExtrict(Map<String, String> map);
}
