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
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.EtcDeviceInfo;

@Component("EtcDeviceInfoDao")
public class EtcDeviceInfoDaoImpl extends AbstractDaoImpl implements EtcDeviceInfoDao {

	final String fields= "id,device_id,device_type,device_name,device_serno,device_desc,interior_id,pos_x,pos_y,pos_z,dir_x,dir_y,dir_z,register_date,install_date,term_date,updated_dtm,created_dtm";
	
	public EtcDeviceInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(EtcDeviceInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO etc_device_info (device_id,device_type,device_name,device_serno,device_desc,interior_id,pos_x,pos_y,pos_z,dir_x,dir_y,dir_z,register_date,install_date,term_date,updated_dtm,created_dtm) " + 
				"VALUES(:deviceId,:deviceType,:deviceName,:deviceSerno,:deviceDesc,:interiorId,:posX,:posY,:posZ,:dirX,:dirY,:dirZ,:registerDate,:installDate,:termDate,NOW(3), NOW(3))"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<EtcDeviceInfo> load(long id) {
		EtcDeviceInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from etc_device_info where id = :id ",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toEtcDeviceInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from etc_device_info " + 
				" WHERE id=:id "
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(EtcDeviceInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE etc_device_info SET " +
				"id           =:id          ,"+
				"device_id    =:deviceId    ,"+
				"device_type  =:deviceType  ,"+
				"device_name  =:deviceName  ,"+
				"device_serno =:deviceSerno ,"+
				"device_desc  =:deviceDesc  ,"+
				"interior_id      =:interiorId      ,"+
				"pos_x        =:posX        ,"+
				"pos_y        =:posY        ,"+
				"pos_z        =:posZ        ,"+
				"dir_x        =:dirX        ,"+
				"dir_y        =:dirY        ,"+
				"dir_z        =:dirZ        ,"+
				"register_date=:registerDate,"+
				"install_date =:installDate ,"+
				"term_date    =:termDate    ,"+
				"updated_dtm  =NOW(3)"+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<EtcDeviceInfo>> loadAll() {
		List<EtcDeviceInfo> list = jdbcTemplate.query(
				"select "+fields+" from etc_device_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toEtcDeviceInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<EtcDeviceInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<EtcDeviceInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from etc_device_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<EtcDeviceInfo>(EtcDeviceInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<EtcDeviceInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from etc_device_info where 1=1";
		return internalSearch(queryStr, req, EtcDeviceInfo.class);
	}

}
