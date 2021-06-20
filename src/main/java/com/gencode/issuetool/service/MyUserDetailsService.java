package com.gencode.issuetool.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.NotSupportedException;

//import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gencode.issuetool.dao.UserInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
//import com.gencode.issuetool.mail.EmailService;
import com.gencode.issuetool.obj.UserInfo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	private final static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	@Autowired
	private UserInfoDao userInfoDao;
	
	//@Autowired
	//private EmailService emailService;
		
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		logger.info("loadUserByUsername:loingId="+loginId);
		Optional<UserInfo> userInfo = userInfoDao.login(loginId);
		if (!userInfo.isPresent()) {
			throw new UsernameNotFoundException("User not found by loginId:"+loginId);
		}
		return toUserDetails(userInfo.get());
	}

	private UserDetails toUserDetails(UserInfo userInfo) {
		return org.springframework.security.core.userdetails.User.withUsername(userInfo.getLoginId())
				.password(userInfo.getPasswd())
				.authorities(userInfo.getLevel()).build();
	}
	
	public Optional<UserInfo> load(String loginId) {
		return userInfoDao.login(loginId);
	}
	}
