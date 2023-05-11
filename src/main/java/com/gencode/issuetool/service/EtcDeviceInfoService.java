/**=========================================================================================
<overview>기타장비기준정보관련 업무처리서비스
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

import com.gencode.issuetool.dao.EtcDeviceInfoDao;
import com.gencode.issuetool.dao.NoticeBoardDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.EtcDeviceInfo;
import com.gencode.issuetool.obj.NoticeBoard;

@Service
public class EtcDeviceInfoService {
	private final static Logger logger = LoggerFactory.getLogger(EtcDeviceInfoService.class);

	@Autowired
	private EtcDeviceInfoDao etcDeviceInfoDao;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private CacheMapManager cacheMapManager; 
	
	@Transactional
	public Optional<EtcDeviceInfo> register(EtcDeviceInfo t) {
		long etcDeviceId = etcDeviceInfoDao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_ETC_DEVICE_INFO_ADD.get(), etcDeviceInfoDao.load(etcDeviceId).get());
		return etcDeviceInfoDao.load(etcDeviceId);
	}

	@Transactional
	public Optional<EtcDeviceInfo> update(EtcDeviceInfo t)throws NotFoundException {
		if (etcDeviceInfoDao.update(t) <=0) {
			throw new NotFoundException();
		}		
		pushService.sendMsgAll(Constant.PUSH_TAG_ETC_DEVICE_INFO_UPDATE.get(), etcDeviceInfoDao.load(t.getId()).get());
		return etcDeviceInfoDao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id) throws NotFoundException {
		if (etcDeviceInfoDao.delete(id) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_ETC_DEVICE_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<EtcDeviceInfo> load(long id) {
		return etcDeviceInfoDao.load(id);
	}
	
	public Optional<List<EtcDeviceInfo>> loadAll() {
		return etcDeviceInfoDao.loadAll();
	}

	public Optional<PageResultObj<List<EtcDeviceInfo>>> search(PageRequest req) {
		return etcDeviceInfoDao.search(req);
	}
}
