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
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.PlantInfo;

@Component("PlantInfoDao")
public class PlantInfoDaoImpl extends AbstractDaoImpl implements PlantInfoDao {

	final String fields= "id,plant_no,plant_name,description";
	
	public PlantInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(PlantInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO plant_info (plant_no,plant_name,description) " + 
				"VALUES(:plantNo, :plantName, :description)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<PlantInfo> load(long id) {
		PlantInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from plant_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toPlantInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from plant_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));		
	}

	@Override
	public long update(PlantInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE plant_info SET " +
				"plant_no    = :plantNo,"+
				"plant_name  = :plantName,"+
				"description = :description "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<PlantInfo>> loadAll() {
		List<PlantInfo> list = jdbcTemplate.query(
				"select "+fields+" from plant_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toPlantInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<PlantInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<PlantInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from plant_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<PlantInfo>(PlantInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<PlantInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from plant_info where 1=1";
		return internalSearch(queryStr, req, PlantInfo.class);
	}

}
