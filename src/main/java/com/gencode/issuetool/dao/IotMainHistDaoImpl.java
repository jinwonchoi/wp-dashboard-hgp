/**=========================================================================================
<overview>센서상태값처리 DAO 처리구현
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

import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.ObjMapper;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.IotMain;

/**
 * 1. 5초단원 조회내역 일괄 입력
 * 2. 최근 1분, 10분, 1시간, 1일 데이터 조회(key: plantNo/plantPartCode/facilCode)
 * 1. 1분/10분/1시간/6시간/1일단위 평균값/최저값/최고값집계
 * @author jinno
 *
 */

@Component("IotMainHistDao")
public class IotMainHistDaoImpl extends AbstractDaoImpl implements IotMainHistDao {

	final String fields= "created_dtm,interior_code,avg_humid_val,avg_smoke_val,avg_temp_val,avg_co_val,avg_flame,max_humid_val,max_smoke_val,max_temp_val,max_co_val,max_flame";
	
	
	public IotMainHistDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(IotMain t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO iot_main_hist (created_dtm,interior_code,avg_humid_val,avg_smoke_val,avg_temp_val,avg_co_val,avg_flame,max_humid_val,max_smoke_val,max_temp_val,max_co_val,max_flame) " + 
				"VALUES(:createdDtm,:interiorCode,:avgHumidVal,:avgSmokeVal,:avgTempVal,:avgCoVal,:avgFlame,:maxHumidVal,:maxSmokeVal,:maxTempVal,:maxCoVal,:maxFlame)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public int[] register(List<IotMain> t) {
		return jdbcTemplate.batchUpdate("INSERT INTO iot_main_hist (created_dtm,interior_code,avg_humid_val,avg_smoke_val,avg_temp_val,avg_co_val,avg_flame,max_humid_val,max_smoke_val,max_temp_val,max_co_val,max_flame)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", 
			new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, t.get(i).getCreatedDtm());
					ps.setString(2, t.get(i).getInteriorCode());
					ps.setLong(3, t.get(i).getAvgHumidVal());
					ps.setLong(4, t.get(i).getAvgSmokeVal());
					ps.setLong(5, t.get(i).getAvgTempVal());
					ps.setLong(6, t.get(i).getAvgCoVal());
					ps.setLong(7, t.get(i).getAvgFlame());
					ps.setLong(8, t.get(i).getMaxHumidVal());
					ps.setLong(9, t.get(i).getMaxSmokeVal());
					ps.setLong(10, t.get(i).getMaxTempVal());
					ps.setLong(11, t.get(i).getMaxCoVal());
					ps.setLong(12, t.get(i).getMaxFlame());
				}

				@Override
				public int getBatchSize() {
					return t.size();
				}
		});
		
	}

	@Override
	public Optional<IotMain> load(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long update(IotMain t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<IotMain>> loadAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<IotMain>> getRealtimeChartData(PageRequest req) {
		SearchMapObj searchMapObj = new SearchMapObj(req.getSearchMap(), false);
		String fieldsStr;
		
		String valType = Constant.IOT_REALTIME_CHART_VAL_LEVEL_MAX.get().equals(req.getParamMap().get("valType"))?Constant.IOT_REALTIME_CHART_VAL_LEVEL_MAX.get():Constant.IOT_REALTIME_CHART_VAL_LEVEL_AVG.get();
				
		if ((Constant.IOT_REALTIME_CHART_FIELD_HUMID.get().equals(req.getParamMap().get("fieldType")))) {
			fieldsStr = "created_dtm,interior_code,"+valType+"_humid_val avg_temp_val";
		} else if ((Constant.IOT_REALTIME_CHART_FIELD_SMOKE.get().equals(req.getParamMap().get("fieldType")))) {
			fieldsStr = "created_dtm,interior_code,"+valType+"_smoke_val avg_temp_val";
		} else if ((Constant.IOT_REALTIME_CHART_FIELD_CO.get().equals(req.getParamMap().get("fieldType")))) {
			fieldsStr = "created_dtm,interior_code,"+valType+"_co_val avg_temp_val";
		} else if ((Constant.IOT_REALTIME_CHART_FIELD_FLAME.get().equals(req.getParamMap().get("fieldType")))) {
			fieldsStr = "created_dtm,interior_code,"+valType+"_flame avg_temp_val";
		} else {//if ((Constant.IOT_REALTIME_CHART_FIELD_TEMP.get().equals(req.getParamMap().get("fieldType")))) {
			fieldsStr = "created_dtm,interior_code,"+valType+"_temp_val avg_temp_val";
		}
		
		List<IotMain> t = namedParameterJdbcTemplate.query
				("select "+fieldsStr+" from iot_main_hist b, (select distinct created_dtm idx_dtm from iot_main_hist 	where  created_dtm >= DATE_SUB(NOW(), INTERVAL 10 minute) order by created_dtm desc limit "+req.getParamMap().get("realtimeCount")+" ) a" +
						"	where created_dtm = a.idx_dtm " + 
						searchMapObj.andQuery() +
						" order by interior_code,created_dtm "
						, searchMapObj.params()
						, new BeanPropertyRowMapper<IotMain>(IotMain.class));
		return Optional.of(t);
	}
	
	@Override
	public Optional<List<IotMain>> getRealtimeChartDataByInteriorList(PageRequest req) {
		SearchMapObj searchMapObj = new SearchMapObj(req.getSearchMap(), false);
		String fieldsStr;
		boolean useArea=Constant.IOT_REALTIME_CHART_KEY_MODE_AREA.get().equals(req.getParamMap().get("mode"));
		String valType = Constant.IOT_REALTIME_CHART_VAL_LEVEL_MAX.get().equals(req.getParamMap().get("valType"))?Constant.IOT_REALTIME_CHART_VAL_LEVEL_MAX.get():Constant.IOT_REALTIME_CHART_VAL_LEVEL_AVG.get();
		fieldsStr = "created_dtm,"+(useArea?"ai.area_code interior_code":"b.interior_code")+","+valType+"_humid_val avg_humid_val,"+valType+"_smoke_val avg_smoke_val,"+valType+"_co_val avg_co_val,"+valType+"_flame avg_flame_val,"+valType+"_temp_val avg_temp_val";
		String sqlStr = 
				"select "+fieldsStr+" from iot_main_hist b, area_info ai, interior_info ii, (select distinct created_dtm idx_dtm from iot_main_hist where  created_dtm >= DATE_SUB(NOW(), INTERVAL 10 minute) order by created_dtm desc limit "+req.getParamMap().get("realtimeCount")+" ) a" +
				"	where created_dtm = a.idx_dtm and ai.id=ii.area_id and ii.interior_code=b.interior_code "+(useArea?"":"and ai.area_code=:areaCode") + 
				" order by "+(useArea?"ai.area_code":"b.interior_code")+",created_dtm ";

		List<IotMain> t = namedParameterJdbcTemplate.query
				(sqlStr
						, searchMapObj.params()
						, new BeanPropertyRowMapper<IotMain>(IotMain.class));
		return Optional.of(t);
	}
	
	

	@Override
	public Optional<List<IotMain>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<IotMain> t = namedParameterJdbcTemplate.query
				("select "+fields+" from iot_main_hist where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<IotMain>(IotMain.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<IotMain>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from iot_main_hist where 1=1";
		return internalSearch(queryStr, req, IotMain.class);
	}

	/**
	 * 7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	@Override
	public int cleanseData() {
		return jdbcTemplate.update("delete from iot_main_hist \r\n" + 
				"where created_dtm < date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i:00.000')\r\n" + 
				"and date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i') < (select ifnull(max(created_dtm), date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i')) from iot_main_hist_stat where time_mode = '1M')");
	}
	
}
