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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.obj.BizInfo;
import com.gencode.issuetool.obj.CommonCode;
import com.gencode.issuetool.dao.BizInfoDao;
import com.gencode.issuetool.dao.CommonCodeDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;

@Service
public class CommonInfoService {
	private final static Logger logger = LoggerFactory.getLogger(CommonInfoService.class);

	@Autowired
	private CommonCodeDao commonCodeDao;
	@Autowired
	private BizInfoDao bizInfoDao;
	
	public Optional<List<CommonCode>> loadAll(String langFlag) {
		return commonCodeDao.loadAll(langFlag);
	}

	public Optional<List<CommonCode>> loadAll() {
		return commonCodeDao.loadAll();
	}

	public Optional<List<CommonCode>> search(String langFlag, Map<String, String> req) {
		return commonCodeDao.search(langFlag, req);
	}

	public Optional<List<CommonCode>> search(Map<String, String> req) {
		return commonCodeDao.search(req);
	}
	
	public Optional<List<BizInfo>> loadAllBizInfo(String langFlag) {
		return bizInfoDao.loadAll(langFlag);
	}

	public Optional<List<BizInfo>> searchBizInfo(String langFlag, Map<String, String> req) {
		return bizInfoDao.search(langFlag, req);
	}

}
