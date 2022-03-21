package com.gencode.issuetool.prototype.dao;

import com.gencode.issuetool.prototype.obj.FileReference;

public interface PrtFileReferenceDao extends PrtDao<FileReference> {
	long forceDelete(long id);
	long delete(FileReference t);
	long forceDelete(FileReference t);
	//void completeUpload(long id, long fileId);
}
