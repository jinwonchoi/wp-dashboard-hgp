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
import com.gencode.issuetool.obj.EventTimeLine;

@Component("EventTimeLineDao")
public class EventTimeLineDaoImpl extends AbstractDaoImpl implements EventTimeLineDao {

	final String fields= "id, iot_rule_code, iot_rule_name, data_type, interior_id, critic_val, rule_desc, tip_desc, updated_dtm, created_dtm";
	
	public EventTimeLineDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(EventTimeLine t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO event_time_line (iot_rule_code, iot_rule_name, data_type, interior_id, critic_val, rule_desc, tip_desc, updated_dtm, created_dtm) " + 
				"VALUES(:iotRuleCode,iotRuleName, dataType, :interiorId, :criticVal, :ruleDesc, :tipDesc, now(), now())"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<EventTimeLine> load(long id) {
		EventTimeLine t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from event_time_line where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toEventTimeLine(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from event_time_line " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(EventTimeLine t) {
		return namedParameterJdbcTemplate.update("UPDATE event_time_line SET " +
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
	public Optional<List<EventTimeLine>> loadAll() {
		List<EventTimeLine> list = jdbcTemplate.query(
				"select "+fields+" from event_time_line where 1=1", (resultSet, i) -> {
            return ObjMapper.toEventTimeLine(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<EventTimeLine>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<EventTimeLine> t = namedParameterJdbcTemplate.query
				("select "+fields+" from event_time_line where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<EventTimeLine>(EventTimeLine.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<EventTimeLine>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from event_time_line where 1=1";
		return internalSearch(queryStr, req, EventTimeLine.class);
	}

}
