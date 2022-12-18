/**=========================================================================================
<overview>설비기준정보관련 DAO 처리구현
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
import com.gencode.issuetool.obj.FacilityInfo;

@Component("FacilityInfoDao")
public class FacilityInfoDaoImpl extends AbstractDaoImpl implements FacilityInfoDao {

	final String fields= "id, facil_code, facil_name, facil_name2, plant_part_id, description";
	
	
	public FacilityInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(FacilityInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO facility_info (facil_code, facil_name, facil_name2,plant_part_id,description) " + 
				"VALUES(:facilCode, :facilName, :facilName2,:plantId,:description)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<FacilityInfo> load(long id) {
		FacilityInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from facility_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toFacilityInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from facility_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(FacilityInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE facility_info SET " +
				"facil_code =:facilCode,"+
				"facil_name =:facilName,"+
				"facil_name2 =:facilName2,"+
				"plant_part_id   =:plantPartId,"+
				"description=:description "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<FacilityInfo>> loadAll() {
		List<FacilityInfo> list = jdbcTemplate.query(
				"select "+fields+" from facility_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toFacilityInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<FacilityInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<FacilityInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from facility_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<FacilityInfo>(FacilityInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<FacilityInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from facility_info where 1=1";
		return internalSearch(queryStr, req, FacilityInfo.class);
	}

}
