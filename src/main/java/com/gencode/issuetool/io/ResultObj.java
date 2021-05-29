package com.gencode.issuetool.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.obj.Pojo;


public class ResultObj<T> extends Pojo implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String resultCode;
	protected String resultMsg;
	protected T item;//userNum,callingNum,callingName,registerDate,expiredDate,jsonMsg,durationType,downloadCnt,updateDate;

	public ResultObj() {
		// TODO Auto-generated constructor stub
	}
		
	public ResultObj(ReturnCode resultCode) {
		this.resultCode = resultCode.get();
		this.resultMsg = ReturnCode.valueOf("STR_"+resultCode).get();
	}

	public ResultObj(ReturnCode resultCode, T t) {
		this.resultCode = resultCode.get();
		this.resultMsg = ReturnCode.valueOf("STR_"+resultCode).get();
		item = t;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

	static public <T> ResultObj<T> success(T item) {
		ResultObj<T> obj = new ResultObj<T>(ReturnCode.SUCCESS);
		obj.setItem(item);
		return obj;
	}
	
	static public <T> ResultObj<T> error(ReturnCode resultCode) {
		return new ResultObj<T>(resultCode);		
	}


	static public <T> ResultObj<T> dataNotFound() {
		ResultObj<T> obj = new ResultObj<T>(ReturnCode.DATA_NOT_FOUND);
		return obj;
	}
	
	static public <T> ResultObj<T> success() {
		ResultObj<T> obj = new ResultObj<T>(ReturnCode.SUCCESS);
		return obj;
	}
	
	static public <T> ResultObj<T> errorUnknown() {
		ResultObj<T> obj = new ResultObj<T>(ReturnCode.ERROR_UNKNOWN);
		return obj;
	}

	static public boolean isSuccess(ReturnCode returnCode) {
		return returnCode == ReturnCode.SUCCESS;
	}

	static public boolean isSuccess(String returnCode) {
		return ReturnCode.SUCCESS.get().equals(returnCode);
	}
}
