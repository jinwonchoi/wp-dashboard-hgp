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
import com.gencode.issuetool.obj.PlantPartInfo;

@Component("PlantPartInfoDao")
public class PlantPartInfoDaoImpl extends AbstractDaoImpl implements PlantPartInfoDao {

	final String fields= "id, plant_part_code, plant_part_name, plant_id, description";
	
	
	public PlantPartInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(PlantPartInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO plant_part_info (plant_part_code, plant_part_name, plant_id, description) " + 
				"VALUES(:plantPartCode, :plantPartName, :plantId, :description)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<PlantPartInfo> load(long id) {
		PlantPartInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from plant_part_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toPlantPartInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from plant_part_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(PlantPartInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE plant_part_info SET " +
				" plant_part_code  =:plantPartCode,"+
				" plant_part_name  =:plantPartName,"+
				" plant_id   =:plantId,"+
				" description=:description "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<PlantPartInfo>> loadAll() {
		List<PlantPartInfo> list = jdbcTemplate.query(
				"select "+fields+" from plant_part_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toPlantPartInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<PlantPartInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<PlantPartInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from plant_part_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<PlantPartInfo>(PlantPartInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<PlantPartInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from plant_part_info where 1=1";
		return internalSearch(queryStr, req, PlantPartInfo.class);
	}

}
