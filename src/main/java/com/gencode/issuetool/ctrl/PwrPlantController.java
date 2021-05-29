package com.gencode.issuetool.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.gencode.issuetool.service.DashBoardService;
import com.gencode.issuetool.service.MyUserDetailsService;
import com.gencode.issuetool.config.JwtTokenProvider;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.GroupSum;
import com.gencode.issuetool.obj.StatsGoal;
import com.gencode.issuetool.obj.StatsPerDay;
import com.gencode.issuetool.obj.UserInfo;

@RestController
@RequestMapping("/pwrplant")
@CrossOrigin(origins = "${cors_url}")
public class PwrPlantController {

	private final static Logger logger = LoggerFactory.getLogger(PwrPlantController.class);
	
	@Autowired
	private DashBoardService dashBoardService;
	
	
	/* 사업부 사용자 인입 건수
	 * @return StatsPerDay 
	 *
	 */    
	@RequestMapping("/api/card/card-statistics/subscribers") 
	ResultObj<List<StatsPerDay>> getCustomerInboundCount() {
//		try {
//			Optional<List<StatsPerDay>> statsPerDay = dashBoardService.getCustomerInboundCount(bizId);
//			return new ResultObj<List<StatsPerDay>>(ReturnCode.SUCCESS, statsPerDay.get());
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
		return null;
	}

}
