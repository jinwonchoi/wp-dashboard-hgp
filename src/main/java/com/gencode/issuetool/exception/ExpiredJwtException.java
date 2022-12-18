/**=========================================================================================
<overview>JWT유효기간만료 예외자
  </overview>
==========================================================================================*/
package com.gencode.issuetool.exception;

import io.jsonwebtoken.JwtException;

public class ExpiredJwtException extends JwtException {

	public ExpiredJwtException(String message) {
		super(message);
	}

}
