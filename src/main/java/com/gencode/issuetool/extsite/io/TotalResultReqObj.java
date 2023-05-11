package com.gencode.issuetool.extsite.io;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.UpperCamelCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TotalResultReqObj {
	String EVALUATIONLIST_CODE;
	String START_EVALUATION_DATE;
	String END_EVALUATION_DATE;
	
	public TotalResultReqObj() {
		
	}

	public TotalResultReqObj(String eVALUATIONLIST_CODE) {
		super();
		EVALUATIONLIST_CODE = eVALUATIONLIST_CODE;
	}

	public TotalResultReqObj(String eVALUATIONLIST_CODE, String sTART_EVALUATION_DATE, String eND_EVALUATION_DATE) {
		super();
		EVALUATIONLIST_CODE = eVALUATIONLIST_CODE;
		START_EVALUATION_DATE = sTART_EVALUATION_DATE;
		END_EVALUATION_DATE = eND_EVALUATION_DATE;
	}

	public String getEVALUATIONLIST_CODE() {
		return EVALUATIONLIST_CODE;
	}

	public void setEVALUATIONLIST_CODE(String eVALUATIONLIST_CODE) {
		EVALUATIONLIST_CODE = eVALUATIONLIST_CODE;
	}

	public String getSTART_EVALUATION_DATE() {
		return START_EVALUATION_DATE;
	}

	public void setSTART_EVALUATION_DATE(String sTART_EVALUATION_DATE) {
		START_EVALUATION_DATE = sTART_EVALUATION_DATE;
	}

	public String getEND_EVALUATION_DATE() {
		return END_EVALUATION_DATE;
	}

	public void setEND_EVALUATION_DATE(String eND_EVALUATION_DATE) {
		END_EVALUATION_DATE = eND_EVALUATION_DATE;
	}
}
