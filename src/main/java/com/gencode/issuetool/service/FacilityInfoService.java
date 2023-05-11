/**=========================================================================================
<overview>설비기준정보관련 업무처리서비스
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

import com.gencode.issuetool.dao.FacilityInfoDao;
import com.gencode.issuetool.dao.NoticeBoardDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.FacilityInfo;
import com.gencode.issuetool.obj.NoticeBoard;

@Service
public class FacilityInfoService {
	private final static Logger logger = LoggerFactory.getLogger(FacilityInfoService.class);

	@Autowired
	private FacilityInfoDao facilityInfoDao;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private CacheMapManager cacheMapManager; 
	@Transactional
	public Optional<FacilityInfo> register(FacilityInfo t) {
		long facilityId = facilityInfoDao.register(t);
		FacilityInfo facilityInfo = facilityInfoDao.load(facilityId).get();
		pushService.sendMsg("all", Constant.PUSH_TAG_FACILITY_INFO_ADD.get(), facilityInfo);
		cacheMapManager.insert(facilityInfo);
		return facilityInfoDao.load(facilityId);
	}

	@Transactional
	public Optional<FacilityInfo> update(FacilityInfo t) throws NotFoundException {
		if (facilityInfoDao.update(t) <=0) {
			throw new NotFoundException();
		}
		FacilityInfo facilityInfo = facilityInfoDao.load(t.getId()).get();
		pushService.sendMsgAll(Constant.PUSH_TAG_FACILITY_INFO_UPDATE.get(), facilityInfo);
		cacheMapManager.update(facilityInfo);
		return facilityInfoDao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id)throws NotFoundException {
		if (facilityInfoDao.delete(id) <=0) {
			throw new NotFoundException();
		}		
		cacheMapManager.delete(facilityInfoDao.load(id).get());
		pushService.sendMsgAll(Constant.PUSH_TAG_FACILITY_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<FacilityInfo> load(long id) {
		return facilityInfoDao.load(id);
	}
	
	public Optional<List<FacilityInfo>> loadAll() {
		return facilityInfoDao.loadAll();
	}

	public Optional<PageResultObj<List<FacilityInfo>>> search(PageRequest req) {
		return facilityInfoDao.search(req);
	}
}
