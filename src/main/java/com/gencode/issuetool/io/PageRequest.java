package com.gencode.issuetool.io;

import java.util.Map;

import com.gencode.issuetool.obj.Pojo;

public class PageRequest extends Pojo {
	int pageNo;
	int pageSize;
	int offset;
	int limit;
	int lastOffset;
	SortDirection sortDir;
	String sortField;
	Map<String, String> searchMap;
	Map<String, String> searchByOrMap;
	Map<String, String> paramMap;
	Map<String, String> paramValMap;

	public PageRequest() {
	}

	public PageRequest(	int pageNo, int pageSize, int lastOffset, SortDirection sortDir, String sortField, Map<String, String> searchMap, Map<String, String> searchByOrMap, Map<String, String> paramMap, Map<String, String> paramValMap) {
		setPage(pageNo, pageSize);
		this.lastOffset=lastOffset;
		this.sortField = sortField;
		this.sortDir = sortDir;
		this.searchMap = searchMap;
		this.searchByOrMap = searchByOrMap;
		this.paramMap = paramMap;
		this.paramValMap=paramValMap;
	}
	
	public PageRequest(	int pageNo, int pageSize, int lastOffset, Map<String, String> searchMap, Map<String, String> searchByOrMap,Map<String, String> paramMap, Map<String, String> paramValMap) {
		setPage(pageNo, pageSize);
		this.lastOffset=lastOffset;
		this.searchMap = searchMap;
		this.searchByOrMap = searchByOrMap;
		this.paramMap = paramMap;
		this.paramValMap = paramValMap;
	}

	public PageRequest(	int pageNo, int pageSize, int lastOffset) {
		setPage(pageNo, pageSize);
		this.lastOffset=lastOffset;		
	}

	void setPage(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		offset = (pageNo-1)*pageSize;
		limit = pageNo*pageSize;
	}
	
	void setPage() {
		offset = (pageNo-1)*pageSize;
		limit = pageNo*pageSize;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public SortDirection getSortDir() {
		return sortDir;
	}

	public String getSortField() {
		return sortField;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSortDir(SortDirection sortDir) {
		this.sortDir = sortDir;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public Map<String, String> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<String, String> searchMap) {
		this.searchMap = searchMap;
	}

	public Map<String, String> getSearchByOrMap() {
		return searchByOrMap;
	}

	public void setSearchByOrMap(Map<String, String> searchByOrMap) {
		this.searchByOrMap = searchByOrMap;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	public Map<String, String> getParamValMap() {
		return paramValMap;
	}

	public void setParamValMap(Map<String, String> paramValMap) {
		this.paramValMap = paramValMap;
	}

	public int getOffset() {
		if (offset == 0 && limit == 0) setPage();
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		if (offset == 0 && limit == 0) setPage();
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLastOffset() {
		return lastOffset;
	}

	public void setLastOffset(int lastOffset) {
		this.lastOffset = lastOffset;
	}
}
