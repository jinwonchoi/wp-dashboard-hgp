package com.gencode.issuetool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gencode.issuetool.extsite.client.KfslClient;
import com.gencode.issuetool.extsite.obj.KfslResultObj;
import com.gencode.issuetool.util.JsonUtils;

@Service
public class KfslAPIService {
	private final static Logger logger = LoggerFactory.getLogger(KfslAPIService.class);
	
	@Value("${kfsl.api.url:http://dt.rozetatech.com:89/Api}")
	String apiUrl;//="http://dt.rozetatech.com:3000/hg/api";

	@Autowired
	private CacheMapManager cacheMapManager;

	//new HashMap<String, String>(){{
	//	put("EVALUATIONLIST_CODE","100");
	////	put("START_EVALUATION_DATE","2023-02-01");
	////	put("END_EVALUATION_DATE","2023-02-01");
	//}}
	public Map<String, Object> getTotalResult(Map<String, String> map) throws Exception{
		KfslClient<Map<String, Object>> kfslClient = new KfslClient<Map<String, Object>>();
		Map<String, Object> result = (Map<String, Object>) kfslClient.call(apiUrl+"/getTotalResult", map, Map.class);
		logger.info(JsonUtils.toJson(result).toString());
		return result;
	}

	public Map<String, Object> getSpaceResult(Map<String, String> map) throws Exception{
		KfslClient<Map<String, Object>> kfslClient = new KfslClient<Map<String, Object>>();
		Map<String, Object> result = (Map<String, Object>) kfslClient.call(apiUrl+"/getSpaceResult", map, Map.class);
		logger.info(JsonUtils.toJson(result).toString());
		return result;
	}
	
	/**
	 * 사업장공간별안전평가: 
	 * 전체결과에서 추산 
	 * 날짜를 선택하지 않고 기본 최근일
	 * 결과에서 나온 평가을에서 목록추출하여 평가일 선택
	 * @throws Exception 
	 */
	
	public KfslResultObj getTotalResultMap(String evaluationDate) throws Exception {
		KfslResultObj kfslResultObj = new KfslResultObj();
		Map<String, Object> resultMap = this.getTotalResult(new HashMap<String, String>() {{
			put("EVALUATIONLIST_CODE","100");
			if (evaluationDate!=null) {
				put("START_EVALUATION_DATE",evaluationDate);
				put("END_EVALUATION_DATE",evaluationDate);
			}
		}});
		return getResultObj(resultMap);
//		List<String> evaluationDateList = new ArrayList<String>();
//		((List<Map<String, Object>>)resultMap.get("data")).forEach(e -> {
//			if (evaluationDateList.size()==0) {
//				kfslResultObj.setEvaluationDate((String)e.get("EVALUATION_DATE"));
//				kfslResultObj.setSafetyGrade((String)e.get("SAFETY_GRADE"));
//				kfslResultObj.setSafetyScore((String)e.get("SAFETY_SCORE"));
//				kfslResultObj.setSafetyType1((String)e.get("SAFETY_TYPE1"));
//				kfslResultObj.setSafetyType2((String)e.get("SAFETY_TYPE2"));
//				Map<String, String> stepResultMap = new HashMap<String, String>(); 
//				((List<Map<String, String>>)e.get("STEP1_RESULT")).forEach(ex -> {
//					stepResultMap.put(ex.get("STEP1_NAME"), ex.get("STEP1_JISU"));
//				});
//				kfslResultObj.setStep1Result(stepResultMap);
//			}
//			evaluationDateList.add((String)e.get("EVALUATION_DATE"));
//		});
//		//evaluationDateList.sort(null);
//		kfslResultObj.setEvaluationDateList(evaluationDateList);
//		return kfslResultObj;
	}
	
	/**
	 * 공간별안전등급분포현황
	 * 공통제외 개별 6개 구역코드 각각 조회
	 * 
	 */
	public Map<String, KfslResultObj> getSpaceResultMap(String evaluationDate) throws Exception {
		//KfslResultObj kfslResultObj = new KfslResultObj();
		Map<String, KfslResultObj> kfslResultMap = new HashMap<String, KfslResultObj>(); 
		cacheMapManager.getCommonCodes("KFSL_AREA_CODE").stream().filter(e->!e.getItemKey().equals("10000")).forEach(e -> {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			try {
				resultMap = this.getSpaceResult(new HashMap<String, String>() {{
					put("EVALUATIONLIST_CODE","100");
					put("AREA_CODE",(String)e.getItemKey());
					if (evaluationDate!=null) {
						put("START_EVALUATION_DATE",evaluationDate);
						put("END_EVALUATION_DATE",evaluationDate);
					}
				}});
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
			kfslResultMap.put(e.getItemKey(), getResultObj(resultMap));
		});
		return kfslResultMap;
//		List<String> evaluationDateList = new ArrayList<String>();
//		((List<Map<String, Object>>)resultMap.get("data")).forEach(e -> {
//			if (evaluationDateList.size()==0) {
//				kfslResultObj.setEvaluationDate((String)e.get("EVALUATION_DATE"));
//				kfslResultObj.setSafetyGrade((String)e.get("SAFETY_GRADE"));
//				kfslResultObj.setSafetyScore((String)e.get("SAFETY_SCORE"));
//				kfslResultObj.setSafetyType1((String)e.get("SAFETY_TYPE1"));
//				kfslResultObj.setSafetyType2((String)e.get("SAFETY_TYPE2"));
//				Map<String, String> stepResultMap = new HashMap<String, String>(); 
//				((List<Map<String, String>>)e.get("STEP1_RESULT")).forEach(ex -> {
//					stepResultMap.put(ex.get("STEP1_NAME"), ex.get("STEP1_JISU"));
//				});
//				kfslResultObj.setStep1Result(stepResultMap);
//			}
//			evaluationDateList.add((String)e.get("EVALUATION_DATE"));
//		});
//		//evaluationDateList.sort(null);
//		kfslResultObj.setEvaluationDateList(evaluationDateList);
//		return kfslResultObj;
	}
	
	KfslResultObj getResultObj(Map<String, Object> resultMap) {
		KfslResultObj kfslResultObj = new KfslResultObj();
		List<String> evaluationDateList = new ArrayList<String>();
		((List<Map<String, Object>>)resultMap.get("data")).forEach(e -> {
			if (evaluationDateList.size()==0) {
				kfslResultObj.setEvaluationDate((String)e.get("EVALUATION_DATE"));
				kfslResultObj.setSafetyGrade((String)e.get("SAFETY_GRADE"));
				kfslResultObj.setSafetyScore((String)e.get("SAFETY_SCORE"));
				kfslResultObj.setSafetyType1((String)e.get("SAFETY_TYPE1"));
				kfslResultObj.setSafetyType2((String)e.get("SAFETY_TYPE2"));
				Map<String, String> stepResultMap = new LinkedHashMap<String, String>(); 
				((List<Map<String, String>>)e.get("STEP1_RESULT")).forEach(ex -> {
					stepResultMap.put(ex.get("STEP1_NAME"), ex.get("STEP1_JISU"));
				});
				kfslResultObj.setStep1Result(stepResultMap);
			}
			evaluationDateList.add((String)e.get("EVALUATION_DATE"));
		});
		//evaluationDateList.sort(null);
		kfslResultObj.setEvaluationDateList(evaluationDateList);
		return kfslResultObj;
		
	}
}
