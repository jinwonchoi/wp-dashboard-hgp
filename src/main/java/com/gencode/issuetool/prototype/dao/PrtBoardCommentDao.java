package com.gencode.issuetool.prototype.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.prototype.obj.BoardComment;

public interface PrtBoardCommentDao extends PrtDao<BoardComment> {
	Optional<BoardComment> load(long id);
	Optional<PageResultObj<List<BoardComment>>> search(PageRequest req);	
}
