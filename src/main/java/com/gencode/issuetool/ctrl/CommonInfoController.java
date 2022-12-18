/**=========================================================================================
<overview>공통코드기준정보 관련 Controller
  </overview>
==========================================================================================*/
package com.gencode.issuetool.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.config.JwtTokenProvider;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.BaseRequestObj;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.BizInfo;
import com.gencode.issuetool.obj.CommonCode;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.service.CommonInfoService;
import com.gencode.issuetool.service.UserAccountService;
import com.gencode.issuetool.service.UserProfileService;

@RestController
@RequestMapping("/common")
@CrossOrigin(origins = "${cors_url}")
public class CommonInfoController {

	private final static Logger logger = LoggerFactory.getLogger(CommonInfoController.class);
	
	@Autowired
	private CommonInfoService commonInfoService;

	@RequestMapping("/list") 
	ResultObj<List<CommonCode>> getCommonCodeList() {
		try {
			Optional<List<CommonCode>> list = commonInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<CommonCode>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<CommonCode>>dataNotFound();
			}

		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/search")
	public ResultObj<List<CommonCode>> searchCommoncode(@RequestBody Map<String, String> map) {
		try {
			Optional<List<CommonCode>> list = commonInfoService.search(map);
			if (list.get().size() == 0) {
				return new ResultObj<List<CommonCode>>(ReturnCode.DATA_NOT_FOUND, null);
			} else {
				return new ResultObj<List<CommonCode>>(ReturnCode.SUCCESS, list.get());
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

}
