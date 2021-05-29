package com.gencode.issuetool.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.dao.LoginHistoryDao;
import com.gencode.issuetool.dao.UserDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.obj.LoginHistory;
import com.gencode.issuetool.obj.User;
import com.gencode.issuetool.obj.UserInfo;

@Service
public class LoginHistoryService {
	private final static Logger logger = LoggerFactory.getLogger(LoginHistoryService.class);

	@Autowired
	private LoginHistoryDao loginHistoryDao;


	@Transactional
	public void register(UserInfo userInfo) throws TooManyRowException, IOException {
		LoginHistory loginHistory = new LoginHistory(userInfo.getId());
		loginHistoryDao.register(loginHistory);		
	}
	
	@Transactional
	public void logout(UserInfo userInfo) throws TooManyRowException, IOException {
		loginHistoryDao.load(userInfo.getId())
		.ifPresent(e-> {
			e.setLogoutType(Constant.LOGIN_HISTORY_NORMAL_LOGOUT.get());
			loginHistoryDao.update(e);		
		});
	}
	@Transactional
	public void timeout(UserInfo userInfo) throws TooManyRowException, IOException {
		loginHistoryDao.load(userInfo.getId())
		.ifPresent(e-> {
			e.setLogoutType(Constant.LOGIN_HISTORY_NORMAL_TIMEOUT.get());
			loginHistoryDao.update(e);		
		});
	}
	
}
