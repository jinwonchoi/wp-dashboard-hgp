package com.gencode.issuetool.ctrl;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.gencode.issuetool.service.LoginHistoryService;
import com.gencode.issuetool.service.MyUserDetailsService;

import io.jsonwebtoken.impl.DefaultClaims;

import com.gencode.issuetool.config.JwtTokenProvider;
import com.gencode.issuetool.dao.LoginHistoryDao;
import com.gencode.issuetool.dao.LoginUserDao;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.obj.AuthUserInfo;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "${cors_url}",allowedHeaders = "Authorization, content-type", allowCredentials = "false") //https://stackoverflow.com/questions/59836005/receiving-null-authorization-header-in-spring-boot-from-requests-with-angular-7
//@CrossOrigin(origins = "${cors_url}")
//@CrossOrigin(origins = "http://192.168.0.104:8082")
public class AuthController {

	private final static Logger logger = LoggerFactory.getLogger(AuthController.class);
	
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
//    @Autowired
//    private PasswordEncoder bCryptPasswordEncoder;
    
	@Autowired
	private MyUserDetailsService userService;
	
	@Autowired
	private LoginUserDao loginUserDao;
	
	@Autowired
	private LoginHistoryService loginHistoryService;

	@Value("${issuetool.home.url}") String homeUrl;
	@Value("${issuetool.error.url}") String errorUrl;
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	ResultObj<AuthUserInfo> login(@RequestBody UserInfo userInfo) {
		try {
			logger.info(userInfo.toString());
			String loginId = userInfo.getLoginId();
//			String passwdBCrypt = bCryptPasswordEncoder.encode(user.getPassword());
//			logger.info(bCryptPasswordEncoder.encode(user.getPassword()));
//			user.setPassword(passwdBCrypt);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginId, userInfo.getPasswd()));
			// todo: role은 나중에 추가하기로 함.
			
			Optional<UserInfo> returnUser = userService.load(loginId);
			String token = jwtTokenProvider.createToken(loginId, "user");
			AuthUserInfo authUserInfo = new AuthUserInfo(returnUser.get(), token);
			loginHistoryService.register(returnUser.get());
			ResultObj<AuthUserInfo> resultObj = ResultObj.success();
			resultObj.setItem(authUserInfo);
			return resultObj;
		} catch (AuthenticationException au) {
			logger.error(String.format("auth error: loginId[%s]passwd[%s]", userInfo.getLoginId(), userInfo.getPasswd()));
			return new ResultObj(ReturnCode.INVALID_AUTH);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	/**
	 * https://www.javainuse.com/spring/ang7-jwt
	 * @param req
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/refreshToken")
	ResultObj<String> refreshToken(HttpServletRequest req, @RequestBody UserInfo userInfo) {
		try {
			DefaultClaims claims = (DefaultClaims)req.getAttribute("claims");
			
			
			Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
			String token = jwtTokenProvider.createRefreshToken(claims, expectedMap.get("sub").toString());
			ResultObj<String> resultObj = ResultObj.success();
			resultObj.setItem(token);
			return resultObj;
		} catch (AuthenticationException au) {
			logger.error(String.format("refreshToken error: loginId[%s]", userInfo.getLoginId()));
			return new ResultObj(ReturnCode.INVALID_AUTH);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object>entry: claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/logout")
	ResultObj<String> logout(@RequestBody UserInfo userInfo) {
		try {
			loginHistoryService.logout(userInfo);
			ResultObj<String> resultObj = ResultObj.success();
			return resultObj;
		} catch (AuthenticationException au) {
			logger.error(String.format("logout error: loginId[%s]passwd[%s]", userInfo.getLoginId(), userInfo.getPasswd()));
			return new ResultObj(ReturnCode.INVALID_AUTH);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

//	@RequestMapping(method=RequestMethod.POST, value="/register")
//	ResultObj<String> register(@RequestBody String userId) {
//		try {
//			loginUserDao.register(userId);
//			ResultObj<AuthUserInfo> resultObj = ResultObj.success();
//			return ResultObj.success();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
//	}

//
//	@RequestMapping(method=RequestMethod.GET, value="/user/checkdup/userid/{userid}")
//	ResultObj<String> checkDupUserid(@PathVariable("userid") String userid) {
//		try {
//			if (userService.hasUserid(userid)) {
//				return new ResultObj(ReturnCode.ALREADY_EXISTS_USERID);
//			} else {
//				return ResultObj.success();
//			}		
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
//	}
//
//	@RequestMapping(method=RequestMethod.GET, value="/user/checkdup/email/{email}")
//	ResultObj<String> checkDupEmail(@PathVariable("email") String email) {
//		try {
//			if (userService.hasEmail(email)) {
//				return new ResultObj(ReturnCode.ALREADY_EXISTS_EMAIL);
//			} else {
//				return ResultObj.success();
//			}		
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
//	}
//	
//    /**
//     *  최초 사용자 정보 등록 
//     *  1. user정보 등록
//     *  2. 계정 활성화 메일 전송
//     * @param user
//     * @return
//     */
//	@RequestMapping(method=RequestMethod.POST, value="/user/register")
//	ResultObj<String> registerUser(@RequestBody User user) {
//		try {
//			userService.register(user);
//			return ResultObj.success();
//		} catch (ApplicationException ae) {
//			logger.error("normal error", ae);
//			return new ResultObj<String>(ae.getReturnCode());
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="/user/testmail")
//	ResultObj<String> testMail(@RequestBody User user) {
//		try {
////			String passwdBCrypt = bCryptPasswordEncoder.encode(user.getPassword());
////			user.setPassword(passwdBCrypt);
//			user.setEmailAuthKey(Utils.getRandomPassword());
//			user.setEmailAuthYn("N");
//			emailService.sendRegActivationMail(user.getEmail(), user.getUsername(), user.getUserid(), user.getEmailAuthKey());
//			return ResultObj.success();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
//	}
//	
//	@RequestMapping(method=RequestMethod.GET, value="/user/activate/{userid}/{emailAuthKey}")
//	void activateUser(@PathVariable("userid") String userid, 
//			@PathVariable("emailAuthKey") String emailAuthKey,
//			HttpServletResponse response) {
//		try {
//			User user = new User();
//			user.setUserid(userid);
//			user.setEmailAuthKey(emailAuthKey);
//			if (userService.activate(user)) {
//				response.sendRedirect(homeUrl);	
//			} else {
//				Map<String,String> templateArgMap = new HashMap<String,String>();
//				templateArgMap.put("errorCode", ReturnCode.INVALID_AUTH.get());
//				templateArgMap.put("errorMsg", ReturnCode.STR_INVALID_AUTH.get());
//				
//				String text = mailContentBuilder.build(templateArgMap, "error"); 
//				
//				response.setContentType("text/html");
//				response.setCharacterEncoding("UTF-8");
//				response.setStatus(HttpServletResponse.SC_OK);
//				response.getWriter().write(text);
//				response.getWriter().flush();
//				response.getWriter().close();
//			}
//		} catch (Exception e) {
//			logger.error("normal error", e);
//		}
//	}
//
//	@RequestMapping(method=RequestMethod.POST, value="/user/reactivate")
//	ResultObj<String> reactivateUser(@RequestBody User user) {
//		try {
//			user.setDeleteYn("N");
//			userService.update(user);
//			return ResultObj.success();
//		} catch (ApplicationException ae) {
//			logger.error("normal error", ae);
//			return new ResultObj(ae.getReturnCode());
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="/user/notify/userid")
//	ResultObj<String> sendUserIdNotification(@RequestBody User user) {
//		try {
//			userService.sendUseridNotification(user.getEmail());
//			return ResultObj.success();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="/user/notify/password")
//	ResultObj<String> sendPasswordNotification(@RequestBody User user) {
//		try {
//			userService.sendPasswordNotification(user.getUserid(), user.getEmail());
//			return ResultObj.success();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		}
//	}

}
