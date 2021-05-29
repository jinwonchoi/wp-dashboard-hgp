package com.gencode.issuetool.exception;

import io.jsonwebtoken.JwtException;

public class ExpiredJwtException extends JwtException {

	public ExpiredJwtException(String message) {
		super(message);
	}

}
