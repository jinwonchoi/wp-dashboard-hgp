/**=========================================================================================
<overview>기준정보 캐시맵 관리자
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

import javax.annotation.PostConstruct;

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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.dao.AreaInfoDao;
import com.gencode.issuetool.dao.BizInfoDao;
import com.gencode.issuetool.dao.CommonCodeDao;
import com.gencode.issuetool.dao.EtcDeviceInfoDao;
import com.gencode.issuetool.dao.FacilTagInfoDao;
import com.gencode.issuetool.dao.FacilityInfoDao;
import com.gencode.issuetool.dao.InteriorInfoDao;
import com.gencode.issuetool.dao.IotDeviceInfoDao;
import com.gencode.issuetool.dao.PlantInfoDao;
import com.gencode.issuetool.dao.PlantPartInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.AreaInfo;
import com.gencode.issuetool.obj.BizInfo;
import com.gencode.issuetool.obj.CommonCode;
import com.gencode.issuetool.obj.EtcDeviceInfo;
import com.gencode.issuetool.obj.FacilTagInfo;
import com.gencode.issuetool.obj.FacilityInfo;
import com.gencode.issuetool.obj.InteriorInfo;
import com.gencode.issuetool.obj.IotDeviceInfo;
import com.gencode.issuetool.obj.PlantInfo;
import com.gencode.issuetool.obj.PlantPartInfo;

@Service
public class CacheMapManager {
	private final static Logger logger = LoggerFactory.getLogger(CacheMapManager.class);

	@Autowired
	private CommonCodeDao commonCodeDao;
	@Autowired
	private PlantInfoDao plantInfoDao;
	@Autowired
	private AreaInfoDao areaInfoDao;
	@Autowired
	private InteriorInfoDao interiorInfoDao;
	@Autowired
	private PlantPartInfoDao plantPartInfoDao;
	@Autowired
	private IotDeviceInfoDao iotDeviceInfoDao;
	@Autowired
	private EtcDeviceInfoDao etcDeviceInfoDao;
	@Autowired
	private FacilTagInfoDao facilTagInfoDao;
	@Autowired
	private FacilityInfoDao facilityInfoDao;

	Map<String, CommonCode> mapCommonCodeByCode = new HashMap<String, CommonCode>();
	Map<String, PlantInfo> mapPlantInfoByCode = new HashMap<String, PlantInfo>();
	Map<String, PlantPartInfo> mapPlantPartInfoByCode = new HashMap<String, PlantPartInfo>();
	Map<String, AreaInfo> mapAreaInfoByCode = new HashMap<String, AreaInfo>();
	Map<Integer, InteriorInfo> mapInteriorInfo = new HashMap<Integer, InteriorInfo>();
	Map<String, InteriorInfo> mapInteriorInfoByCode = new HashMap<String, InteriorInfo>();
	Map<String, FacilityInfo> mapFacilityInfoByCode = new HashMap<String, FacilityInfo>();
	Map<String, FacilTagInfo> mapFacilTagInfoByCode = new HashMap<String, FacilTagInfo>();
	Map<String, IotDeviceInfo> mapIotDeviceInfoByCode = new HashMap<String, IotDeviceInfo>();
	Map<String, EtcDeviceInfo> mapEtcDeviceInfoByCode = new HashMap<String, EtcDeviceInfo>();
	
	@PostConstruct
	public void loadAll() {
		commonCodeDao.loadAll().get().forEach(e -> {
			mapCommonCodeByCode.put(e.getGroupId()+":"+e.getItemKey(), e);
		});
		plantInfoDao.loadAll().get().forEach(e -> {
			mapPlantInfoByCode.put(e.getPlantNo(), e);
		});
		plantPartInfoDao.loadAll().get().forEach(e -> {
			PlantInfo plantInfo = mapPlantInfoByCode.values().stream().filter(pi -> ((PlantInfo)pi).getId()==((PlantPartInfo)e).getPlantId()).findFirst().get();
			mapPlantPartInfoByCode.put(plantInfo.getPlantNo()+":"+e.getPlantPartCode(), e);
		});
		areaInfoDao.loadAll().get().forEach(e -> {
			mapAreaInfoByCode.put(e.getAreaCode(), e);
		});
		interiorInfoDao.loadAll().get().forEach(e -> {
			mapInteriorInfoByCode.put(e.getInteriorCode(), e);
			mapInteriorInfo.put(new Integer((int)e.getId()), e);
		});
		facilityInfoDao.loadAll().get().forEach(e -> {
			mapFacilityInfoByCode.put(e.getFacilCode(), e);
		});
		facilTagInfoDao.loadAll().get().forEach(e -> {
			mapFacilTagInfoByCode.put(e.getTagName(), e);
		});
		iotDeviceInfoDao.loadAll().get().forEach(e -> {
			mapIotDeviceInfoByCode.put(e.getDeviceId(), e);
		});
		etcDeviceInfoDao.loadAll().get().forEach(e -> {
			mapEtcDeviceInfoByCode.put(e.getDeviceId(), e);
		});
	}
	
	public <T> void update(T t) {
		if (t instanceof CommonCode) {
			mapCommonCodeByCode.put(((CommonCode)t).getGroupId()+":"+((CommonCode)t).getItemKey(), (CommonCode)t);
		} else if (t instanceof PlantInfo) {
			mapPlantInfoByCode.put(((PlantInfo)t).getPlantNo(), (PlantInfo)t);
		} else if (t instanceof PlantPartInfo) {
			mapPlantPartInfoByCode.put(mapPlantInfoByCode.get(((PlantPartInfo)t).getPlantId()).getPlantNo()
					+":"+((PlantPartInfo)t).getPlantPartCode(), (PlantPartInfo)t);
		} else if (t instanceof AreaInfo) {
			mapAreaInfoByCode.put(((AreaInfo)t).getAreaCode(), (AreaInfo)t);
		} else if (t instanceof InteriorInfo) {
			mapInteriorInfoByCode.put(((InteriorInfo)t).getInteriorCode(), (InteriorInfo)t);
			mapInteriorInfo.put(new Integer((int)((InteriorInfo)t).getId()), (InteriorInfo)t);
		} else if (t instanceof FacilityInfo) {
			mapFacilityInfoByCode.put(((FacilityInfo)t).getFacilCode(), (FacilityInfo)t);
		} else if (t instanceof FacilTagInfo) {
			mapFacilTagInfoByCode.put(((FacilTagInfo)t).getTagName(), (FacilTagInfo)t);
		} else if (t instanceof IotDeviceInfo) {
			mapIotDeviceInfoByCode.put(((IotDeviceInfo)t).getDeviceId(), (IotDeviceInfo)t);
		} else if (t instanceof EtcDeviceInfo) {
			mapEtcDeviceInfoByCode.put(((EtcDeviceInfo)t).getDeviceId(), (EtcDeviceInfo)t);
		}
	}

	public <T> void delete(T t) {
		if (t instanceof CommonCode) {
			mapCommonCodeByCode.remove(((CommonCode)t).getGroupId()+":"+((CommonCode)t).getItemKey());
		} else if (t instanceof PlantInfo) {
			mapPlantInfoByCode.remove(((PlantInfo)t).getPlantNo());
		} else if (t instanceof PlantPartInfo) {
			mapPlantPartInfoByCode.remove(mapPlantInfoByCode.get(((PlantPartInfo)t).getPlantId()).getPlantNo()
					+":"+((PlantPartInfo)t).getPlantPartCode());
		} else if (t instanceof AreaInfo) {
			mapAreaInfoByCode.remove(((AreaInfo)t).getAreaCode());
		} else if (t instanceof InteriorInfo) {
			mapInteriorInfoByCode.remove(((InteriorInfo)t).getInteriorCode());
			mapInteriorInfo.remove(((InteriorInfo)t).getId());
		} else if (t instanceof FacilityInfo) {
			mapFacilityInfoByCode.remove(((FacilityInfo)t).getFacilCode());
		} else if (t instanceof FacilTagInfo) {
			mapFacilTagInfoByCode.remove(((FacilTagInfo)t).getTagName());
		} else if (t instanceof IotDeviceInfo) {
			mapIotDeviceInfoByCode.remove(((IotDeviceInfo)t).getDeviceId());
		} else if (t instanceof EtcDeviceInfo) {
			mapEtcDeviceInfoByCode.remove(((EtcDeviceInfo)t).getDeviceId());
		}
	}

	
//	public static<T> T getVal(Object o, Class<T> clazz) throws ClassCastException {
//		return clazz.cast(o);
//	}
	
//	public static<T> T getVal(Object o) throws ClassCastException {
//		Class<T> clazz = ;
//		return clazz.cast(o);
//	}

	public Map<String, CommonCode> getMapCommonCodeByCode() {
		return mapCommonCodeByCode;
	}

	public void setMapCommonCodeByCode(Map<String, CommonCode> mapCommonCodeByCode) {
		this.mapCommonCodeByCode = mapCommonCodeByCode;
	}

	public Map<String, PlantInfo> getMapPlantInfoByCode() {
		return mapPlantInfoByCode;
	}

	public void setMapPlantInfoByCode(Map<String, PlantInfo> mapPlantInfoByCode) {
		this.mapPlantInfoByCode = mapPlantInfoByCode;
	}

	public Map<String, PlantPartInfo> getMapPlantPartInfoByCode() {
		return mapPlantPartInfoByCode;
	}

	public void setMapPlantPartInfoByCode(Map<String, PlantPartInfo> mapPlantPartInfoByCode) {
		this.mapPlantPartInfoByCode = mapPlantPartInfoByCode;
	}

	public Map<String, AreaInfo> getMapAreaInfoByCode() {
		return mapAreaInfoByCode;
	}

	public void setMapAreaInfoByCode(Map<String, AreaInfo> mapAreaInfoByCode) {
		this.mapAreaInfoByCode = mapAreaInfoByCode;
	}

	public Map<String, InteriorInfo> getMapInteriorInfoByCode() {
		return mapInteriorInfoByCode;
	}

	public void setMapInteriorInfoByCode(Map<String, InteriorInfo> mapInteriorInfoByCode) {
		this.mapInteriorInfoByCode = mapInteriorInfoByCode;
	}

	public Map<Integer, InteriorInfo> getMapInteriorInfo() {
		return mapInteriorInfo;
	}

	public void setMapInteriorInfo(Map<Integer, InteriorInfo> mapInteriorInfo) {
		this.mapInteriorInfo = mapInteriorInfo;
	}

	public Map<String, FacilityInfo> getMapFacilityInfoByCode() {
		return mapFacilityInfoByCode;
	}

	public void setMapFacilityInfoByCode(Map<String, FacilityInfo> mapFacilityInfoByCode) {
		this.mapFacilityInfoByCode = mapFacilityInfoByCode;
	}

	public Map<String, FacilTagInfo> getMapFacilTagInfoByCode() {
		return mapFacilTagInfoByCode;
	}

	public void setMapFacilTagInfoByCode(Map<String, FacilTagInfo> mapFacilTagInfoByCode) {
		this.mapFacilTagInfoByCode = mapFacilTagInfoByCode;
	}

	public Map<String, IotDeviceInfo> getMapIotDeviceInfoByCode() {
		return mapIotDeviceInfoByCode;
	}

	public void setMapIotDeviceInfoByCode(Map<String, IotDeviceInfo> mapIotDeviceInfoByCode) {
		this.mapIotDeviceInfoByCode = mapIotDeviceInfoByCode;
	}

	public Map<String, EtcDeviceInfo> getMapEtcDeviceInfoByCode() {
		return mapEtcDeviceInfoByCode;
	}

	public void setMapEtcDeviceInfoByCode(Map<String, EtcDeviceInfo> mapEtcDeviceInfoByCode) {
		this.mapEtcDeviceInfoByCode = mapEtcDeviceInfoByCode;
	}

	
	
}
