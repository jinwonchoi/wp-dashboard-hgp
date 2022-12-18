/**=========================================================================================
<overview>센서장비화재지수관련 DAO 처리구현
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
import com.gencode.issuetool.obj.IotFireIdx;

@Component("IotFireIdxHistDao")
public class IotFireIdxHistDaoImpl extends AbstractDaoImpl implements IotFireIdxHistDao {

	final String fields= "created_dtm,interior_code,fire_idx,max_fire_idx";
	
	
	public IotFireIdxHistDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(IotFireIdx t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO iot_fire_idx_hist (created_dtm,interior_code,fire_idx,max_fire_idx) " + 
				"VALUES(:createdDtm,:interiorCode,:fireIdx,:maxFireIdx)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public void register(List<IotFireIdx> t) {
		jdbcTemplate.batchUpdate("INSERT INTO iot_fire_idx_hist (created_dtm,interior_code,fire_idx,max_fire_idx)  VALUES (?,?,?,?)", 
			new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, t.get(i).getCreatedDtm());
					ps.setString(2, t.get(i).getInteriorCode());
					ps.setLong(3, t.get(i).getFireIdx());
					ps.setLong(4, t.get(i).getMaxFireIdx());
				}

				@Override
				public int getBatchSize() {
					return t.size();
				}
		});		
	}

	@Override
	public Optional<IotFireIdx> load(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteOld() {
		jdbcTemplate.update("delete from iot_fire_idx_hist where created_dtm < DATE_SUB(NOW(), INTERVAL 1 HOUR)");		
	}
	
	@Override
	public long update(IotFireIdx t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<IotFireIdx>> loadAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<IotFireIdx>> getRealtimeChartData(PageRequest req) {
		SearchMapObj searchMapObj = new SearchMapObj(req.getSearchMap(), false);
		List<IotFireIdx> t = namedParameterJdbcTemplate.query
				("select substr(created_dtm, 1,19) created_dtm , interior_code, round(fire_idx,0) fire_idx, 0 max_fire_idx from iot_fire_idx_hist b," + 
						"(select distinct created_dtm idx_dtm from iot_fire_idx_hist 	where  created_dtm >= DATE_SUB(NOW(), INTERVAL 10 minute) order by created_dtm desc limit "+req.getParamMap().get("realtimeCount")+" ) a" + 
						"	where created_dtm = a.idx_dtm " + 
						searchMapObj.andQuery() +
						" group by substr(created_dtm, 1,19) , interior_code" +
						" order by interior_code, substr(created_dtm, 1,19) "
						, new MapSqlParameterSource(req.getParamMap())
						, new BeanPropertyRowMapper<IotFireIdx>(IotFireIdx.class));
		return Optional.of(t);
	}
	
	@Deprecated
	@Override
	public Optional<List<IotFireIdx>> getCountOverStableByMonth() {
		List<IotFireIdx> t = jdbcTemplate.query
				("select '' created_dtm, a.interior_code, ifnull(b.fire_idx,0) fire_idx, 0 max_fire_idx  from (select distinct interior_code from interior_info fti  union select 'AVERAGE') a\r\n" + 
						"left outer join (select interior_code, count(*) fire_idx from iot_fire_idx_hist tfih\r\n" + 
						"where created_dtm > DATE_SUB(NOW(), INTERVAL 1 month)\r\n" + 
						"and fire_idx > 480\r\n" + 
						"group by interior_code) b  on (a.interior_code=b.interior_code)\r\n" + 
						"order by interior_code "
						, new BeanPropertyRowMapper<IotFireIdx>(IotFireIdx.class));
		return Optional.of(t);
	}
	
	@Override
	public Optional<List<IotFireIdx>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<IotFireIdx> t = namedParameterJdbcTemplate.query
				("select "+fields+" from iot_fire_idx_hist where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<IotFireIdx>(IotFireIdx.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<IotFireIdx>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from iot_fire_idx_hist where 1=1";
		return internalSearch(queryStr, req, IotFireIdx.class);
	}
	/**
	 * 7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	@Override
	public int cleanseData() {
		return jdbcTemplate.update("delete from iot_fire_idx_hist \r\n" + 
				"where created_dtm < date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i:00.000')\r\n" + 
				"and date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i') < (select ifnull(max(created_dtm), date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i')) from iot_fire_idx_hist_stat where time_mode = '1M')");
	}

}
