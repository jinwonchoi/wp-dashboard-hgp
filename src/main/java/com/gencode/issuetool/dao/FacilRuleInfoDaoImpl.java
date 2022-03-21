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
import com.gencode.issuetool.obj.FacilRuleInfo;

@Component("FacilRuleInfoDao")
public class FacilRuleInfoDaoImpl extends AbstractDaoImpl implements FacilRuleInfoDao {

	final String fields= "id, facil_rule_code,facil_rule_name, data_type, facility_id, critic_val, rule_desc, tip_desc, updated_dtm, created_dtm";
	final String queryStr= "select fri.id, fri.facil_rule_code,fri.facil_rule_name, fri.data_type, fri.facility_id, fri.critic_val, fri.rule_desc, fri.tip_desc, fri.updated_dtm, fri.created_dtm from facil_rule_info fri "
	+ " left join facil_tag_mapping ftm on (ftm.FACIL_RULE_ID =fri.id) "
	+ " where 1=1";
	public FacilRuleInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(FacilRuleInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO facil_rule_info (facil_rule_code,facil_rule_name, data_type, facility_id, critic_val, rule_desc, tip_desc, updated_dtm, created_dtm) " + 
				"VALUES(:facilRuleCode,:facilRuleName, :dataType, :facilityId, :criticVal, :ruleDesc, :tipDesc, now(), now())"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<FacilRuleInfo> load(long id) {
		FacilRuleInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from facil_rule_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toFacilRuleInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from facil_rule_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(FacilRuleInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE facil_rule_info SET " +
				"facil_rule_code    = :facilRuleCode,"+
				"facil_rule_name    = :facilRuleName,"+
				"data_type    = :dataType,"+
				"facility_id  = :facilityId,"+
				"critic_val  = :criticVal,"+
				"rule_desc  = :ruleDesc,"+
				"tip_desc  = :tipDesc,"+
				"updated_dtm  = now(),"+
				"created_dtm = now() "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<FacilRuleInfo>> loadAll() {
		List<FacilRuleInfo> list = jdbcTemplate.query(
				"select "+fields+" from facil_rule_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toFacilRuleInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<FacilRuleInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<FacilRuleInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from facil_rule_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<FacilRuleInfo>(FacilRuleInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<FacilRuleInfo>>> search(PageRequest req) {
		String queryStr = this.queryStr;
		return internalSearch(queryStr, req, FacilRuleInfo.class);
	}

}
