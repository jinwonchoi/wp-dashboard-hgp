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

import com.gencode.issuetool.dao.FacilTagMappingDao;
import com.gencode.issuetool.obj.FacilTagMapping;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;

@Service
public class FacilTagMappingService {
	private final static Logger logger = LoggerFactory.getLogger(FacilTagMappingService.class);

	@Autowired
	private FacilTagMappingDao facilTagMappingDao;
	
	@Autowired
	private PushService pushService;
	
	@Transactional
	public Optional<FacilTagMapping> register(FacilTagMapping t) {
		facilTagMappingDao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_FACIL_TAG_MAPPING_ADD.get(), t);
		return Optional.of(t);
	}

	@Transactional
	public Optional<FacilTagMapping> update(FacilTagMapping t) throws NotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@Transactional
	public void delete(FacilTagMapping t) throws NotFoundException {
		if (facilTagMappingDao.delete(t) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_FACIL_TAG_MAPPING_DELETE.get(), t);
	}
	
	@Transactional
	public void deleteByRuleId(long id) throws NotFoundException {
		if (facilTagMappingDao.deleteByRuleId(id) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_FACIL_TAG_MAPPING_DELETE_BY_RULE_ID.get(), id);
	}
	
	public Optional<FacilTagMapping> load(long id) {
		throw new UnsupportedOperationException();
	}
	
	public Optional<List<FacilTagMapping>> loadAll() {
		return facilTagMappingDao.loadAll();
	}

	public Optional<PageResultObj<List<FacilTagMapping>>> search(PageRequest req) {
		return facilTagMappingDao.search(req);
	}
}
