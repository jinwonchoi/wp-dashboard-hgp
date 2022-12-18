/**=========================================================================================
<overview>게시판관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.NoticeBoardWithFileList;

public interface NoticeBoardDao extends Dao<NoticeBoard> {
	Optional<NoticeBoardEx> loadEx(long id);
	Optional<PageResultObj<List<NoticeBoardEx>>> searchEx(PageRequest req);
	void incReadCnt(long id);
	Optional<List<NoticeBoard>> searchMyNotice(Map<String, String> map);
	
//	void decCommentCnt(long id);
}
