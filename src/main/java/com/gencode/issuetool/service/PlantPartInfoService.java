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
import com.gencode.issuetool.dao.PlantPartInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.PlantPartInfo;

@Service
public class PlantPartInfoService {
	private final static Logger logger = LoggerFactory.getLogger(PlantPartInfoService.class);

	@Autowired
	private PlantPartInfoDao plantPartInfoDao;
	
	@Autowired
	private PushService pushService;
	
	@Transactional
	public Optional<PlantPartInfo> register(PlantPartInfo t) {
		long areaId = plantPartInfoDao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_PLANT_PART_INFO_ADD.get(), plantPartInfoDao.load(areaId).get());
		return plantPartInfoDao.load(areaId);
	}

	@Transactional
	public Optional<PlantPartInfo> update(PlantPartInfo t) throws NotFoundException {
		if (plantPartInfoDao.update(t)<=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_PLANT_PART_INFO_UPDATE.get(), plantPartInfoDao.load(t.getId()).get());
		return plantPartInfoDao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id) throws NotFoundException {
		if (plantPartInfoDao.delete(id)<=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_PLANT_PART_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<PlantPartInfo> load(long id) {
		return plantPartInfoDao.load(id);
	}
	
	public Optional<List<PlantPartInfo>> loadAll() {
		return plantPartInfoDao.loadAll();
	}

	public Optional<PageResultObj<List<PlantPartInfo>>> search(PageRequest req) {
		return plantPartInfoDao.search(req);
	}
}
