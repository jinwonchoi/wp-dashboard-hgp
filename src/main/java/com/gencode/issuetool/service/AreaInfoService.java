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

import com.gencode.issuetool.dao.AreaInfoDao;
import com.gencode.issuetool.dao.NoticeBoardDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.AreaInfo;
import com.gencode.issuetool.obj.NoticeBoard;

@Service
public class AreaInfoService {
	private final static Logger logger = LoggerFactory.getLogger(AreaInfoService.class);

	@Autowired
	private AreaInfoDao areaInfoDao;
	
	@Autowired
	private PushService pushService;
	
	@Transactional
	public Optional<AreaInfo> register(AreaInfo t) {
		long areaId = areaInfoDao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_AREA_INFO_ADD.get(), areaInfoDao.load(areaId).get());
		return areaInfoDao.load(areaId);
	}

	@Transactional
	public Optional<AreaInfo> update(AreaInfo t) throws NotFoundException {
		if (areaInfoDao.update(t)<=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_AREA_INFO_UPDATE.get(), areaInfoDao.load(t.getId()).get());
		return areaInfoDao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id) throws NotFoundException {
		if (areaInfoDao.delete(id)<=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_AREA_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<AreaInfo> load(long id) {
		return areaInfoDao.load(id);
	}
	
	public Optional<List<AreaInfo>> loadAll() {
		return areaInfoDao.loadAll();
	}

	public Optional<PageResultObj<List<AreaInfo>>> search(PageRequest req) {
		return areaInfoDao.search(req);
	}
}
