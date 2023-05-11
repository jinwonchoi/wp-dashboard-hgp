package com.gencode.issuetool.extsite.obj;

import java.util.List;
import java.util.Map;

import com.gencode.issuetool.obj.Pojo;

public class KfslResultObj extends Pojo {
	String evaluationDate;
	String areaCode;
	String safetyGrade;
	String safetyScore;
	String safetyType1;//화재예방(빈도)지수
	String safetyType2;//화재예방(심도)지수
	Map<String, String> step1Result; //단계별 결과 code-지수
	List<String> evaluationDateList; // 전체조회시 평가일자 추출하여 목록생성
	String resultCode;
	public KfslResultObj() {
		super();
		// TODO Auto-generated constructor stub
	}
	public KfslResultObj(String evaluationDate, String areaCode, String safetyGrade, String safetyScore, String safetyType1,
			String safetyType2, Map<String, String> step1Result, List<String> evaluationDateList) {
		super();
		this.evaluationDate = evaluationDate;
		this.areaCode = areaCode;
		this.safetyGrade = safetyGrade;
		this.safetyScore = safetyScore;
		this.safetyType1 = safetyType1;
		this.safetyType2 = safetyType2;
		this.step1Result = step1Result;
		this.evaluationDateList = evaluationDateList;
	}
	public String getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(String evaluationDate) {
		this.evaluationDate = evaluationDate;
	}	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getSafetyGrade() {
		return safetyGrade;
	}
	public void setSafetyGrade(String safetyGrade) {
		this.safetyGrade = safetyGrade;
	}
	public String getSafetyScore() {
		return safetyScore;
	}
	public void setSafetyScore(String safetyScore) {
		this.safetyScore = safetyScore;
	}
	public String getSafetyType1() {
		return safetyType1;
	}
	public void setSafetyType1(String safetyType1) {
		this.safetyType1 = safetyType1;
	}
	public String getSafetyType2() {
		return safetyType2;
	}
	public void setSafetyType2(String safetyType2) {
		this.safetyType2 = safetyType2;
	}
	public Map<String, String> getStep1Result() {
		return step1Result;
	}
	public void setStep1Result(Map<String, String> step1Result) {
		this.step1Result = step1Result;
	}
	public List<String> getEvaluationDateList() {
		return evaluationDateList;
	}
	public void setEvaluationDateList(List<String> evaluationDateList) {
		this.evaluationDateList = evaluationDateList;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}	
}
