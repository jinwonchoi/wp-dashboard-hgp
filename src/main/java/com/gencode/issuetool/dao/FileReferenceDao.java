package com.gencode.issuetool.dao;

import com.gencode.issuetool.obj.FileReference;

public interface FileReferenceDao extends Dao<FileReference> {
	void forceDelete(long id);
	void delete(FileReference t);
	void forceDelete(FileReference t);
	//void completeUpload(long id, long fileId);
}
