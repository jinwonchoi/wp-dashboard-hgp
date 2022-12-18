/**=========================================================================================
<overview>게시판첨부파일처리관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.obj.FileInfo;

public interface FileInfoDao extends Dao<FileInfo> {
	long forceDelete(long id);
	long completeUpload(long id);
	Optional<List<FileInfo>> getUncompletedFiles(long registerId);
	Optional<List<FileInfo>> getFilesByRefId(long refId, String refType);

}
