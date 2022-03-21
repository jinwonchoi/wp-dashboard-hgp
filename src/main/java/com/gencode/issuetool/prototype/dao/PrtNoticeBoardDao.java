package com.gencode.issuetool.prototype.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.prototype.obj.NoticeBoard;
import com.gencode.issuetool.prototype.obj.NoticeBoardEx;
import com.gencode.issuetool.prototype.obj.NoticeBoardWithFileList;

public interface PrtNoticeBoardDao extends PrtDao<NoticeBoard> {
	Optional<NoticeBoardEx> loadEx(long id);
	Optional<PageResultObj<List<NoticeBoardEx>>> searchEx(PageRequest req);
//	void incCommentCnt(long id);
//	void decCommentCnt(long id);
}
