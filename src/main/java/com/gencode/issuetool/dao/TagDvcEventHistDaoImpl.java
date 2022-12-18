/**=========================================================================================
<overview>태그/센서 이벤트발생이력관련 DAO 처리구현
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
import com.gencode.issuetool.obj.PlantPartInfo;
import com.gencode.issuetool.obj.TagDvcEventHist;

/**
 * 1. 5초단원 조회내역 일괄 입력
 * 2. 최근 1분, 10분, 1시간, 1일 데이터 조회(key: plantNo/plantPartCode/facilCode)
 * 1. 1분/10분/1시간/6시간/1일단위 평균값/최저값/최고값집계
 * @author jinno
 *
 */

@Component("TagDvcEventHistDao")
public class TagDvcEventHistDaoImpl extends AbstractDaoImpl implements TagDvcEventHistDao {

	final String fields= "created_dtm,device_type,device_id,val_type,event_level,event_val,interior_code,plant_no,plant_part_code,facil_code";
	
	
	public TagDvcEventHistDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(TagDvcEventHist t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO tag_dvc_event_hist (created_dtm,device_type,device_id,val_type,event_level,event_val,interior_code,plant_no,plant_part_code,facil_code) " + 
				"VALUES(:createdDtm,:deviceType,:deviceId,:valType,:eventLevel,:eventVal,:interiorCode,:plantNo,:plantPartCode,:facilCode)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public int[] register(List<TagDvcEventHist> t) {
		return jdbcTemplate.batchUpdate("INSERT INTO tag_dvc_event_hist (created_dtm,device_type,device_id,val_type,event_level,event_val,interior_code,plant_no,plant_part_code,facil_code)  VALUES (?,?,?,?,?,?,?,?,?,?)", 
			new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, t.get(i).getCreatedDtm());
					ps.setString(2, t.get(i).getDeviceType());
					ps.setString(3, t.get(i).getDeviceId());
					ps.setString(4, t.get(i).getValType());
					ps.setString(5, t.get(i).getEventLevel());
					ps.setString(6, t.get(i).getEventVal());
					ps.setString(7, t.get(i).getInteriorCode());
					ps.setString(8, t.get(i).getPlantNo());
					ps.setString(9, t.get(i).getPlantPartCode());
					ps.setString(10, t.get(i).getFacilCode());
				}

				@Override
				public int getBatchSize() {
					return t.size();
				}
		});
		
	}

	@Override
	public Optional<TagDvcEventHist> load(long id) {		
		throw new UnsupportedOperationException();
	}

	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public long update(TagDvcEventHist t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<TagDvcEventHist>> loadAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<TagDvcEventHist>> getRealtimeChartData(PageRequest req) {
		SearchMapObj searchMapObj = new SearchMapObj(req.getSearchMap(), false);
		List<TagDvcEventHist> t = namedParameterJdbcTemplate.query
				("select "+fields+" from tag_dvc_event_hist b, (select distinct created_dtm idx_dtm from tag_dvc_event_hist 	where  created_dtm >= DATE_SUB(NOW(), INTERVAL 10 minute) order by created_dtm desc limit "+req.getParamMap().get("realtimeCount")+" ) a" +
						"	where created_dtm = a.idx_dtm " + 
						searchMapObj.andQuery() +
						" order by device_type,device_id,val_type,created_dtm "
						, new MapSqlParameterSource(req.getParamMap())
						, new BeanPropertyRowMapper<TagDvcEventHist>(TagDvcEventHist.class));
		return Optional.of(t);
	}

	@Override
	public Optional<List<TagDvcEventHist>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<TagDvcEventHist> t = namedParameterJdbcTemplate.query
				("select "+fields+" from tag_dvc_event_hist where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<TagDvcEventHist>(TagDvcEventHist.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<TagDvcEventHist>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from tag_dvc_event_hist where 1=1";
		return internalSearch(queryStr, req, TagDvcEventHist.class);
	}

	/**
	 * 7. hst테이블 클랜징
		1시간전 데이터 삭제
		stat최종이 1시간전데이터이면 삭제안함
	 */
	@Override
	public int cleanseData() {
		return jdbcTemplate.update("delete from tag_dvc_event_hist \r\n" + 
				"where created_dtm < date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i:00.000')\r\n" + 
				"and date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i') < (select ifnull(max(created_dtm), date_format(date_sub(now(), interval 1 hour), '%Y-%m-%d %H:%i')) from tag_dvc_event_hist_stat where time_mode = '1M')");
	}
	
}
