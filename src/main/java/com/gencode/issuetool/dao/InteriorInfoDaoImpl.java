/**=========================================================================================
<overview>공간기준정보관련 DAO 처리구현
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
import com.gencode.issuetool.obj.InteriorInfo;

@Component("InteriorInfoDao")
public class InteriorInfoDaoImpl extends AbstractDaoImpl implements InteriorInfoDao {

	final String fields= "id, interior_code , interior_name, area_id, pos_x, pos_y, pos_z, size_x, size_y, size_z, description";
		
	public InteriorInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(InteriorInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO interior_info (interior_code , interior_name, area_id, pos_x, pos_y, pos_z, size_x, size_y, size_z, description) " + 
				"VALUES(:interiorCode , :interiorName, :areaId, :posX, :posY, :posZ, :sizeX, :sizeY, :sizeZ, :description)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<InteriorInfo> load(long id) {
		InteriorInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from interior_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toInteriorInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from interior_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(InteriorInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE interior_info SET " +
				" interior_code  =:interiorCode ,"+
				" interior_name=:interiorName,"+
				" area_id      =:areaId,"+
				" pos_x        =:posX,"+
				" pos_y        =:posY,"+
				" pos_z        =:posZ,"+
				" size_x       =:sizeX,"+
				" size_y       =:sizeY,"+
				" size_z       =:sizeZ,"+
				" description  =:description "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<InteriorInfo>> loadAll() {
		List<InteriorInfo> list = jdbcTemplate.query(
				"select "+fields+" from interior_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toInteriorInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<InteriorInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<InteriorInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from interior_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<InteriorInfo>(InteriorInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<InteriorInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from interior_info where 1=1";
		return internalSearch(queryStr, req, InteriorInfo.class);
	}

}
