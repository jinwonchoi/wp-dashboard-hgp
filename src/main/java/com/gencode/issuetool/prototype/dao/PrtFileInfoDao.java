package com.gencode.issuetool.prototype.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.prototype.obj.FileInfo;

public interface PrtFileInfoDao extends PrtDao<FileInfo> {
	void forceDelete(long id);
	void completeUpload(long id);
	Optional<List<FileInfo>> getUncompletedFiles(long registerId);
	Optional<List<FileInfo>> getFilesByRefId(long refId, String refType);

}
