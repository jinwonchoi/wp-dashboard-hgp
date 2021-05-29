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

import com.gencode.issuetool.service.UserAccountService;
import com.gencode.issuetool.service.UserProfileService;
import com.gencode.issuetool.config.JwtTokenProvider;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.BaseRequestObj;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.PasswdChange;
import com.gencode.issuetool.obj.UserInfo;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "${cors_url}")
public class UserController {

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserAccountService userAccountService;

	@RequestMapping("/{id}") 
	ResultObj<UserInfo> getUser(@PathVariable(name="id") String id) {
		try {
			Optional<UserInfo> userInfo = userAccountService.load(Long.parseLong(id));
			return new ResultObj<UserInfo>(ReturnCode.SUCCESS, userInfo.get());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	@RequestMapping("/list") 
	ResultObj<List<UserInfo>> getList() {
		try {
			logger.debug("getList");
			return userAccountService.loadAll().map(t->(t.size()>0)
					?ResultObj.<List<UserInfo>>success(t)
							:ResultObj.<List<UserInfo>>error(ReturnCode.USER_NOT_FOUND)).get();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/search")
	public ResultObj<List<UserInfo>> searchUser(@RequestBody Map<String, String> map) {
		try {
			System.out.println(map.toString());
			Optional<List<UserInfo>> list = userAccountService.search(map);
			if (list.get().size() == 0) {
				return new ResultObj<List<UserInfo>>(ReturnCode.USER_NOT_FOUND, null);
			} else {
				return new ResultObj<List<UserInfo>>(ReturnCode.SUCCESS, list.get());
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return null;
		}
	}

	@RequestMapping(method=RequestMethod.POST, value="/page")
	public PageResultObj<List<UserInfo>> pageUser(@RequestBody PageRequest pageRequest) {
		try {
			logger.info(pageRequest.toString());
			Optional<PageResultObj<List<UserInfo>>> list = userAccountService.search(pageRequest);
			if (list.get().getItem().size() == 0) {
				return new PageResultObj<List<UserInfo>>(ReturnCode.USER_NOT_FOUND, null);
			} else {
				return new PageResultObj<List<UserInfo>>(ReturnCode.SUCCESS, list.get());
			}
//		} catch (ApplicationException ae) {
//			return new PageResultObj<List<UserInfo>>(ae.getReturnCode());
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<UserInfo>>errorUnknown();
		}
	}
	
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/update")
	ResultObj<UserInfo> updateUser(@RequestBody UserInfo user) {
		try {
			Optional<UserInfo> userInfo = userAccountService.update(user);
			return new ResultObj<UserInfo>(ReturnCode.SUCCESS, userInfo.get());
		} catch (ApplicationException ae) {
			return ResultObj.error(ae.getReturnCode());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/check/loginid/{loginId}")
	ResultObj<String> checkLoginId(@PathVariable(name="loginId") String loginId) {
		try {
			if (userAccountService.hasLoginId(loginId)) {
				return new ResultObj<String>(ReturnCode.ALREADY_EXISTS_USERID);
			} else {
				return ResultObj.success();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/check/useremail")
	ResultObj<String> checkUserEmail(@RequestBody BaseRequestObj userEmailObj) {
		try {
			if (userAccountService.hasUserEmail(userEmailObj.getSimpleStr())) {
				return new ResultObj<String>(ReturnCode.ALREADY_EXISTS_EMAIL);
			} else {
				return ResultObj.success();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/add")
	ResultObj<UserInfo> addUser(@RequestBody UserInfo user) {
		try {
			UserInfo resultUserInfo = userAccountService.register(user).get();
			return new ResultObj<UserInfo>(ReturnCode.SUCCESS, resultUserInfo);
		} catch (ApplicationException ae) {
			return ResultObj.error(ae.getReturnCode());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	/**
	 * 
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	ResultObj<String> deleteUser(@PathVariable(name="id") String id) {
		try {
			userAccountService.delete(Long.parseLong(id));
			return ResultObj.success();
//		} catch (ApplicationException ae) {
//			return ResultObj.error(ae.getReturnCode());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	/** 
	 * file만 업로드하고 url을 리턴
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/profile/upload", method=RequestMethod.POST,  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	ResultObj<UserInfo> uploadProfileImage(@RequestPart("json") UserInfo userInfo, @RequestPart("upfile") MultipartFile multipartFile) {
		try {			
			logger.debug(multipartFile.getOriginalFilename());
			logger.debug(multipartFile.getSize()+"");
			Optional<UserInfo> resultUserInfo = userProfileService.uploadProfileImage(multipartFile, userInfo);
			return new ResultObj<UserInfo>(ReturnCode.SUCCESS, resultUserInfo.get());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	/**
	 * 프로파일이미지 삭제 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/profile/delete/{id}", method=RequestMethod.POST)
	ResultObj<String> deleteProfileImage(@PathVariable(name="id") String id) {
		try {
			userProfileService.deleteProfileImage(Long.parseLong(id));
			return ResultObj.success();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	@RequestMapping("/profile/{id}") 
	ResultObj<UserInfo> getUserProfile(@PathVariable(name="id") String id) {
		try {
			Optional<UserInfo> userInfo = userProfileService.getUserProfile(Long.parseLong(id));
			return new ResultObj<UserInfo>(ReturnCode.SUCCESS, userInfo.get());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/profile/update")
	ResultObj<UserInfo> updateUserProfile(@RequestBody UserInfo user) {
		try {			
			Optional<UserInfo> userInfo = userProfileService.updateUserProfile(user);
			return new ResultObj<UserInfo>(ReturnCode.SUCCESS, userInfo.get());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	@RequestMapping(method=RequestMethod.POST, value="/changepwd")
	ResultObj<String> changePassword(@RequestBody PasswdChange passwdChange) {
		try {			
			userAccountService.evaluatePassword(passwdChange);
			return new ResultObj<String>(ReturnCode.SUCCESS);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
}
