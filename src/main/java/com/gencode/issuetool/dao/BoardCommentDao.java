/**=========================================================================================
<overview>게시판댓글관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.obj.BoardComment;

public interface BoardCommentDao extends Dao<BoardComment> {
	Optional<BoardComment> load(long id);
	Optional<PageResultObj<List<BoardComment>>> search(PageRequest req);	
}
