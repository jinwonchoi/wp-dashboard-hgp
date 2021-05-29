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
