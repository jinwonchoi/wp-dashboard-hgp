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
import com.gencode.issuetool.obj.IotRuleInfo;

@Component("IotRuleInfoDao")
public class IotRuleInfoDaoImpl extends AbstractDaoImpl implements IotRuleInfoDao {

	final String fields= "id, iot_rule_code, iot_rule_name, data_type, interior_id, critic_val, rule_desc, tip_desc, updated_dtm, created_dtm";
	
	public IotRuleInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(IotRuleInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO iot_rule_info (iot_rule_code, iot_rule_name, data_type, interior_id, critic_val, rule_desc, tip_desc, updated_dtm, created_dtm) " + 
				"VALUES(:iotRuleCode,iotRuleName, dataType, :interiorId, :criticVal, :ruleDesc, :tipDesc, now(), now())"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<IotRuleInfo> load(long id) {
		IotRuleInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from iot_rule_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toIotRuleInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from iot_rule_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(IotRuleInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE iot_rule_info SET " +
				"iot_rule_code    = :iotRuleCode,"+
				"iot_rule_name    = :iotRuleName,"+
				"data_type    = :dataType,"+
				"interior_id  = :interiorId,"+
				"critic_val  = :criticVal,"+
				"rule_desc  = :ruleDesc,"+
				"tip_desc  = :tipDesc,"+
				"updated_dtm  = now(),"+
				"created_dtm = now() "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<IotRuleInfo>> loadAll() {
		List<IotRuleInfo> list = jdbcTemplate.query(
				"select "+fields+" from iot_rule_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toIotRuleInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<IotRuleInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<IotRuleInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from iot_rule_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<IotRuleInfo>(IotRuleInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<IotRuleInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from iot_rule_info where 1=1";
		return internalSearch(queryStr, req, IotRuleInfo.class);
	}

}
