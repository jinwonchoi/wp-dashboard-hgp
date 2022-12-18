/**=========================================================================================
<overview>공통코드기준정보관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.obj.CommonCode;

public interface CommonCodeDao extends Dao<CommonCode> {
	Optional<List<CommonCode>> loadAll(String langFlag);
	Optional<List<CommonCode>> search(String langFlag, Map<String, String> map);
	Optional<PageResultObj<List<CommonCode>>> search(String langFlag, PageRequest req);
}
