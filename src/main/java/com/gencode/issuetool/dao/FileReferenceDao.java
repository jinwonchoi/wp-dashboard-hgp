/**=========================================================================================
<overview>게시판첨부파일맵핑정보관련  DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import com.gencode.issuetool.obj.FileReference;

public interface FileReferenceDao extends Dao<FileReference> {
	long forceDelete(long id);
	long delete(FileReference t);
	long forceDelete(FileReference t);
	//void completeUpload(long id, long fileId);
}
