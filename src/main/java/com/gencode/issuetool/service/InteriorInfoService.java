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

import com.gencode.issuetool.dao.InteriorInfoDao;
import com.gencode.issuetool.dao.NoticeBoardDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.InteriorInfo;
import com.gencode.issuetool.obj.NoticeBoard;

@Service
public class InteriorInfoService {
	private final static Logger logger = LoggerFactory.getLogger(InteriorInfoService.class);

	@Autowired
	private InteriorInfoDao interiorInfoao;
	
	@Autowired
	private PushService pushService;
	
	@Transactional
	public Optional<InteriorInfo> register(InteriorInfo t) {
		long interiorId = interiorInfoao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_INTERIOR_INFO_ADD.get(), interiorInfoao.load(interiorId).get());
		return interiorInfoao.load(interiorId);
	}

	@Transactional
	public Optional<InteriorInfo> update(InteriorInfo t) throws NotFoundException {
		if (interiorInfoao.update(t) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_INTERIOR_INFO_UPDATE.get(), interiorInfoao.load(t.getId()).get());
		return interiorInfoao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id) throws NotFoundException {
		if (interiorInfoao.delete(id) <=0) {
			throw new NotFoundException();
		}		
		pushService.sendMsgAll(Constant.PUSH_TAG_INTERIOR_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<InteriorInfo> load(long id) {
		return interiorInfoao.load(id);
	}
	
	public Optional<List<InteriorInfo>> loadAll() {
		return interiorInfoao.loadAll();
	}

	public Optional<PageResultObj<List<InteriorInfo>>> search(PageRequest req) {
		return interiorInfoao.search(req);
	}
}
