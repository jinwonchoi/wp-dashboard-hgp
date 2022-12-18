/**=========================================================================================
<overview>파일처리관련 NotFound예외처리자
  </overview>
==========================================================================================*/
package com.gencode.issuetool.exception;

public class StorageFileNotFoundException extends StorageException {

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
