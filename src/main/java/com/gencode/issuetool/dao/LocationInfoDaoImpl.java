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
import com.gencode.issuetool.obj.LocationInfo;

@Component("LocationInfoDao")
public class LocationInfoDaoImpl extends AbstractDaoImpl implements LocationInfoDao {

	final String fields= "id, loc_type, size_x, size_y, size_z, parent_loc_id";
	
	public LocationInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(LocationInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO location_info (loc_type, size_x, size_y, size_z, parent_loc_id) " + 
				"VALUES(:locType, :sizeX, :sizeY, :sizeZ, :parentLocId)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<LocationInfo> load(long id) {
		LocationInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from location_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toLocationInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from location_info " + 
				"WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(LocationInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE location_info SET " +
				"loc_type			=:locType,"+
				"size_x				=:sizeX,"+
				"size_y				=:sizeY,"+
				"size_z			=:sizeZ,"+
				"parent_loc_id			=:parentLocId " +				
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<LocationInfo>> loadAll() {
		List<LocationInfo> list = jdbcTemplate.query(
				"select "+fields+" from location_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toLocationInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<LocationInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<LocationInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from location_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<LocationInfo>(LocationInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<LocationInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from location_info where 1=1";
		return internalSearch(queryStr, req, LocationInfo.class);
	}

}
