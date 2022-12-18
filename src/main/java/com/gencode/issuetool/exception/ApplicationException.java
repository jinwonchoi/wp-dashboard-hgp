/**=========================================================================================
<overview>일반 비즈니스 예외자 클래스
  </overview>
==========================================================================================*/
package com.gencode.issuetool.exception;

import com.gencode.issuetool.etc.ReturnCode;

public class ApplicationException extends Exception {
	ReturnCode returnCode;
	public ApplicationException(ReturnCode returnCode) {
		this.returnCode = returnCode;
	}
	
	public ReturnCode getReturnCode() {
		return this.returnCode;
	}
}
