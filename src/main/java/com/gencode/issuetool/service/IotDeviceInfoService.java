/**=========================================================================================
<overview>센서장비기준정보관련 업무처리서비스
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

import com.gencode.issuetool.dao.IotDeviceInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.IotDeviceInfo;

@Service
public class IotDeviceInfoService {
	private final static Logger logger = LoggerFactory.getLogger(IotDeviceInfoService.class);

	@Autowired
	private IotDeviceInfoDao iotDeviceInfoDao;
	
	@Autowired
	private PushService pushService;
	
	@Transactional
	public Optional<IotDeviceInfo> register(IotDeviceInfo t) {
		long iotDeviceId = iotDeviceInfoDao.register(t);
		pushService.sendMsg("all", Constant.PUSH_TAG_IOT_DEVICE_INFO_ADD.get(), iotDeviceInfoDao.load(iotDeviceId).get());
		return iotDeviceInfoDao.load(iotDeviceId);
	}

	@Transactional
	public Optional<IotDeviceInfo> update(IotDeviceInfo t)  throws NotFoundException {
		if (iotDeviceInfoDao.update(t) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_IOT_DEVICE_INFO_UPDATE.get(), iotDeviceInfoDao.load(t.getId()).get());
		return iotDeviceInfoDao.load(t.getId());
	}
	
	@Transactional
	public void delete(long id)  throws NotFoundException {
		if (iotDeviceInfoDao.delete(id) <=0) {
			throw new NotFoundException();
		}
		pushService.sendMsgAll(Constant.PUSH_TAG_IOT_DEVICE_INFO_DELETE.get(), Long.toString(id));
	}
	
	public Optional<IotDeviceInfo> load(long id) {
		return iotDeviceInfoDao.load(id);
	}
	
	public Optional<List<IotDeviceInfo>> loadAll() {
		return iotDeviceInfoDao.loadAll();
	}

	public Optional<PageResultObj<List<IotDeviceInfo>>> search(PageRequest req) {
		return iotDeviceInfoDao.search(req);
	}
}
