package com.gencode.issuetool.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ParamMapObj;
import com.gencode.issuetool.io.SearchMapByOrObj;
import com.gencode.issuetool.io.SearchMapObj;

abstract public class AbstractDaoImpl<T> implements AbstractDao<T> {

	protected final static Logger logger = LoggerFactory.getLogger(AbstractDaoImpl.class);
	protected final JdbcTemplate jdbcTemplate;
	protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	AbstractDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
//	@Override
//	public long getLastInsertId() {
//		return jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Long.class);
//	} 
	
	@Override
	public int getTotalCount(String query, MapSqlParameterSource namedParameters) {
		String queryCntStr = String.format("select count(*) totalCnt from (%s) a", query);
		int totalCount = namedParameterJdbcTemplate.queryForObject(queryCntStr, namedParameters, Integer.class);
		return totalCount;
	}

//	protected <T> Optional<PageResultObj<List<T>>> internalSearch(String queryStr, String alias, PageRequest req, Class<T> entityClass) {
//		String _alias = (alias!=null&&!alias.equals(""))?(alias+"."):"";
//		SearchMapObj searchMapObj = new SearchMapObj(req.getSearchMap(),_alias); 
//		SearchMapByOrObj searchMapByOrObj = new SearchMapByOrObj(req.getSearchByOrMap(),_alias); 
//		ParamMapObj paramMapObj = new ParamMapObj(req.getParamMap(),_alias);
//		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		namedParameters.addValues(searchMapObj.map()).addValues(searchMapByOrObj.map());
//		namedParameters.addValues(paramMapObj.map()).addValues(paramMapObj.map());
//		String orderByStr = "";
//		if (req.getSortField()!=null&&!req.getSortField().equals("")) {
//			orderByStr = " order by "+ req.getSortField()+
//			((req.getSortDir()==SortDirection.ASC)?" asc":" desc");
//		}
//		String limitStr = String.format(" limit %d, %d", req.getOffset(), req.getPageSize());
//
//		int totalCnt = this.getTotalCount(queryStr+paramMapObj.andQuery()+searchMapObj.andQuery()+searchMapByOrObj.orQuery()
//									, namedParameters);
//		
////		@SuppressWarnings("unchecked")
////		Class<T> classOfObjectClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//		
//		List<T> tasks = namedParameterJdbcTemplate.query
//				(queryStr+paramMapObj.andQuery()+searchMapObj.andQuery()+searchMapByOrObj.orQuery()+orderByStr+limitStr
//						, namedParameters
//						, new BeanPropertyRowMapper<T>(entityClass));
//		PageResultObj<List<T>> pageResultObj = new PageResultObj<List<T>>();
//		pageResultObj.setSuccess();
//		pageResultObj.setItem(tasks);
//		pageResultObj.setTotalCnt(totalCnt);
//		pageResultObj.setPageNo(req.getPageNo());
//		pageResultObj.setPageSize(req.getPageSize());
//		return Optional.ofNullable(pageResultObj);
//	}

	protected <T> Optional<PageResultObj<List<T>>> internalSearch(String queryStr, PageRequest req, Class<T> entityClass) {
		return internalSearch(queryStr, "", req, new BeanPropertyRowMapper<T>(entityClass));
	}
	protected <T> Optional<PageResultObj<List<T>>> internalSearch(String queryStr, PageRequest req, RowMapper<T> rowMapper) {
		return internalSearch(queryStr, "", req, rowMapper);
	}

	protected <T> Optional<PageResultObj<List<T>>> internalSearch(String queryStr, String alias, PageRequest req, RowMapper<T> rowMapper) {
		return internalSearch(queryStr, "", alias, req, rowMapper);
	}
	protected <T> Optional<PageResultObj<List<T>>> internalSearch(String queryStr, String orderByStr, String alias, PageRequest req, RowMapper<T> rowMapper) {
		String _alias = (alias!=null&&!alias.equals(""))?(alias+"."):"";
		SearchMapObj searchMapObj = new SearchMapObj(req.getSearchMap(), _alias);
		SearchMapByOrObj searchMapByOrObj = new SearchMapByOrObj(req.getSearchByOrMap(),_alias);
		ParamMapObj paramMapObj = new ParamMapObj(req.getParamMap(),_alias);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValues(searchMapObj.map()).addValues(searchMapByOrObj.map());
		namedParameters.addValues(paramMapObj.map()).addValues(paramMapObj.map());
		if (orderByStr.equals("")&&req.getSortField()!=null&&!req.getSortField().equals("")) {
			orderByStr = " order by "+ _alias + Utils.unCamel(req.getSortField())+
			((req.getSortDir()==SortDirection.ASC)?" asc":" desc");
		}
		String limitStr = String.format(" limit %d, %d", req.getOffset(), req.getPageSize());

		int totalCnt = this.getTotalCount(queryStr+paramMapObj.andQuery()+searchMapObj.andQuery()+searchMapByOrObj.orQuery()
							, namedParameters);
		
//		@SuppressWarnings("unchecked")
//		Class<T> classOfObjectClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
		List<T> tasks = namedParameterJdbcTemplate.query
				(queryStr+paramMapObj.andQuery()+searchMapObj.andQuery()+searchMapByOrObj.orQuery()+orderByStr+limitStr
						, namedParameters
						, rowMapper);
		PageResultObj<List<T>> pageResultObj = new PageResultObj<List<T>>();
		pageResultObj.setSuccess();
		pageResultObj.setItem(tasks);
		pageResultObj.setTotalCnt(totalCnt);
		pageResultObj.setPageNo(req.getPageNo());
		pageResultObj.setPageSize(req.getPageSize());
		return Optional.ofNullable(pageResultObj);
	}

}
