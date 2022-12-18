/**=========================================================================================
<overview>파일처리관련 예외처리자
  </overview>
==========================================================================================*/
package com.gencode.issuetool.exception;

public class StorageException extends RuntimeException {
	
	public StorageException(String message) {
		super(message);
	}
	
	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

}
