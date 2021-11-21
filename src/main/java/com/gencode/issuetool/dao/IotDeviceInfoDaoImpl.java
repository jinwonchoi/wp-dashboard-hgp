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
import com.gencode.issuetool.obj.IotDeviceInfo;

@Component("IotDeviceInfoDao")
public class IotDeviceInfoDaoImpl extends AbstractDaoImpl implements IotDeviceInfoDao {

	final String fields= "id,device_id,device_type,interior_id,pos_x,pos_y,pos_z,updated_dtm,created_dtm";
	
	public IotDeviceInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(IotDeviceInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO iot_device_info (device_id,device_type,interior_id,pos_x,pos_y,pos_z,updated_dtm,created_dtm) " + 
				"VALUES(:deviceId,:deviceType,:interiorId,:posX,:posY,:posZ,NOW(3), NOW(3))"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<IotDeviceInfo> load(long id) {
		IotDeviceInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from iot_device_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toIotDeviceInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from iot_device_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(IotDeviceInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE iot_device_info SET " +
				"device_id  =:deviceId  ,"+
				"device_type=:deviceType,"+
				"interior_id    =:interiorId    ,"+
				"pos_x      =:posX      ,"+
				"pos_y      =:posY      ,"+
				"pos_z      =:posZ      ,"+
				"updated_dtm=:updatedDtm,"+
				"created_dtm=:createdDtm "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<IotDeviceInfo>> loadAll() {
		List<IotDeviceInfo> list = jdbcTemplate.query(
				"select "+fields+" from iot_device_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toIotDeviceInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<IotDeviceInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<IotDeviceInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from iot_device_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<IotDeviceInfo>(IotDeviceInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<IotDeviceInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from iot_device_info where 1=1";
		return internalSearch(queryStr, req, IotDeviceInfo.class);
	}

}
