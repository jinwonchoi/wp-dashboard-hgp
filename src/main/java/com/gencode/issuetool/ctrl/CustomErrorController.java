package com.gencode.issuetool.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.User;

@RestController
@CrossOrigin(origins = "${cors_url}")
public class CustomErrorController {//implements ErrorController {

	private final static Logger logger = LoggerFactory.getLogger(CustomErrorController.class);
		
/*
	@RequestMapping(method=RequestMethod.POST, value="/user/testmail")
	ResultObj<String> testMail(@RequestBody User user) {	
 */
//	@RequestMapping("/error")
//	String error(@RequestBody String errorCode) {
//		logger.info("errorCode:"+errorCode);
//		return "error";
//	}
//	
//	@RequestMapping("/error")
//	String error(@RequestBody String errorCode) {
//		logger.info("errorCode:======================================>");
//
//		logger.info("errorCode:======================================>"+errorCode);
//		return "error";
//	}
	
	@RequestMapping(method=RequestMethod.GET, value="/test")
	ResultObj<String> test() {
		logger.info("/error/test");
		ResultObj<String> result = new ResultObj<String>();
		result.setResultCode("1111");
		result.setResultMsg("1111Message");
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/maxfilesize")
	ResultObj<String> exceedMaxFileSize() {
		logger.info("/error/maxfilesize");
		ResultObj<String> result = new ResultObj<String>();
		result.setResultCode("1111");
		result.setResultMsg("1111Message");
		return result;
	}

//	@Override
//	public String getErrorPath() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
