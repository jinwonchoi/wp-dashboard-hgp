/**=========================================================================================
<overview>구역기준정보관련 DAO 처리구현
  </overview>
==========================================================================================*/
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
import com.gencode.issuetool.obj.AreaInfo;

@Component("AreaInfoDao")
public class AreaInfoDaoImpl extends AbstractDaoImpl implements AreaInfoDao {

	final String fields= "id, area_code, area_name, plant_id, size_x, size_y, size_z, description";
	
	
	public AreaInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(AreaInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO area_info (area_code, area_name, plant_id, size_x, size_y, size_z, description) " + 
				"VALUES(:areaCode, :areaName, :plantId, :sizeX, :sizeY, :sizeZ, :description)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<AreaInfo> load(long id) {
		AreaInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from area_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toAreaInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from area_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(AreaInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE area_info SET " +
				" area_code  =:areaCode,"+
				" area_name  =:areaName,"+
				" plant_id   =:plantId,"+
				" size_x     =:sizeX,"+
				" size_y     =:sizeY,"+
				" size_z     =:sizeZ,"+
				" description=:description "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<AreaInfo>> loadAll() {
		List<AreaInfo> list = jdbcTemplate.query(
				"select "+fields+" from area_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toAreaInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<AreaInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<AreaInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from area_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<AreaInfo>(AreaInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<AreaInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from area_info where 1=1";
		return internalSearch(queryStr, req, AreaInfo.class);
	}

}
