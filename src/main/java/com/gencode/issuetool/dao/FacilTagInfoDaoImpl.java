package com.gencode.issuetool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
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
import com.gencode.issuetool.obj.FacilTagInfo;

@Component("FacilTagInfoDao")
public class FacilTagInfoDaoImpl extends AbstractDaoImpl implements FacilTagInfoDao {

	final String fields= "id, tag_name, facility_id, data_type, tag_desc, tag_style, updated_dtm, created_dtm";
	
	public FacilTagInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(FacilTagInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO facil_tag_info (tag_name, facility_id, data_type, tag_desc, tag_style, now(), now()) " + 
				"VALUES(:tagName, :facilityId, :dataType, :tagDesc, :tagStyle)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<FacilTagInfo> load(long id) {
		FacilTagInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from facil_tag_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toFacilTagInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from facil_tag_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(FacilTagInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE facil_tag_info SET " +
				"tag_name    = :tagName,"+ 
				"facility_id = :facilityId,"+
				"data_type   = :dataType,"+
				"tag_desc    = :tagDesc,"+
				"tag_style   = :tagStyle,"+
				"updated_dtm = now(), "+
				"created_dtm = now() " +
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<FacilTagInfo>> loadAll() {
		List<FacilTagInfo> list = jdbcTemplate.query(
				"select "+fields+" from facil_tag_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toFacilTagInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<FacilTagInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<FacilTagInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from facil_tag_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<FacilTagInfo>(FacilTagInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<FacilTagInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from facil_tag_info where 1=1";
		return internalSearch(queryStr, req, FacilTagInfo.class);
	}

}
