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

import com.gencode.issuetool.dao.IotRuleInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.IotRuleInfo;

@Service
public class IotRuleInfoService {
	private final static Logger logger = LoggerFactory.getLogger(IotRuleInfoService.class);

	@Autowired
	private IotRuleInfoDao iotRuleInfoDao;
	
	@Autowired
	private PushService pushService;
	
	@Transactional
	public Optional<IotRuleInfo> register(IotRuleInfo t) {
		long iotRuleId = iotRuleInfoDao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_IOT_RULE_INFO_ADD.get(), iotRuleInfoDao.load(iotRuleId).get());
		return iotRuleInfoDao.load(iotRuleId);
	}

	@Transactional
	public Optional<IotRuleInfo> update(IotRuleInfo t) throws NotFoundException {
		if (iotRuleInfoDao.update(t) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_IOT_RULE_INFO_UPDATE.get(), iotRuleInfoDao.load(t.getId()).get());
		return iotRuleInfoDao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id) throws NotFoundException {
		if (iotRuleInfoDao.delete(id) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_IOT_RULE_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<IotRuleInfo> load(long id) {
		return iotRuleInfoDao.load(id);
	}
	
	public Optional<List<IotRuleInfo>> loadAll() {
		return iotRuleInfoDao.loadAll();
	}

	public Optional<PageResultObj<List<IotRuleInfo>>> search(PageRequest req) {
		return iotRuleInfoDao.search(req);
	}
}
