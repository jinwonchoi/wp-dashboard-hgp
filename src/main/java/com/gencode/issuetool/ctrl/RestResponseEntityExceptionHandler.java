package com.gencode.issuetool.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.io.ResultObj;

@RestControllerAdvice(annotations = RestController.class)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	private final static Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);
   // 413 MultipartException - file size too big 
	@ExceptionHandler(MaxUploadSizeExceededException.class)
		//{MultipartException.class,FileSizeLimitExceededException.class,java.lang.IllegalStateException.class})
   public ResultObj<String> handleSizeExceededException(final WebRequest request, final MaxUploadSizeExceededException ex) {
        //log.warn("413 Status Code. File size too large {}", ex.getMessage());
		logger.error("Max file size error", ex);
		return new ResultObj<String>(ReturnCode.FILE_MAX_SIZE_OVER);
   }
}