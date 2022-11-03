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

import com.gencode.issuetool.dao.NoticeBoardDao;
import com.gencode.issuetool.dao.PlantInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.PlantInfo;

@Service
public class PlantInfoService {
	private final static Logger logger = LoggerFactory.getLogger(PlantInfoService.class);

	@Autowired
	private PlantInfoDao plantInfoDao;
	
	@Autowired
	private PushService pushService;
	
	@Transactional
	public Optional<PlantInfo> register(PlantInfo t) {
		long plantId = plantInfoDao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_PLANT_INFO_ADD.get(), plantInfoDao.load(plantId).get());
		return plantInfoDao.load(plantId);
	}

	@Transactional
	public Optional<PlantInfo> update(PlantInfo t) throws Exception {
		if (plantInfoDao.update(t) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_PLANT_INFO_UPDATE.get(), plantInfoDao.load(t.getId()).get());
		return plantInfoDao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id) throws Exception {
		if (plantInfoDao.delete(id) <=0) {
			throw new NotFoundException();
		}		
		pushService.sendMsgAll(Constant.PUSH_TAG_PLANT_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<PlantInfo> load(long id) {
		return plantInfoDao.load(id);
	}
	
	public Optional<List<PlantInfo>> loadAll() {
		return plantInfoDao.loadAll();
	}

	public Optional<PageResultObj<List<PlantInfo>>> search(PageRequest req) {
		return plantInfoDao.search(req);
	}
}
