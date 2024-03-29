/**=========================================================================================
<overview>설비태그기준정보관련 업무처리서비스
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

import com.gencode.issuetool.dao.FacilTagInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.FacilTagInfo;

@Service
public class FacilTagInfoService {
	private final static Logger logger = LoggerFactory.getLogger(FacilTagInfoService.class);

	@Autowired
	private FacilTagInfoDao facilRuleInfoDao;
	
	@Autowired
	private PushService pushService;
	
	@Transactional
	public Optional<FacilTagInfo> register(FacilTagInfo t) {
		long iotRuleId = facilRuleInfoDao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_FACIL_RULE_INFO_ADD.get(), facilRuleInfoDao.load(iotRuleId).get());
		return facilRuleInfoDao.load(iotRuleId);
	}

	@Transactional
	public Optional<FacilTagInfo> update(FacilTagInfo t) throws NotFoundException {
		if (facilRuleInfoDao.update(t) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_FACIL_RULE_INFO_UPDATE.get(), facilRuleInfoDao.load(t.getId()).get());
		return facilRuleInfoDao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id) throws NotFoundException {
		if (facilRuleInfoDao.delete(id) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_FACIL_RULE_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<FacilTagInfo> load(long id) {
		return facilRuleInfoDao.load(id);
	}
	
	public Optional<List<FacilTagInfo>> loadAll() {
		return facilRuleInfoDao.loadAll();
	}

	public Optional<PageResultObj<List<FacilTagInfo>>> search(PageRequest req) {
		return facilRuleInfoDao.search(req);
	}
}
