package com.gencode.issuetool.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gencode.issuetool.etc.ReturnCode;

public class PageResultObj<T> extends ResultObj<T> {
	private static final long serialVersionUID = 1L;
	int totalCnt;
	int pageSize;
	int pageNo;

	//page number, page size, sort direction sort field
	public PageResultObj() {
		super();
	}
	
	public PageResultObj(ReturnCode resultCode) {
		super(resultCode);
	}

	public PageResultObj(ReturnCode resultCode, PageResultObj<T> t) {
		super(resultCode, t.item);
		totalCnt = t.totalCnt;
		pageSize = t.pageSize;
		pageNo = t.pageNo;
	}
	
	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setSuccess() {
		this.setResultCode(ReturnCode.SUCCESS.get());
		this.setResultMsg(ReturnCode.STR_SUCCESS.get());
	}
	
	static public <T> PageResultObj<T> success() {
		PageResultObj obj = new PageResultObj(ReturnCode.SUCCESS);
		return obj;
	}
	
	static public <T> PageResultObj<T> errorUnknown() {
		PageResultObj obj = new PageResultObj(ReturnCode.ERROR_UNKNOWN);
		return obj;
	}


	static public <T> PageResultObj<T> dataNotFound() {
		PageResultObj<T> obj = new PageResultObj<T>(ReturnCode.DATA_NOT_FOUND);
		return obj;
	}
	
}
