/**=========================================================================================
<overview>사용자 계정관련 업무처리서비스
  </overview>
==========================================================================================*/
package com.gencode.issuetool.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.NotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.dao.FileInfoDao;
import com.gencode.issuetool.dao.FileReferenceDao;
import com.gencode.issuetool.dao.UserDao;
import com.gencode.issuetool.dao.UserInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.FileReference;
import com.gencode.issuetool.obj.PasswdChange;
import com.gencode.issuetool.obj.User;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.storage.EmbedFileSystemStorageService;
import com.gencode.issuetool.storage.ProfileImgStorageService;

@Service
public class UserAccountService {
	private final static Logger logger = LoggerFactory.getLogger(UserAccountService.class);

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${password.encoding.use}")
	protected String passwordEncodingUse;

	/**
	 * 어드민이 사용자등록
	 * LoginId, email, agentId, name, department, group 등록 
	 * @param user
	 * @throws Exception 
	 */
	@Transactional
	public Optional<UserInfo> register(UserInfo userInfo) throws Exception {
		try { 
			//email등록된것이 있으면 오류
			if (hasLoginId(userInfo.getLoginId())) {
				throw new ApplicationException(ReturnCode.ALREADY_EXISTS_USERID);
			}
			
			if (hasUserEmail(userInfo.getUserEmail())) {
				throw new ApplicationException(ReturnCode.ALREADY_EXISTS_EMAIL);
			}
			logger.info(userInfo.getPasswd());
			if (passwordEncodingUse.equals("yes")) {
				userInfo.setPasswd(passwordEncoder.encode(userInfo.getPasswd()));
			} else {
				userInfo.setPasswd(Constant.DEFAULT_PASSWORD.get());
			}
			logger.info(userInfo.getPasswd());
			userInfo.setAuthKey(Utils.getRandomPassword());
			userInfo.setUserStatus(Constant.USER_INFO_REGISTERED.get());
			long userId = userInfoDao.register(userInfo);
			return userInfoDao.load(userId);
			//emailService.sendRegActivationMail(user.getEmail(), user.getUsername(), user.getUserid(),user.getEmailAuthKey());
		} catch (Exception e) {
			logger.error("register fail:"+userInfo, e);
			throw e;
		}
		
	}

	/**
	 * 사용자가 비번변경으로 사용활성화시킴
	 * @param userInfo
	 * @throws Exception 
	 */
	@Transactional
	public void evaluatePassword(PasswdChange passwdChange) throws Exception {
		UserInfo userInfo = null;
		try { 
			logger.info(passwdChange.toString());
			userInfo = userInfoDao.load(passwdChange.getUserId()).get();
			if (!userInfo.getPasswd().equals(passwdChange.getOldPassword())) {
				throw new ApplicationException(ReturnCode.PASSWORD_NOT_MATCH);
			}
			if (passwordEncodingUse.equals("yes")) {
				userInfo.setPasswd(passwordEncoder.encode(passwdChange.getNewPassword()));
			} else {
				userInfo.setPasswd(passwdChange.getNewPassword());
			}
			logger.info(userInfo.getPasswd());
			userInfo.setUserStatus(Constant.USER_INFO_ACTIVATED.get());
			userInfoDao.update(userInfo);
			//emailService.sendRegActivationMail(user.getEmail(), user.getUsername(), user.getUserid(),user.getEmailAuthKey());
		} catch (Exception e) {
			logger.error("register fail:"+userInfo, e);
			throw e;
		}
	}
	
	
	@Transactional
	public void deactivateUser(long userId) {
		try { 
			userInfoDao.delete(userId);
			//emailService.sendRegActivationMail(user.getEmail(), user.getUsername(), user.getUserid(),user.getEmailAuthKey());
		} catch (Exception e) {
			logger.error("deactivateUser fail:"+userId, e);
			throw e;
		}
	}

	public boolean hasLoginId(String loginId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginId", loginId);
		logger.info("userInfoDao.searchExtrict(map).get().size():"+userInfoDao.searchExtrict(map).get().size());
		return (userInfoDao.searchExtrict(map).get().size() > 0);
	}
	
	public boolean hasUserEmail(String userEmail) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userEmail", userEmail);		
		return (userInfoDao.searchExtrict(map).get().size() > 0);
	}
	
	@Transactional
	public boolean activate(UserInfo userInfo) {
		try {
			throw new NotSupportedException();
//			userInfo.setConfirmYn("Y");
//			userInfoDao.activate(userInfo);
//			return true;
		} catch (Exception e) {
			logger.error("activate fail:"+userInfo, e);
			return false;
		}
	}

	public void sendUseridNotification(String email) throws Exception {
//		Map<String, String> map = new HashMap<String, String>();
//		try {
//			map.put("email", email);		
//			Optional<List<User>> userList = userDao.search(map);
//			User user = userList.get().get(0);
//			//emailService.sendUseridNotification(user.getEmail(), user.getUserid(), user.getUsername());
//		} catch (Exception e) {
//			logger.error("userid notification fail:"+email, e);
//			throw e;
//		}
	}
	
	public void sendPasswordNotification(String userid, String email) throws Exception {
//		Map<String, String> map = new HashMap<String, String>();
//		User user = null;
//		try {
//			map.put("userid", userid);		
//			map.put("email", email);		
//			Optional<List<User>> userList = userDao.search(map);
//			user = userList.get().get(0);
//			String password = Utils.getRandomPassword();
//			user.setPassword(passwordEncoder.encode(password));
//			logger.debug(user.toString());
//			userDao.update(user);
//			//emailService.sendPasswordNotification(user.getEmail(), password, user.getUsername());
//		} catch (Exception e) {
//			logger.error("password notification fail:"+user, e);
//			throw e;
//		}
	}

	public Optional<UserInfo> load(long id) {
		return userInfoDao.load(id);
	}
	
	public Optional<List<UserInfo>> loadAll() {
		return userInfoDao.loadAll();
	}
	
	public Optional<List<UserInfo>> search(Map<String, String> map) {
		return userInfoDao.search(map);
	}
		
	/**
	 * 
	 * @param page
	 * @return
	 */
	public Optional<PageResultObj<List<UserInfo>>> search(PageRequest req) {
		return userInfoDao.search(req);
	}
	
	@Transactional
	public void delete(long id) {
		userInfoDao.delete(id);
	}

	@Transactional
	public Optional<UserInfo> update(UserInfo userInfo) throws Exception {
		if (userInfo.getPasswd()!=null&&!userInfo.getPasswd().trim().equals("")) {
			//TODO:		
			if (passwordEncodingUse.equals("yes")) {
				userInfo.setPasswd(passwordEncoder.encode(userInfo.getPasswd()));
			} else {
				userInfo.setPasswd(userInfo.getPasswd());
			}
		}
		userInfoDao.update(userInfo);
		return userInfoDao.load(userInfo.getId());
	}	
}
