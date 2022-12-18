/**=========================================================================================
<overview>메소드 미지원 예외처리자
  </overview>
==========================================================================================*/
package com.gencode.issuetool.exception;

public class MethodUnsupportableException extends RuntimeException {
	
	public MethodUnsupportableException(String message) {
		super(message);
	}
	
	public MethodUnsupportableException(String message, Throwable cause) {
		super(message, cause);
	}

}
