/**=========================================================================================
<overview>기타장비기준정보관련 DAO 처리구현
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
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.EtcDeviceInfo;

@Component("EtcDeviceInfoDao")
public class EtcDeviceInfoDaoImpl extends AbstractDaoImpl implements EtcDeviceInfoDao {

	final String fields= "id,device_id,org_device_id,device_type,interior_id,pmt_no,rptr_no,seq,cctv_path,cctv_userid,cctv_pwd,device_desc,updated_dtm,created_dtm";
	
	public EtcDeviceInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(EtcDeviceInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO etc_device_info (device_id,org_device_id,device_type,interior_id,pmt_no,rptr_no,seq,cctv_path,cctv_userid,cctv_pwd,device_desc,updated_dtm,created_dtm) " + 
				"VALUES(:deviceId,:orgDeviceId,:deviceType,:interiorId,:pmtNo,:rptrNo,:seq,:cctvPath,:cctvUserid,:cctvPwd,:deviceDesc,:updatedDate,NOW(3), NOW(3))"
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
				"device_id     =:deviceId,"+
				"org_device_id =:orgDeviceId,"+
				"device_type   =:deviceType,"+
				"interior_id   =:interiorId,"+
				"pmt_no        =:pmtNo,"+
				"rptr_no       =:rptrNo,"+
				"seq           =:seq,"+
				"cctv_path     =:cctvPath,"+
				"cctv_userid   =:cctvUserid,"+
				"cctv_pwd      =:cctvPwd,"+
				"device_desc   =:deviceDesc ,"+
				"updated_dtm   =NOW(3)"+
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
