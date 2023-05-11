/**=========================================================================================
<overview>기준정보 캐시맵 관리자
  </overview>
==========================================================================================*/
package com.gencode.issuetool.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
	Map<Long, AreaInfo> mapAreaInfo = new HashMap<Long, AreaInfo>();
	Map<String, AreaInfo> mapAreaInfoByCode = new HashMap<String, AreaInfo>();
	Map<Long, AreaInfo> mapAreaInfoForIot = new HashMap<Long, AreaInfo>();
	Map<String, AreaInfo> mapAreaInfoByCodeForIot = new HashMap<String, AreaInfo>();
	Map<Long, AreaInfo> mapAreaInfoForCctv = new HashMap<Long, AreaInfo>();
	Map<String, AreaInfo> mapAreaInfoByCodeForCctv = new HashMap<String, AreaInfo>();
	Map<Long, InteriorInfo> mapInteriorInfo = new HashMap<Long, InteriorInfo>();
	Map<String, InteriorInfo> mapInteriorInfoByCode = new HashMap<String, InteriorInfo>();
	Map<Long, InteriorInfo> mapInteriorInfoForIot = new HashMap<Long, InteriorInfo>();
	Map<String, InteriorInfo> mapInteriorInfoByCodeForIot = new HashMap<String, InteriorInfo>();
	Map<Long, InteriorInfo> mapInteriorInfoForCctv = new HashMap<Long, InteriorInfo>();
	Map<String, InteriorInfo> mapInteriorInfoByCodeForCctv = new HashMap<String, InteriorInfo>();
	Map<String, FacilityInfo> mapFacilityInfoByCode = new HashMap<String, FacilityInfo>();
	Map<String, FacilTagInfo> mapFacilTagInfoByCode = new HashMap<String, FacilTagInfo>();
	Map<String, IotDeviceInfo> mapIotDeviceInfoByCode = new HashMap<String, IotDeviceInfo>();
	Map<String, EtcDeviceInfo> mapEtcDeviceInfoByCode = new HashMap<String, EtcDeviceInfo>();

	List<CommonCode> commonCodes = new ArrayList<CommonCode>();
	List<PlantInfo> plantInfos = new ArrayList<PlantInfo>();
	List<PlantPartInfo> plantPartInfos = new ArrayList<PlantPartInfo>();
	List<AreaInfo> areaInfos = new ArrayList<AreaInfo>();
	List<AreaInfo> areaInfosForIot = new ArrayList<AreaInfo>();
	List<AreaInfo> areaInfosForCctv = new ArrayList<AreaInfo>();
	List<InteriorInfo> interiorInfos = new ArrayList<InteriorInfo>();
	List<InteriorInfo> interiorInfosForIot = new ArrayList<InteriorInfo>();
	List<InteriorInfo> interiorInfosForCctv = new ArrayList<InteriorInfo>();
	List<FacilityInfo> facilityInfos = new ArrayList<FacilityInfo>();
	List<FacilTagInfo> facilTagInfos = new ArrayList<FacilTagInfo>();
	List<IotDeviceInfo> iotDeviceInfos = new ArrayList<IotDeviceInfo>();
	List<EtcDeviceInfo> etcDeviceInfos = new ArrayList<EtcDeviceInfo>();
	
	String interiorsStr;//"ST4","ST3","SC3","SC2","SC1","SU1","SI2","SI1","SI0"
	String plantTypeStr="BFP,TBN";
	String plantNosStr="M1,M2,MC";
	String plantPartsStr="FP,MAA,MAV,MAB,MAK,MKD";
	String areaCodesStr="TURBIN,MCTRL,UNDERCBL,INVERTER";

	@PostConstruct
	public void loadAll() {
		commonCodeDao.loadAll().get().forEach(e -> {
			mapCommonCodeByCode.put(e.getGroupId()+":"+e.getItemKey(), e);
			commonCodes.add(e);
		});
		plantInfoDao.loadAll().get().forEach(e -> {
			mapPlantInfoByCode.put(e.getPlantNo(), e);
			plantInfos.add(e);
		});
		
		StringBuffer sbPlantPart = new StringBuffer();
		plantPartInfoDao.loadAll().get().forEach(e -> {
			PlantInfo plantInfo = mapPlantInfoByCode.values().stream().filter(pi -> ((PlantInfo)pi).getId()==((PlantPartInfo)e).getPlantId()).findFirst().get();
			mapPlantPartInfoByCode.put(plantInfo.getPlantNo()+":"+e.getPlantPartCode(), e);
			plantPartInfos.add(e);
			sbPlantPart.append((sbPlantPart.length()==0)?e.getPlantPartCode():(","+e.getPlantPartCode()));
		});
		plantPartsStr = sbPlantPart.toString();
		
		StringBuffer sbArea = new StringBuffer();
		areaInfoDao.loadAll().get().forEach(e -> {
			mapAreaInfoByCode.put(e.getAreaCode(), e);
			mapAreaInfo.put(new Long(e.getId()), e);
			areaInfos.add(e);
			sbArea.append((sbArea.length()==0)?e.getAreaCode():(","+e.getAreaCode()));
		});
		areaCodesStr = sbArea.toString();
		
		StringBuffer sbInterior = new StringBuffer();
		interiorInfoDao.loadAll().get().forEach(e -> {
			mapInteriorInfoByCode.put(e.getInteriorCode(), e);
			mapInteriorInfo.put(new Long(e.getId()), e);
			interiorInfos.add(e);
			sbInterior.append((sbInterior.length() == 0 )?e.getInteriorCode():(","+e.getInteriorCode()));
		});
		interiorsStr = sbInterior.toString();
		
		facilityInfoDao.loadAll().get().forEach(e -> {
			mapFacilityInfoByCode.put(e.getFacilCode(), e);
			facilityInfos.add(e);			
		});
		facilTagInfoDao.loadAll().get().forEach(e -> {
			mapFacilTagInfoByCode.put(e.getTagName(), e);
			facilTagInfos.add(e);
		});
		iotDeviceInfoDao.loadAll().get().forEach(e -> {
			mapIotDeviceInfoByCode.put(e.getDeviceId(), e);
			iotDeviceInfos.add(e);
		});
		etcDeviceInfoDao.loadAll().get().forEach(e -> {
			mapEtcDeviceInfoByCode.put(e.getDeviceId(), e);
			etcDeviceInfos.add(e);
		});
		TreeSet<Long> areaIdsForIot = new TreeSet<Long>();
		TreeSet<Long> interiorIdsForIot = new TreeSet<Long>();
		TreeSet<Long> areaIdsForCctv = new TreeSet<Long>();
		TreeSet<Long> interiorIdsForCctv = new TreeSet<Long>();
		iotDeviceInfos.stream().forEach(e -> {
			interiorIdsForIot.add(new Long(e.getInteriorId()));
		});
		interiorIdsForIot.forEach(e -> {
			areaIdsForIot.add(new Long(mapInteriorInfo.get(e).getAreaId()));
			interiorInfosForIot.add(mapInteriorInfo.get(e));
		});
		areaIdsForIot.forEach(e -> {
			areaInfosForIot.add(mapAreaInfo.get(e));
		});
		etcDeviceInfos.stream().forEach(e -> {
			interiorIdsForCctv.add(new Long(e.getInteriorId()));
		});
		interiorIdsForCctv.forEach(e -> {
			areaIdsForCctv.add(new Long(mapInteriorInfo.get(e).getAreaId()));
			interiorInfosForCctv.add(mapInteriorInfo.get(e));
		});
		areaIdsForCctv.forEach(e -> {
			areaInfosForCctv.add(mapAreaInfo.get(e));
		});
	}

	public <T> void insert(T t) {
		if (t instanceof CommonCode) {
			mapCommonCodeByCode.put(((CommonCode)t).getGroupId()+":"+((CommonCode)t).getItemKey(), (CommonCode)t);
			commonCodes.add((CommonCode)t);
		} else if (t instanceof PlantInfo) {
			mapPlantInfoByCode.put(((PlantInfo)t).getPlantNo(), (PlantInfo)t);
			plantInfos.add((PlantInfo)t);
		} else if (t instanceof PlantPartInfo) {
			mapPlantPartInfoByCode.put(mapPlantInfoByCode.get(((PlantPartInfo)t).getPlantId()).getPlantNo()
					+":"+((PlantPartInfo)t).getPlantPartCode(), (PlantPartInfo)t);
			plantPartInfos.add((PlantPartInfo)t);
		} else if (t instanceof AreaInfo) {
			mapAreaInfoByCode.put(((AreaInfo)t).getAreaCode(), (AreaInfo)t);
			mapAreaInfo.put(new Long(((AreaInfo)t).getId()), (AreaInfo)t);
			areaInfos.add((AreaInfo)t);
			areaInfosForIot.add((AreaInfo)t);
			areaInfosForCctv.add((AreaInfo)t);
		} else if (t instanceof InteriorInfo) {
			mapInteriorInfoByCode.put(((InteriorInfo)t).getInteriorCode(), (InteriorInfo)t);
			mapInteriorInfo.put(new Long(((InteriorInfo)t).getId()), (InteriorInfo)t);
			interiorInfos.add((InteriorInfo)t);
			interiorInfosForIot.add((InteriorInfo)t);
			interiorInfosForCctv.add((InteriorInfo)t);
		} else if (t instanceof FacilityInfo) {
			mapFacilityInfoByCode.put(((FacilityInfo)t).getFacilCode(), (FacilityInfo)t);
			facilityInfos.add((FacilityInfo)t);
		} else if (t instanceof FacilTagInfo) {
			mapFacilTagInfoByCode.put(((FacilTagInfo)t).getTagName(), (FacilTagInfo)t);
			facilTagInfos.add((FacilTagInfo)t);
		} else if (t instanceof IotDeviceInfo) {
			mapIotDeviceInfoByCode.put(((IotDeviceInfo)t).getDeviceId(), (IotDeviceInfo)t);
			iotDeviceInfos.add((IotDeviceInfo)t);
		} else if (t instanceof EtcDeviceInfo) {
			mapEtcDeviceInfoByCode.put(((EtcDeviceInfo)t).getDeviceId(), (EtcDeviceInfo)t);
			etcDeviceInfos.add((EtcDeviceInfo)t);
		}
	}
	
	public <T> void update(T t) {
		if (t instanceof CommonCode) {
			mapCommonCodeByCode.put(((CommonCode)t).getGroupId()+":"+((CommonCode)t).getItemKey(), (CommonCode)t);
			commonCodes.set(commonCodes.indexOf((CommonCode)t), (CommonCode)t);
		} else if (t instanceof PlantInfo) {
			mapPlantInfoByCode.put(((PlantInfo)t).getPlantNo(), (PlantInfo)t);
			plantInfos.set(plantInfos.indexOf((PlantInfo)t), (PlantInfo)t);
		} else if (t instanceof PlantPartInfo) {
			mapPlantPartInfoByCode.put(mapPlantInfoByCode.get(((PlantPartInfo)t).getPlantId()).getPlantNo()
					+":"+((PlantPartInfo)t).getPlantPartCode(), (PlantPartInfo)t);
			plantPartInfos.set(plantPartInfos.indexOf((PlantPartInfo)t), (PlantPartInfo)t);
		} else if (t instanceof AreaInfo) {
			mapAreaInfoByCode.put(((AreaInfo)t).getAreaCode(), (AreaInfo)t);
			mapAreaInfo.put(new Long(((AreaInfo)t).getId()), (AreaInfo)t);
			areaInfos.set(areaInfos.indexOf((AreaInfo)t), (AreaInfo)t);
			if (areaInfosForIot.indexOf((AreaInfo)t) >= 0) {
				areaInfosForIot.set(areaInfosForIot.indexOf((AreaInfo)t), (AreaInfo)t);
			}
			if (areaInfosForCctv.indexOf((AreaInfo)t) >= 0) {
				areaInfosForCctv.set(areaInfosForCctv.indexOf((AreaInfo)t), (AreaInfo)t);
			}
		} else if (t instanceof InteriorInfo) {
			mapInteriorInfoByCode.put(((InteriorInfo)t).getInteriorCode(), (InteriorInfo)t);
			mapInteriorInfo.put(new Long(((InteriorInfo)t).getId()), (InteriorInfo)t);
			interiorInfos.set(interiorInfos.indexOf((InteriorInfo)t), (InteriorInfo)t);
			if (interiorInfosForIot.indexOf((InteriorInfo)t) >= 0) {
				interiorInfosForIot.set(interiorInfosForIot.indexOf((InteriorInfo)t), (InteriorInfo)t);
			}
			if (interiorInfosForCctv.indexOf((InteriorInfo)t) >= 0) {
				interiorInfosForCctv.set(interiorInfosForCctv.indexOf((InteriorInfo)t), (InteriorInfo)t);
			}
		} else if (t instanceof FacilityInfo) {
			mapFacilityInfoByCode.put(((FacilityInfo)t).getFacilCode(), (FacilityInfo)t);
			facilityInfos.set(facilityInfos.indexOf((FacilityInfo)t), (FacilityInfo)t);
		} else if (t instanceof FacilTagInfo) {
			mapFacilTagInfoByCode.put(((FacilTagInfo)t).getTagName(), (FacilTagInfo)t);
			facilTagInfos.set(facilTagInfos.indexOf((FacilTagInfo)t), (FacilTagInfo)t);
		} else if (t instanceof IotDeviceInfo) {
			mapIotDeviceInfoByCode.put(((IotDeviceInfo)t).getDeviceId(), (IotDeviceInfo)t);
			iotDeviceInfos.set(iotDeviceInfos.indexOf((IotDeviceInfo)t), (IotDeviceInfo)t);
		} else if (t instanceof EtcDeviceInfo) {
			mapEtcDeviceInfoByCode.put(((EtcDeviceInfo)t).getDeviceId(), (EtcDeviceInfo)t);
			etcDeviceInfos.set(etcDeviceInfos.indexOf((EtcDeviceInfo)t), (EtcDeviceInfo)t);
		}
	}

	public <T> void delete(T t) {
		if (t instanceof CommonCode) {
			mapCommonCodeByCode.remove(((CommonCode)t).getGroupId()+":"+((CommonCode)t).getItemKey());
			commonCodes.remove((CommonCode)t);
		} else if (t instanceof PlantInfo) {
			mapPlantInfoByCode.remove(((PlantInfo)t).getPlantNo());
			plantInfos.remove((PlantInfo)t);
		} else if (t instanceof PlantPartInfo) {
			mapPlantPartInfoByCode.remove(mapPlantInfoByCode.get(((PlantPartInfo)t).getPlantId()).getPlantNo()
					+":"+((PlantPartInfo)t).getPlantPartCode());
			plantPartInfos.remove((PlantPartInfo)t);
		} else if (t instanceof AreaInfo) {
			mapAreaInfoByCode.remove(((AreaInfo)t).getAreaCode());
			mapAreaInfo.remove(((AreaInfo)t).getId());
			areaInfos.remove((AreaInfo)t);
			areaInfosForIot.remove((AreaInfo)t);
			areaInfosForCctv.remove((AreaInfo)t);
		} else if (t instanceof InteriorInfo) {
			mapInteriorInfoByCode.remove(((InteriorInfo)t).getInteriorCode());
			mapInteriorInfo.remove(((InteriorInfo)t).getId());
			interiorInfos.remove((InteriorInfo)t);
			interiorInfosForIot.remove((InteriorInfo)t);
			interiorInfosForCctv.remove((InteriorInfo)t);
		} else if (t instanceof FacilityInfo) {
			mapFacilityInfoByCode.remove(((FacilityInfo)t).getFacilCode());
			facilityInfos.remove((FacilityInfo)t);
		} else if (t instanceof FacilTagInfo) {
			mapFacilTagInfoByCode.remove(((FacilTagInfo)t).getTagName());
			facilTagInfos.remove((FacilTagInfo)t);
		} else if (t instanceof IotDeviceInfo) {
			mapIotDeviceInfoByCode.remove(((IotDeviceInfo)t).getDeviceId());
			iotDeviceInfos.remove((IotDeviceInfo)t);
		} else if (t instanceof EtcDeviceInfo) {
			mapEtcDeviceInfoByCode.remove(((EtcDeviceInfo)t).getDeviceId());
			etcDeviceInfos.remove((EtcDeviceInfo)t);
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
	
	public Map<Long, AreaInfo> getMapAreaInfo() {
		return mapAreaInfo;
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

	public Map<Long, InteriorInfo> getMapInteriorInfo() {
		return mapInteriorInfo;
	}

	public void setMapInteriorInfo(Map<Long, InteriorInfo> mapInteriorInfo) {
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
	public List<CommonCode> getCommonCodes() {
		return commonCodes;
	}
	
	public List<CommonCode> getCommonCodes(String groupId) {
		return commonCodes.stream().filter(e-> e.getGroupId().equals(groupId)).collect(Collectors.toList());
	}

	public List<PlantInfo> getPlantInfos() {
		return plantInfos;
	}

	public List<PlantPartInfo> getPlantPartInfos() {
		return plantPartInfos;
	}

	public List<AreaInfo> getAreaInfos() {
		return areaInfos;
	}

	public List<AreaInfo> getAreaInfosForIot() {
		return areaInfosForIot;
	}

	public List<AreaInfo> getAreaInfosForCctv() {
		return areaInfosForCctv;
	}

	public List<InteriorInfo> getInteriorInfosForIot() {
		return interiorInfosForIot;
	}

	public List<InteriorInfo> getInteriorInfosForCctv() {
		return interiorInfosForCctv;
	}

	public List<InteriorInfo> getInteriorInfos() {
		return interiorInfos;
	}

	public List<FacilityInfo> getFacilityInfos() {
		return facilityInfos;
	}

	public List<FacilTagInfo> getFacilTagInfos() {
		return facilTagInfos;
	}

	public List<IotDeviceInfo> getIotDeviceInfos() {
		return iotDeviceInfos;
	}

	public List<EtcDeviceInfo> getEtcDeviceInfos() {
		return etcDeviceInfos;
	}
	public String getPlantTypeStr() {
		return plantTypeStr;
	}
	public String getInteriorsStr() {
		return interiorsStr;
	}

	public String getPlantNosStr() {
		return plantNosStr;
	}

	public String getPlantPartsStr() {
		return plantPartsStr;
	}

	public String getAreaCodesStr() {
		return areaCodesStr;
	}
	
}
