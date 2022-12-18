/**=========================================================================================
<overview>대시보드화면처리관련 업무처리서비스
  </overview>
==========================================================================================*/
package com.gencode.issuetool.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.dao.IotDataHistDao;
import com.gencode.issuetool.dao.IotDataHistStatDao;
import com.gencode.issuetool.dao.IotFireIdxHistDao;
import com.gencode.issuetool.dao.IotFireIdxHistStatDao;
import com.gencode.issuetool.dao.IotMainHistDao;
import com.gencode.issuetool.dao.IotMainHistStatDao;
import com.gencode.issuetool.dao.IotSensorDataDao;
import com.gencode.issuetool.dao.NoticeBoardDao;
import com.gencode.issuetool.dao.TagDataHistDao;
import com.gencode.issuetool.dao.TagDataHistStatDao;
import com.gencode.issuetool.dao.TagDvcEventHistDao;
import com.gencode.issuetool.dao.TagDvcPushEventHistDao;
import com.gencode.issuetool.dao.TagFireIdxHistDao;
import com.gencode.issuetool.dao.TagFireIdxHistStatDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.FakeDataUtil;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.io.chart.RealtimeChartObj;
import com.gencode.issuetool.io.chart.ColumnChartObj;
import com.gencode.issuetool.io.chart.ColumnChartSeriesItem;
import com.gencode.issuetool.io.chart.RealtimeChartSeriesItem;
import com.gencode.issuetool.logpresso.obj.DashboardObj;
import com.gencode.issuetool.obj.FacilTagInfo;
import com.gencode.issuetool.obj.IotData;
import com.gencode.issuetool.obj.IotFireIdx;
import com.gencode.issuetool.obj.IotFireIdxHistStat;
import com.gencode.issuetool.obj.IotMain;
import com.gencode.issuetool.obj.IotMainHistStat;
import com.gencode.issuetool.obj.IotSensorData;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.TagData;
import com.gencode.issuetool.obj.TagDataHistStat;
import com.gencode.issuetool.obj.TagDvcEventHist;
import com.gencode.issuetool.obj.TagDvcPushEvent;
import com.gencode.issuetool.obj.TagFireIdx;
import com.gencode.issuetool.obj.TagFireIdxHistStat;
import com.gencode.issuetool.util.JsonUtils;
import com.logpresso.client.Cursor;
import com.logpresso.client.Logpresso;
import com.logpresso.client.Tuple;

@Service
public class DashboardService {
	private final static Logger logger = LoggerFactory.getLogger(DashboardService.class);

	@Value("${logpresso.host}") String host;
	@Value("${logpresso.port}") String port;
	@Value("${logpresso.userid}") String userid;
	@Value("${logpresso.password}") String password;
	@Value("${server.servlet.context-path}") String contextPath;

	final String hdgPath = "/hdgdashboard";
	@Autowired
	private IotSensorDataDao iotSensorDataDao;
	
	@Autowired private TagFireIdxHistDao tagFireIdxHistDao;
	@Autowired private TagFireIdxHistStatDao tagFireIdxHistStatDao;
	@Autowired private TagDataHistDao tagDataHistDao;
	@Autowired private TagDataHistStatDao tagDataHistStatDao;
	
	@Autowired private IotFireIdxHistDao iotFireIdxHistDao;
	@Autowired private IotFireIdxHistStatDao iotFireIdxHistStatDao;
	@Autowired private IotMainHistDao iotMainHistDao;
	@Autowired private IotMainHistStatDao iotMainHistStatDao;
	
	@Autowired private IotDataHistDao iotDataHistDao;
	@Autowired private IotDataHistStatDao iotDataHistStatDao;

	@Autowired private TagDvcEventHistDao tagDvcEventHistDao;
	@Autowired private TagDvcPushEventHistDao tagDvcPushEventHistDao;

	@Autowired
	private PushService pushService;
	
	@Autowired
	private LogpressoConnector conn;
	
	@Autowired
	private CacheMapManager cacheMapManager; 
	
//	public DashboardObj getDashboardDataAll() throws IOException {
//		DashboardObj arResult = new DashboardObj();
//		
//		arResult.setDefaultMain(FakeDataUtil.getMapTotalFireIdxData(FakeDataUtil.generateTotalFireIdxData())); //화재종합지표
//		arResult.setTagFireIdx(FakeDataUtil.getMapTagFireIdx(FakeDataUtil.generateTagFireIdx())); //설비별화재지수
//		arResult.setIotFireIdx(FakeDataUtil.getMapIotFireIdx(FakeDataUtil.generateIotFireIdx())); //공간별화재지수
//		arResult.setTagMain(FakeDataUtil.getMapTagData(FakeDataUtil.generateTagData())); //설비별위험현황
//		arResult.setIotMain(FakeDataUtil.getMapIotData(FakeDataUtil.generateIotData())); //공간별위험현황
//		return arResult;
//	}

	/**
	 * 화재종합지표
	 * @return
	 * @throws IOException
	 */
	public DashboardObj getDashboardTotal() throws IOException {
		DashboardObj arResult = new DashboardObj();
		arResult.setDefaultMain(FakeDataUtil.getMapTotalFireIdxData(FakeDataUtil.generateTotalFireIdxData())); //화재종합지표
		return arResult;
	}
	
	/*
	 * 설비별 화재지수 처리:
	 * 매시간 조회후 tag_fire_idx_hist에 누적
	 * 화재지수 조회(상단 테이블)
	 *  - 직접 로그프레소 호출 
	 * 화재지수변동추이
	 *  - 30일분 1일 평균으로 tag_fire_dix_hist 조회
	 * 설비별 최근30일 안정구간 초과건수
	 *  - 최근 30일  tag_fire_dix_hist 집계(>20)
	 */	
	
	/**
	 * 화재지수 조회(상단 테이블)
	 *  - 직접 로그프레소 호출
	 * @return
	 * @throws IOException
	 */
	public DashboardObj getTagFireIdx() throws IOException {
		DashboardObj arResult = new DashboardObj();
		String strTagFireIdx = FakeDataUtil.generateTagFireIdx();
		arResult.setTagFireIdx(FakeDataUtil.getMapTagFireIdx(strTagFireIdx)); //설비별화재지수
		//tagFireIdxHistDao.deleteOld();
		//tagFireIdxHistDao.register(FakeDataUtil.getListTagFireIdx(strTagFireIdx));
		return arResult;
	}


	/**
	 * 화재지수변동추이
	 *  - 30일분 1일 평균으로 tag_fire_dix_hist 조회
	 *  - 기본 5초
	 *  - 1분, 1시간, 6시간, 1일 단위
	 *  - 키는 예>"1호기:MAA"
	 * @return
	 * @throws IOException
	 */
	public RealtimeChartObj getTagFireIdxRealtimeChart(PageRequest req) throws IOException {
		List<RealtimeChartSeriesItem> listSeriesObj = new ArrayList<RealtimeChartSeriesItem>();
		Map<String, List<List<String>>> mapResult = new TreeMap<String, List<List<String>>>();
		List<List<String>> listData = new ArrayList<List<String>>();
		
		Optional<List<TagFireIdx>> daoList= (req.getParamMap().get("timeMode").equals(Constant.DASHBOARD_STATS_TIME_MODE_5SEC.get()))?
									tagFireIdxHistDao.getRealtimeChartData(req):tagFireIdxHistStatDao.getRealtimeChartData(req);

		String[] prevKeyStr = {""};
		daoList.get().forEach( e -> {
			String keyStr = (String)e.getPlantNo()+":"+(String)e.getPlantPartCode();
			if (mapResult.get(keyStr)==null && listData.size()>0) {
				mapResult.put(prevKeyStr[0], 
						(List<List<String>>)((ArrayList<List<String>>)listData).clone());
				listData.clear();
			}
			listData.add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getFireIdx()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			prevKeyStr[0] = keyStr;
		});
		if (listData.size()>0) {
			mapResult.put(prevKeyStr[0], listData);
		}
		mapResult.keySet().forEach(e -> {
			listSeriesObj.add(new RealtimeChartSeriesItem((e.contains("AVERAGE")?"평균"
					:cacheMapManager.getMapPlantPartInfoByCode().get(e).getDescription())
					+"("+e.substring(0,2)+")", 
					mapResult.get(e)));
		});
		return new RealtimeChartObj(new ArrayList<String>(mapResult.keySet()), listSeriesObj);

	}
	
	/**
	 * 설비별 최근30일 안정구간 초과건수
	 *  - 최근 30일  tag_fire_dix_hist 집계(>20)
	 * @return
	 * @throws IOException
	 */
	public ColumnChartObj getTagFireIdxCntOverStable(PageRequest req) throws IOException {
		ArrayList<String> listData = new ArrayList<String>();
		ArrayList<Object> listTagNames = new ArrayList<Object>();
		Optional<List<TagFireIdxHistStat>> daoList = tagFireIdxHistStatDao.getStatsCnt(req);
		daoList.get().forEach( e -> {
			String keyStr = (e.getPlantPartCode().contains("AVERAGE")?"평균":
					cacheMapManager.getMapPlantPartInfoByCode().get((String)e.getPlantNo()+":"+(String)e.getPlantPartCode()).getDescription())
					+"("+e.getPlantNo()+")";
			listData.add(Long.toString(e.getAlarmCnt()));
			listTagNames.add(Utils.splitWord(keyStr,2));
		});
		return new ColumnChartObj(listTagNames,
				new ArrayList<ColumnChartSeriesItem>() {{
				add(new ColumnChartSeriesItem("초과건", listData));
			}});
	} 
	/**
	 * 공간별 화재지수
	 * @return
	 * @throws IOException
	 */
	public DashboardObj getIotFireIdx() throws IOException {
		DashboardObj arResult = new DashboardObj();
		arResult.setIotFireIdx(FakeDataUtil.getMapIotFireIdx(FakeDataUtil.generateIotFireIdx())); //공간별화재지수
		return arResult;
	}
	
	/**
	 * 화재지수변동추이
	 *  - 30일분 1일 평균으로 iot_fire_dix_hist 조회
	 *  - 키는 예>"1호기:MAA"
	 * @return
	 * @throws IOException
	 */
	public RealtimeChartObj getIotFireIdxRealtimeChart(PageRequest req) throws IOException {
		List<RealtimeChartSeriesItem> listSeriesObj = new ArrayList<RealtimeChartSeriesItem>();
		Map<String, List<List<String>>> mapResult = new TreeMap<String, List<List<String>>>();
		List<List<String>> listData = new ArrayList<List<String>>();
		
		Optional<List<IotFireIdx>> daoList = (req.getParamMap().get("timeMode").equals(Constant.DASHBOARD_STATS_TIME_MODE_5SEC.get()))?
											iotFireIdxHistDao.getRealtimeChartData(req):
											iotFireIdxHistStatDao.getRealtimeChartData(req);
		String[] prevKeyStr = {""};
		daoList.get().forEach( e -> {
			String keyStr = (String)e.getInteriorCode();
			if (mapResult.get(keyStr)==null && listData.size()>0) {
				mapResult.put(prevKeyStr[0], (List<List<String>>)((ArrayList<List<String>>)listData).clone());
				listData.clear();
			}
			listData.add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getFireIdx()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			prevKeyStr[0] = keyStr;
		});
		if (listData.size()>0) {
			mapResult.put(prevKeyStr[0], listData);
		}
		mapResult.keySet().forEach(e -> {
			listSeriesObj.add(new RealtimeChartSeriesItem(e.contains("AVERAGE")?"평균"
					:cacheMapManager.getMapInteriorInfoByCode().get(e).getInteriorName(), mapResult.get(e)));
		});
		return new RealtimeChartObj(new ArrayList<String>(mapResult.keySet()), listSeriesObj);

	}
	
	/**
	 * 설비별 최근30일 안정구간 초과건수
	 *  - 최근 30일  tag_fire_dix_hist 집계(>20)
	 * @return
	 * @throws IOException
	 */
	public ColumnChartObj getIotFireIdxCntOverStable(PageRequest req) throws IOException {
		ArrayList<String> listData = new ArrayList<String>();
		ArrayList<Object> listInteriorNames = new ArrayList<Object>();
		Optional<List<IotFireIdxHistStat>> daoList = iotFireIdxHistStatDao.getStatsCnt(req);
		daoList.get().forEach( e -> {
			String keyStr = e.getInteriorCode().contains("AVERAGE")?"평균"
					:cacheMapManager.getMapInteriorInfoByCode().get(e.getInteriorCode()).getInteriorName();
			listData.add(Long.toString(e.getAlarmCnt()));
			listInteriorNames.add(Utils.splitWord(keyStr,2));
		});
		return new ColumnChartObj(listInteriorNames,
				new ArrayList<ColumnChartSeriesItem>() {{
				add(new ColumnChartSeriesItem("초과건", listData));
			}});
	} 
	
	/**
	 * TAG DATA
	 * tag_data_hist_stat 처리방식
	 * 
	 * 1. 분당건 1일보관  => 299520건
	 * 2. 시간당 30일보관 => 374400건
	 * 3. 1일건 영구보관 => 1달 6240건
	 * 
	 * ------------------
	 *  집계데이터처리방식
	 * ------------------
	 * 1. 5초 데이터수신
	2. hst테이블 입력
	3. 최종 등록시간체크
	4. 분단위 등록
		4.1. 분단위 등록시 기존등록 삭제
		4.2. 최종분이후 현재분 등록
		hst테이블=>stat테이블
	5. 시간단위 등록
		5.1. 시간단위 등록시 기존등록 삭제
		5.2. 최종시간이후 현재시간 등록
		stat테이블=>stat테이블
	6. 일단위 등록
		6.1. 일단위 등록시 기존등록 삭제
		6.2.최종일자 이후 오늘자 등록
		stat테이블=>stat테이블
	7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	8. stat테이블 클랜징
		8.1. 분단위 => 1일전 데이터 삭제
		  - 시간단위가 1일전이면 삭제안함
		8.2. 시간단위=> 30일전 데이터 삭제
	 * 
	 */

	/**
	 * 공간별 위험현황
	 * @return
	 * @throws IOException
	 */
	public DashboardObj getIotMain() throws IOException {
		DashboardObj arResult = new DashboardObj();
		arResult.setIotMain(FakeDataUtil.getMapIotMain(FakeDataUtil.generateIotMain()));
		return arResult;
	}

	/**
	 * 센서별 수치현황
	 * @return
	 * @throws IOException
	 */
	public DashboardObj getIotData() throws IOException {
		DashboardObj arResult = new DashboardObj();
		arResult.setIotData(FakeDataUtil.getMapIotData(FakeDataUtil.generateIotData())); 
		return arResult;
	}

	/**
	 * 센서별 수치현황
	 * @return
	 * @throws IOException
	 */
	public List<IotData> getIotDataList() throws IOException {
		DashboardObj arResult = new DashboardObj();
		return FakeDataUtil.getListIotData(FakeDataUtil.generateIotData()); 
	}

	public DashboardObj getTagMain() throws IOException {
		DashboardObj arResult = new DashboardObj();
		arResult.setTagMain(FakeDataUtil.getMapTagData(FakeDataUtil.generateTagData())); //설비별위험현황
		return arResult;
	}
	/**
	 * 	7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	@Transactional
	public void cleanseTagFireIdx() throws Exception { 
		int returnCnt = tagFireIdxHistDao.cleanseData();
		logger.info("cleanseTagFireIdx:"+returnCnt);
	}
	/**
	 * 	7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	@Transactional
	public void cleanseIotFireIdx() throws Exception { 
		int returnCnt = iotFireIdxHistDao.cleanseData();
		logger.info("cleanseIotFireIdx:"+returnCnt);
	}

	/**
	 * 	7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	@Transactional
	public void cleanseIotMain() throws Exception { 
		int returnCnt = iotMainHistDao.cleanseData();
		logger.info("cleanseIotMain:"+returnCnt);
	}
	
	/**
	 * 	7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	@Transactional
	public void cleanseIotData() throws Exception { 
		int returnCnt = iotDataHistDao.cleanseData();
		logger.info("cleanseIotData:"+returnCnt);
	}

	/**
	 * 	7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	@Transactional
	public void cleanseTagData() throws Exception { 
		int returnCnt = tagDataHistDao.cleanseData();
		logger.info("cleanseTagData:"+returnCnt);
	}

	/**
	 * iotMain을 통계용 데이터 집계
	 */
	@Transactional
	public void genIotMainHistStats() {
		String createdDtm = iotMainHistStatDao.getCreatedDtmPreMinuteDataGen();
		int cleanseCnt = iotMainHistStatDao.cleansePreMinuteDataGen(createdDtm);
		logger.info("cleansePreMinuteDataGen:"+cleanseCnt);
		int genCnt = iotMainHistStatDao.generateMinuteData(createdDtm);
		logger.info("generateMinuteData:"+genCnt);
		
		createdDtm = iotMainHistStatDao.getCreatedDtmPreHourlyDataGen();
		cleanseCnt = iotMainHistStatDao.cleansePreHourlyDataGen(createdDtm);
		logger.info("cleansePreHourlyDataGen:"+cleanseCnt);
		genCnt = iotMainHistStatDao.generateHourlyData(createdDtm);
		logger.info("generateHourlyData:"+genCnt);
		
		createdDtm = iotMainHistStatDao.getCreatedDtmPre6HourlyDataGen();
		cleanseCnt = iotMainHistStatDao.cleansePre6HourlyDataGen(createdDtm);
		logger.info("cleansePre6HourlyDataGen:"+cleanseCnt);
		genCnt = iotMainHistStatDao.generate6HourlyData(createdDtm);
		logger.info("generate6HourlyData:"+genCnt);
		
		createdDtm = iotMainHistStatDao.getCreatedDtmPreDailyDataGen();
		cleanseCnt = iotMainHistStatDao.cleansePreDailyDataGen(createdDtm);
		logger.info("cleansePreDailyDataGen:"+cleanseCnt);
		genCnt = iotMainHistStatDao.generateDailyData(createdDtm);
		logger.info("generateDailyData:"+genCnt);

		cleanseCnt = iotMainHistStatDao.cleanseMinuteData();
		logger.info("cleanseMinuteData:"+cleanseCnt);
		cleanseCnt = iotMainHistStatDao.cleanseHourlyData();
		logger.info("cleanseHourlyData:"+cleanseCnt);
	}

	/**
	 * iotData 로 통계용 데이터 집계
	 */
	@Transactional
	public void genIotDataHistStats() {
		String createdDtm = iotDataHistStatDao.getCreatedDtmPreMinuteDataGen();
		int cleanseCnt = iotDataHistStatDao.cleansePreMinuteDataGen(createdDtm);
		logger.info("cleansePreMinuteDataGen:"+cleanseCnt);
		int genCnt = iotDataHistStatDao.generateMinuteData(createdDtm);
		logger.info("generateMinuteData:"+genCnt);
		
		createdDtm = iotDataHistStatDao.getCreatedDtmPreHourlyDataGen();
		cleanseCnt = iotDataHistStatDao.cleansePreHourlyDataGen(createdDtm);
		logger.info("cleansePreHourlyDataGen:"+cleanseCnt);
		genCnt = iotDataHistStatDao.generateHourlyData(createdDtm);
		logger.info("generateHourlyData:"+genCnt);
		
		createdDtm = iotDataHistStatDao.getCreatedDtmPre6HourlyDataGen();
		cleanseCnt = iotDataHistStatDao.cleansePre6HourlyDataGen(createdDtm);
		logger.info("cleansePre6HourlyDataGen:"+cleanseCnt);
		genCnt = iotDataHistStatDao.generate6HourlyData(createdDtm);
		logger.info("generate6HourlyData:"+genCnt);
		
		createdDtm = iotDataHistStatDao.getCreatedDtmPreDailyDataGen();
		cleanseCnt = iotDataHistStatDao.cleansePreDailyDataGen(createdDtm);
		logger.info("cleansePreDailyDataGen:"+cleanseCnt);
		genCnt = iotDataHistStatDao.generateDailyData(createdDtm);
		logger.info("generateDailyData:"+genCnt);

		cleanseCnt = iotDataHistStatDao.cleanseMinuteData();
		logger.info("cleanseMinuteData:"+cleanseCnt);
		cleanseCnt = iotDataHistStatDao.cleanseHourlyData();
		logger.info("cleanseHourlyData:"+cleanseCnt);
	}

	/**
	 * tagData 로 통계용 데이터 집계
	 */
	@Transactional
	public void genTagDataHistStats() {
		String createdDtm = tagDataHistStatDao.getCreatedDtmPreMinuteDataGen();
		int cleanseCnt = tagDataHistStatDao.cleansePreMinuteDataGen(createdDtm);
		logger.info("cleansePreMinuteDataGen:"+cleanseCnt);
		int genCnt = tagDataHistStatDao.generateMinuteData(createdDtm);
		logger.info("generateMinuteData:"+genCnt);
		
		createdDtm = tagDataHistStatDao.getCreatedDtmPreHourlyDataGen();
		cleanseCnt = tagDataHistStatDao.cleansePreHourlyDataGen(createdDtm);
		logger.info("cleansePreHourlyDataGen:"+cleanseCnt);
		genCnt = tagDataHistStatDao.generateHourlyData(createdDtm);
		logger.info("generateHourlyData:"+genCnt);
		
		createdDtm = tagDataHistStatDao.getCreatedDtmPre6HourlyDataGen();
		cleanseCnt = tagDataHistStatDao.cleansePre6HourlyDataGen(createdDtm);
		logger.info("cleansePre6HourlyDataGen:"+cleanseCnt);
		genCnt = tagDataHistStatDao.generate6HourlyData(createdDtm);
		logger.info("generate6HourlyData:"+genCnt);

		createdDtm = tagDataHistStatDao.getCreatedDtmPreDailyDataGen();
		cleanseCnt = tagDataHistStatDao.cleansePreDailyDataGen(createdDtm);
		logger.info("cleansePreDailyDataGen:"+cleanseCnt);
		genCnt = tagDataHistStatDao.generateDailyData(createdDtm);
		logger.info("generateDailyData:"+genCnt);

		cleanseCnt = tagDataHistStatDao.cleanseMinuteData();
		logger.info("cleanseMinuteData:"+cleanseCnt);
		cleanseCnt = tagDataHistStatDao.cleanseHourlyData();
		logger.info("cleanseHourlyData:"+cleanseCnt);
	}
	
	/**
	 * tagFireIdx 로 통계용 데이터 집계
	 */
	@Transactional
	public void genTagFireIdxHistStats() {
		String createdDtm = tagFireIdxHistStatDao.getCreatedDtmPreMinuteDataGen();
		int cleanseCnt = tagFireIdxHistStatDao.cleansePreMinuteDataGen(createdDtm);
		logger.info("cleansePreMinuteDataGen:"+cleanseCnt);
		int genCnt = tagFireIdxHistStatDao.generateMinuteData(createdDtm);
		logger.info("generateMinuteData:"+genCnt);

		createdDtm = tagFireIdxHistStatDao.getCreatedDtmPreHourlyDataGen();
		cleanseCnt = tagFireIdxHistStatDao.cleansePreHourlyDataGen(createdDtm);
		logger.info("cleansePreHourlyDataGen:"+cleanseCnt);
		genCnt = tagFireIdxHistStatDao.generateHourlyData(createdDtm);
		logger.info("generateHourlyData:"+genCnt);
		
		createdDtm = tagFireIdxHistStatDao.getCreatedDtmPre6HourlyDataGen();
		cleanseCnt = tagFireIdxHistStatDao.cleansePre6HourlyDataGen(createdDtm);
		logger.info("cleansePre6HourlyDataGen:"+cleanseCnt);
		genCnt = tagFireIdxHistStatDao.generate6HourlyData(createdDtm);
		logger.info("generate6HourlyData:"+genCnt);

		createdDtm = tagFireIdxHistStatDao.getCreatedDtmPreDailyDataGen();
		cleanseCnt = tagFireIdxHistStatDao.cleansePreDailyDataGen(createdDtm);
		logger.info("cleansePreDailyDataGen:"+cleanseCnt);
		genCnt = tagFireIdxHistStatDao.generateDailyData(createdDtm);
		logger.info("generateDailyData:"+genCnt);

		cleanseCnt = tagFireIdxHistStatDao.cleanseMinuteData();
		logger.info("cleanseMinuteData:"+cleanseCnt);
		cleanseCnt = tagFireIdxHistStatDao.cleanseHourlyData();
		logger.info("cleanseHourlyData:"+cleanseCnt);

	}	
	/**
	 * iotFireIdx 로 통계용 데이터 집계
	 */
	@Transactional
	public void genIotFireIdxHistStats() {
		String createdDtm = iotFireIdxHistStatDao.getCreatedDtmPreMinuteDataGen();
		int cleanseCnt = iotFireIdxHistStatDao.cleansePreMinuteDataGen(createdDtm);
		logger.info("cleansePreMinuteDataGen:"+cleanseCnt);
		int genCnt = iotFireIdxHistStatDao.generateMinuteData(createdDtm);
		logger.info("generateMinuteData:"+genCnt);
		
		createdDtm = iotFireIdxHistStatDao.getCreatedDtmPreHourlyDataGen();
		cleanseCnt = iotFireIdxHistStatDao.cleansePreHourlyDataGen(createdDtm);
		logger.info("cleansePreHourlyDataGen:"+cleanseCnt);
		genCnt = iotFireIdxHistStatDao.generateHourlyData(createdDtm);
		logger.info("generateHourlyData:"+genCnt);
				
		createdDtm = iotFireIdxHistStatDao.getCreatedDtmPre6HourlyDataGen();
		cleanseCnt = iotFireIdxHistStatDao.cleansePre6HourlyDataGen(createdDtm);
		logger.info("cleansePre6HourlyDataGen:"+cleanseCnt);
		genCnt = iotFireIdxHistStatDao.generate6HourlyData(createdDtm);
		logger.info("generate6HourlyData:"+genCnt);

		createdDtm = iotFireIdxHistStatDao.getCreatedDtmPreDailyDataGen();
		cleanseCnt = iotFireIdxHistStatDao.cleansePreDailyDataGen(createdDtm);
		logger.info("cleansePreDailyDataGen:"+cleanseCnt);
		genCnt = iotFireIdxHistStatDao.generateDailyData(createdDtm);
		logger.info("generateDailyData:"+genCnt);

		cleanseCnt = iotFireIdxHistStatDao.cleanseMinuteData();
		logger.info("cleanseMinuteData:"+cleanseCnt);
		cleanseCnt = iotFireIdxHistStatDao.cleanseHourlyData();
		logger.info("cleanseHourlyData:"+cleanseCnt);

	}	

	/**
	 * 센서 공간별 시계열 추이
	 *  - 최근 30건 조회
	 *  - 키는 예>category : 공간별
	 *          series data: 온도(기본) => 화면에서 선택 습도/열/연기...
	 * @return
	 * @throws IOException
	 */	
	public RealtimeChartObj getIotMainRealtimeChartByAllInteriors(PageRequest req) throws Exception {
		List<RealtimeChartSeriesItem> listSeriesObj = new ArrayList<RealtimeChartSeriesItem>();
		Map<String, List<List<String>>> mapResult = new HashMap<String, List<List<String>>>();
		List<List<String>> listData = new ArrayList<List<String>>();
		String[] prevKeyStr = {""};
		
		Optional<List<IotMain>> daoList= (req.getParamMap().get("timeMode").equals(Constant.DASHBOARD_STATS_TIME_MODE_5SEC.get()))?
										iotMainHistDao.getRealtimeChartData(req):
										iotMainHistStatDao.getRealtimeChartData(req);

		daoList.get().forEach( e -> {
			String keyStr = (String)e.getInteriorCode();
			if (mapResult.get(keyStr)==null && listData.size()>0) {
				mapResult.put(prevKeyStr[0], (List<List<String>>)((ArrayList<List<String>>)listData).clone());
				listData.clear();
			}
			listData.add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getAvgTempVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			prevKeyStr[0] = keyStr;
		});
		if (listData.size()>0) {
			mapResult.put(prevKeyStr[0], listData);
		}
		mapResult.keySet().forEach(e -> {
			listSeriesObj.add(new RealtimeChartSeriesItem(cacheMapManager.getMapInteriorInfoByCode().get(e).getInteriorName()+"("+e+")", mapResult.get(e)));
		});

		return new RealtimeChartObj(new ArrayList<String>(mapResult.keySet().stream().map(e-> 
					cacheMapManager.getMapInteriorInfoByCode().get(e).getInteriorName()+"("+e+")"
				).collect(Collectors.toList())), listSeriesObj);
	}
	/**
	 * 센서 각 공간 데이터별 추이
	 *  - 최근 30건 조회
	 *  - 키는 예>category : 온도/습도/열/....
	 *          series data: 개별 수치 
	 * @return
	 * @throws IOException
	 */	
	public RealtimeChartObj getIotMainRealtimeChartByInterior(PageRequest req) throws Exception {
		List<RealtimeChartSeriesItem> listSeriesObj = new ArrayList<RealtimeChartSeriesItem>();
		Map<String, List<List<String>>> mapResult = new HashMap<String, List<List<String>>>();
		List<List<List<String>>> listData = new ArrayList<List<List<String>>>();
		for(int i=0;i<5;i++) {
			listData.add(new ArrayList<List<String>>());
		}
		Optional<List<IotMain>> daoList= (req.getParamMap().get("timeMode").equals(Constant.DASHBOARD_STATS_TIME_MODE_5SEC.get()))?
				iotMainHistDao.getRealtimeChartData(req):iotMainHistStatDao.getRealtimeChartData(req);

		daoList.get().forEach( e -> {
			listData.get(0).add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getAvgTempVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			listData.get(1).add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getAvgHumidVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			listData.get(2).add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getAvgSmokeVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			listData.get(3).add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getAvgCoVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			listData.get(4).add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getAvgFlame()));
			}});
		});
		listSeriesObj.add(new RealtimeChartSeriesItem(Constant.IOT_VALUE_NAME_TEMP.get(), listData.get(0)));
		listSeriesObj.add(new RealtimeChartSeriesItem(Constant.IOT_VALUE_NAME_HUMID.get(), listData.get(1)));
		listSeriesObj.add(new RealtimeChartSeriesItem(Constant.IOT_VALUE_NAME_SMOKE.get(), listData.get(2)));
		listSeriesObj.add(new RealtimeChartSeriesItem(Constant.IOT_VALUE_NAME_CO.get(), listData.get(3)));
		listSeriesObj.add(new RealtimeChartSeriesItem(Constant.IOT_VALUE_NAME_FLARE.get(), listData.get(4)));

		return new RealtimeChartObj(new ArrayList<String>() {{
			add(Constant.IOT_VALUE_NAME_TEMP.get());
			add(Constant.IOT_VALUE_NAME_HUMID.get());
			add(Constant.IOT_VALUE_NAME_SMOKE.get());
			add(Constant.IOT_VALUE_NAME_CO.get());
			add(Constant.IOT_VALUE_NAME_FLARE.get());
		}}, listSeriesObj);
	}

	
	/**
	 * 미사용
	 *  - 최근 30건 조회
	 *  - 키는 예>"1호기:MA1"
	 * @return
	 * @throws IOException
	 */	
	public RealtimeChartObj getIotDataRealTimeChart(PageRequest req) throws Exception {
		//List<String> listNames = new ArrayList<String>();
		List<RealtimeChartSeriesItem> listSeriesObj = new ArrayList<RealtimeChartSeriesItem>();
		Map<String, List<List<String>>> mapResult = new HashMap<String, List<List<String>>>();
		List<List<String>> listData = new ArrayList<List<String>>();
		
		Optional<List<IotData>> daoList= (req.getParamMap().get("timeMode").equals(Constant.DASHBOARD_STATS_TIME_MODE_5SEC.get()))?
				iotDataHistDao.getRealtimeChartData(req):iotDataHistStatDao.getRealtimeChartData(req);

		String[] prevKeyStr = {""};
		daoList.get().forEach( e -> {
			String keyStr = //(String)e.getPlantNo()+":"+(String)e.getPlantPartCode()+":"+(String)e.getFacilCode()+":"+
							(String)e.getDeviceId();
			//listNames.add((String)e.getTagName());
			if (mapResult.get(keyStr)==null && listData.size()>0) {
				mapResult.put(prevKeyStr[0], (List<List<String>>)((ArrayList<List<String>>)listData).clone());
				listData.clear();
			}
			listData.add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getTempVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			prevKeyStr[0] = keyStr;
		});
		if (listData.size()>0) {
			mapResult.put(prevKeyStr[0], listData);
		}
		mapResult.keySet().forEach(e -> {
			listSeriesObj.add(new RealtimeChartSeriesItem(e, mapResult.get(e)));
		});
		return new RealtimeChartObj(new ArrayList<String>(mapResult.keySet()), listSeriesObj);
	}
	
	/**
	 * facility별 시계열 추이
	 *  - 최근 30건 조회
	 *  - 키는 예>"1호기:MA1" 각 태그별 추이
	 * @return
	 * @throws IOException
	 */	
	public RealtimeChartObj getTagDataRealtimeChart(PageRequest req) throws Exception {
		//List<String> listNames = new ArrayList<String>();
		List<RealtimeChartSeriesItem> listSeriesObj = new ArrayList<RealtimeChartSeriesItem>();
		Map<String, List<List<String>>> mapResult = new HashMap<String, List<List<String>>>();
		List<List<String>> listData = new ArrayList<List<String>>();
		
		Optional<List<TagData>> daoList= (req.getParamMap().get("timeMode").equals(Constant.DASHBOARD_STATS_TIME_MODE_5SEC.get()))?
				tagDataHistDao.getRealtimeChartData(req):tagDataHistStatDao.getRealtimeChartData(req);

		String[] prevKeyStr = {""};
		daoList.get().forEach( e -> {
			String keyStr = //(String)e.getPlantNo()+":"+(String)e.getPlantPartCode()+":"+(String)e.getFacilCode()+":"+
							(String)e.getTagName();
			//listNames.add((String)e.getTagName());
			if (mapResult.get(keyStr)==null && listData.size()>0) {
				mapResult.put(prevKeyStr[0], (List<List<String>>)((ArrayList<List<String>>)listData).clone());
				listData.clear();
			}
			listData.add(new ArrayList<String>(){{
				add(e.getCreatedDtm());
				add(Double.toString(e.getTagVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			}});
			prevKeyStr[0] = keyStr;
		});
		if (listData.size()>0) {
			mapResult.put(prevKeyStr[0], listData);
		}
		mapResult.keySet().forEach(e -> {
			listSeriesObj.add(new RealtimeChartSeriesItem(e, mapResult.get(e)));
		});
		return new RealtimeChartObj(new ArrayList<String>(mapResult.keySet()), listSeriesObj);
	}
	


	/**
	 * 5초주기 조회후 iot_main_hist에 누적
	 *  - 1시간전데이터 삭제
	 * @throws IOException
	 */
	@Transactional
	public void addIotMain() throws Exception {
		String strIotMain = FakeDataUtil.generateIotMain();
		int[] returnCntAr = iotMainHistDao.register(FakeDataUtil.getListIotMain(strIotMain));
		logger.info("addIotMain:"+returnCntAr.length);
	}

	/**
	 * 매시간 조회후 iot_fire_idx_hist에 누적
	 *  - 삭제는 하지 않음 1년 누적건수 24*75*30*12=648000	 *   
	 * @throws IOException
	 */
	@Transactional
	public void addIotFireIdx() throws IOException {
		String strIotFireIdx = FakeDataUtil.generateIotFireIdx();
		iotFireIdxHistDao.register(FakeDataUtil.getListIotFireIdx(strIotFireIdx));
	}

	/**
	 * 5초주기 조회후 iot_data_hist에 누적
	 *  - 1시간전데이터 삭제
	 * @throws IOException
	 */
	@Transactional
	public void addIotData() throws Exception {
		String strIotData = FakeDataUtil.generateIotData();
 		int[] returnCntAr = iotDataHistDao.register(FakeDataUtil.getListIotData(strIotData));
		logger.info("addIotData:"+returnCntAr.length);
	}

	/**
	 * 3초주기 조회후 tag_data_hist에 누적
	 *  - 1시간전데이터 삭제
	 *  - tag_data_hist_stat테이블집계는 추후 :TODO   
	 * @throws IOException
	 */
	@Transactional
	public void addTagData() throws Exception {
		String strTagData = FakeDataUtil.generateTagData();
		int[] returnCntAr = tagDataHistDao.register(FakeDataUtil.getListTagData(strTagData));
		logger.info("addTagData:"+returnCntAr.length);
	}
	
	/**
	 * 매시간 조회후 tag_fire_idx_hist에 누적 
	 *  - 삭제는 하지 않음 1년 누적건수 24*75*30*12=648000	 *   
	 * @throws IOException
	 */
	@Transactional
	public void addTagFireIdx() throws IOException {
		String strTagFireIdx = FakeDataUtil.generateTagFireIdx();
		tagFireIdxHistDao.register(FakeDataUtil.getListTagFireIdx(strTagFireIdx));
	}

	/**
	 * 공간별 변화량 현황 30일 초과건수 건수(열/습도/연기/CO/불꽃)
	 * 예>
	 *   series: [  
      { name: '열',
        data: [100, 90 , 40, 45 , 66,75, 65] },  ==> 항목갯수  'MA1, MA2....
        { name: '습도',
        data: [50, 40, 50, 55, 20, 23, 23] },
        { name: '연기,
        data: [75, 65 , 95, 13, 45, 73, 91] },
        { name: 'CO',
        data: [75, 65 , 56, 50, 40 , 22, 76] },
        { name: '불꽃',
        data: [45, 32, 100, 90 , 60, 35, 41] }
    ], 
	 * @return
	 * @throws IOException
	 */
	public ColumnChartObj getIotMainCntStatsChartOverall(PageRequest req) throws IOException {
		ArrayList<ArrayList<String>> listData = new ArrayList<ArrayList<String>>();
		for(int i=0;i<5;i++) {
			listData.add(new ArrayList<String>());
		}
		ArrayList<Object> listTagNames = new ArrayList<Object>();
		List<IotMainHistStat> listResult = iotMainHistStatDao.getStatsCnt(req).get();
		listResult.forEach( e -> {
			listData.get(0).add(Float.toString(e.getMaxTempVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			listData.get(1).add(Float.toString(e.getMaxHumidVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			listData.get(2).add(Float.toString(e.getMaxSmokeVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			listData.get(3).add(Float.toString(e.getMaxCoVal()/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
			listData.get(4).add(Long.toString(e.getFlameCnt()));
			listTagNames.add(cacheMapManager.getMapInteriorInfoByCode().get(e.getInteriorCode()).getInteriorName()+"("+e.getInteriorCode()+")");
		});
		return new ColumnChartObj(listTagNames,
				new ArrayList<ColumnChartSeriesItem>() {{
				add(new ColumnChartSeriesItem(Constant.IOT_VALUE_NAME_TEMP.get(), listData.get(0)));
				add(new ColumnChartSeriesItem(Constant.IOT_VALUE_NAME_HUMID.get(), listData.get(1)));
				add(new ColumnChartSeriesItem(Constant.IOT_VALUE_NAME_SMOKE.get(), listData.get(2)));
				add(new ColumnChartSeriesItem(Constant.IOT_VALUE_NAME_CO.get(), listData.get(3)));
				add(new ColumnChartSeriesItem(Constant.IOT_VALUE_NAME_FLARE.get(), listData.get(4)));
			}});
	}
	
	/**
	 * facilCode별 1일 건수(최대값/평균값/최소값/Alarm건수/Critical건수)
	 * 예>
	 *   series: [  
      { name: '최대값',
        data: [100, 90 , 40, 45 , 66,75, 65] },  ==> 항목갯수  'MA1, MA2....
        { name: '평균값',
        data: [50, 40, 50, 55, 20, 23, 23] },
        { name: '최소값,
        data: [75, 65 , 95, 13, 45, 73, 91] },
        { name: 'Alarm건수',
        data: [75, 65 , 56, 50, 40 , 22, 76] },
        { name: 'Critical건수',
        data: [45, 32, 100, 90 , 60, 35, 41] }
    ], 
	 * @return
	 * @throws IOException
	 */
	public ColumnChartObj getIotMainCntStatsChart(PageRequest req) throws IOException {
		ArrayList<ArrayList<String>> listData = new ArrayList<ArrayList<String>>();
		for(int i=0;i<3;i++) {
			listData.add(new ArrayList<String>());
		}
		ArrayList<Object> listTagNames = new ArrayList<Object>();
		List<IotMainHistStat> listResult = iotMainHistStatDao.getStatsCnt(req).get();
		listResult.forEach( e -> {
			listData.get(0).add(Float.toString(e.getMinTempVal()/100));
			listData.get(1).add(Float.toString(e.getAvgTempVal()/100));
			listData.get(2).add(Float.toString(e.getMaxTempVal()/100));
			listTagNames.add(Constant.IOT_VALUE_NAME_TEMP.get());
			listData.get(0).add(Float.toString(e.getMinHumidVal()/100));
			listData.get(1).add(Float.toString(e.getAvgHumidVal()/100));
			listData.get(2).add(Float.toString(e.getMaxHumidVal()/100));
			listTagNames.add(Constant.IOT_VALUE_NAME_HUMID.get());
			listData.get(0).add(Float.toString(e.getMinSmokeVal()/100));
			listData.get(1).add(Float.toString(e.getAvgSmokeVal()/100));
			listData.get(2).add(Float.toString(e.getMaxSmokeVal()/100));
			listTagNames.add(Constant.IOT_VALUE_NAME_SMOKE.get());
			listData.get(0).add(Float.toString(e.getMinCoVal()/100));
			listData.get(1).add(Float.toString(e.getAvgCoVal()/100));
			listData.get(2).add(Float.toString(e.getMaxCoVal()/100));
			listTagNames.add(Constant.IOT_VALUE_NAME_CO.get());
			listData.get(0).add(Long.toString(e.getFlameCnt()));
			listTagNames.add(Constant.IOT_VALUE_NAME_FLARE.get());
		});
		return new ColumnChartObj(listTagNames,
			new ArrayList<ColumnChartSeriesItem>() {{
				add(new ColumnChartSeriesItem("최소값", listData.get(0)));
				add(new ColumnChartSeriesItem("평균값", listData.get(1)));
				add(new ColumnChartSeriesItem("최대값", listData.get(2)));
			}});
	}
	

	/**
	 * facilCode별 1일 건수(최대값/평균값/최소값/Alarm건수/Critical건수)
	 * 예>
	 *   series: [  
      { name: '최대값',
        data: [100, 90 , 40, 45 , 66,75, 65] },  ==> 항목갯수  'MA1, MA2....
        { name: '평균값',
        data: [50, 40, 50, 55, 20, 23, 23] },
        { name: '최소값,
        data: [75, 65 , 95, 13, 45, 73, 91] },
        { name: 'Alarm건수',
        data: [75, 65 , 56, 50, 40 , 22, 76] },
        { name: 'Critical건수',
        data: [45, 32, 100, 90 , 60, 35, 41] }
    ], 
	 * @return
	 * @throws IOException
	 */
	public ColumnChartObj getTagDataCntStatsChart(PageRequest req) throws IOException {
		ArrayList<ArrayList<String>> listData = new ArrayList<ArrayList<String>>();
		for(int i=0;i<5;i++) {
			listData.add(new ArrayList<String>());
		}
		ArrayList<Object> listTagNames = new ArrayList<Object>();
		List<TagDataHistStat> listResult = tagDataHistStatDao.getStatsCnt(req).get();
		listResult.forEach( e -> {
			listData.get(0).add(Float.toString(e.getMaxTagVal()/100));
			listData.get(1).add(Float.toString(e.getAvgTagVal()/100));
			listData.get(2).add(Float.toString(e.getMinTagVal()/100));
			listData.get(3).add(Long.toString(e.getAlarmCnt()));
			listData.get(4).add(Long.toString(e.getCriticalCnt()));
			listTagNames.add(e.getTagName());
//			FacilTagInfo facilTagInfo = cacheMapManager.getMapFacilTagInfoByCode().get(e.getTagName());
//			listTagNames.add(Utils.splitWord(facilTagInfo.getTagDesc2()+" ("+cacheMapManager.getMapCommonCodeByCode().get("TAG_VAL_TYPE:"+facilTagInfo.getValType()).getItemValue()+")"));
		});
		return new ColumnChartObj(listTagNames,
				new ArrayList<ColumnChartSeriesItem>() {{
				add(new ColumnChartSeriesItem("최대값", listData.get(0)));
				add(new ColumnChartSeriesItem("평균값", listData.get(1)));
				add(new ColumnChartSeriesItem("최소값", listData.get(2)));
				add(new ColumnChartSeriesItem("Alarm건수", listData.get(3)));
				add(new ColumnChartSeriesItem("Critical건수", listData.get(4)));
			}});
	}

	public Optional<PageResultObj<List<IotSensorData>>> getDashboardDataIotPilotDetailList(PageRequest req) throws IOException {
		return iotSensorDataDao.listByCategory(req);
	}
	
	/**
	 * 설비 및 기기별 이벤트 현황 이력
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public  Optional<PageResultObj<List<TagDvcEventHist>>> getTagDvcEventHistory(PageRequest req) throws Exception {
		return tagDvcEventHistDao.search(req);
	}

	public  DashboardObj getTagDvcEventHistList(PageRequest req) throws Exception {
		DashboardObj arResult = new DashboardObj();
		String deviceType = req.getParamMap().get("deviceType");
		arResult.setItemList(JsonUtils.toJson(FakeDataUtil.getListTagDvcEvent(FakeDataUtil.generateTagDvcEvent())
				.stream().filter(e-> e.getDeviceType().equals(deviceType)).collect(Collectors.toList()))); 
		return arResult;
	}
	
	/**
	 * 이벤트수신시 클라이언트 push하고 디비insert처리
	 * @throws Exception
	 */
	public  void addTagDvcPushEventHist() throws Exception {
		if (FakeDataUtil.randomVal(10)/7==1) return;
		Thread.sleep(FakeDataUtil.randomVal(10)*1000);
		TagDvcPushEvent tagDvcPushEvent = FakeDataUtil.getObjTabDvcPushEvent(cacheMapManager, FakeDataUtil.generateTagDvcPushEvent(cacheMapManager));
		tagDvcPushEventHistDao.register(tagDvcPushEvent);
		pushService.sendMsgAll(Constant.PUSH_TAG_TAG_DVC_PUSH_EVENT_ADD.get(), tagDvcPushEvent);
	}
	

	public Optional<PageResultObj<List<TagDvcPushEvent>>> getTagDvcPushEventHistList(PageRequest req) throws Exception {
		DashboardObj arResult = new DashboardObj();
		//String deviceType = req.getParamMap().get("deviceType");
		return tagDvcPushEventHistDao.search(req);
	}
	
	
	String queryLogpressoDashboardData(String strCmd) throws IOException {
		
		//LogpressoConnector conn = null;
		Logpresso logpresso = null;
		Cursor cursor = null;
		try {
			String strResult="";
			//conn = new LogpressoConnector();
			//cursor = conn.executeQuery("proc sp_dsmain()");
			logpresso = conn.getConnection();
			cursor = conn.executeQuery(logpresso, strCmd);
			//cursor = conn.executeQuery("table if_wth_fcst");
	        while (cursor.hasNext()) {
	        	Tuple tuple = cursor.next();
	        	//logger.info(tuple.toString());
	        	strResult = tuple.get("_return").toString();
	        	//logger.info(strResult);
	        }
	        //logger.info("[LOGPRESSO]"+strCmd+":"+strResult);
	        return strResult; 
		} finally {
	        if (cursor != null)
			cursor.close();
		    if (conn != null)
		    	conn.close(logpresso);
		}
	}
	List<String> queryLogpressoDashboardDataList(String strCmd) throws IOException {
		
		//LogpressoConnector conn = null;
		Logpresso logpresso = null;
		Cursor cursor = null;
		try {
			List<String> arResult= new ArrayList<String>();
			//conn = new LogpressoConnector();
			//cursor = conn.executeQuery("proc sp_dsmain()");
			logpresso = conn.getConnection();
			cursor = conn.executeQuery(logpresso, strCmd);
			//cursor = conn.executeQuery("table if_wth_fcst");
	        while (cursor.hasNext()) {
	        	Tuple tuple = cursor.next();
	        	//logger.info(tuple.get("_return").toString());
	        	arResult.add(tuple.get("_return").toString());
	        }
	        return arResult; 
		} finally {
	        if (cursor != null)
			cursor.close();
		    if (conn != null)
		    	conn.close(logpresso);
		}
	}

}
