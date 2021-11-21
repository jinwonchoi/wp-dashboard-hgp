package com.gencode.issuetool.dao;

import com.gencode.issuetool.obj.FileReference;

public interface FileReferenceDao extends Dao<FileReference> {
	long forceDelete(long id);
	long delete(FileReference t);
	long forceDelete(FileReference t);
	//void completeUpload(long id, long fileId);
}
