package com.gencode.issuetool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.gencode.issuetool.etc.ObjMapper;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.FacilTagMapping;

@Component("FacilTagMappingDao")
public class FacilTagMappingDaoImpl extends AbstractDaoImpl implements FacilTagMappingDao {

	final String fields= "facil_rule_id, tag_id";
	
	public FacilTagMappingDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(FacilTagMapping t) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void register(List<FacilTagMapping> t) {
		jdbcTemplate.batchUpdate("INSERT INTO facil_tag_mapping (facil_rule_id, tag_id)  VALUES (?,?)", 
			new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, t.get(i).getFacilRuleId());
					ps.setLong(2, t.get(i).getTagId());
				}

				@Override
				public int getBatchSize() {
					return t.size();
				}
		});
		
	}

	
	@Override
	public Optional<FacilTagMapping> load(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long delete(FacilTagMapping t) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("facilRuleId", t.getFacilRuleId());
		map.addValue("tagId", t.getTagId());
		return namedParameterJdbcTemplate.update("delete from facil_tag_mapping " + 
				" WHERE facil_rule_id=:facilRuleId and tag_id = :tagId"
				,map);
	}

	@Override
	public long deleteByRuleId(long id) {
		return namedParameterJdbcTemplate.update("delete from facil_tag_mapping " + 
				" WHERE facil_rule_id=:facilRuleId "
				,new MapSqlParameterSource("facilRuleId",id));
	}

	
	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long update(FacilTagMapping t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<FacilTagMapping>> loadAll() {
		List<FacilTagMapping> list = jdbcTemplate.query(
				"select "+fields+" from facil_tag_mapping where 1=1", (resultSet, i) -> {
            return ObjMapper.toFacilTagMapping(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<FacilTagMapping>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<FacilTagMapping> t = namedParameterJdbcTemplate.query
				("select "+fields+" from facil_tag_mapping where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<FacilTagMapping>(FacilTagMapping.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<FacilTagMapping>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from facil_tag_mapping where 1=1";
		return internalSearch(queryStr, req, FacilTagMapping.class);
	}

}
