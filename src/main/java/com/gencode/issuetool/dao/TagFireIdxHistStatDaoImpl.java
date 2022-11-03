package com.gencode.issuetool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
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
import com.gencode.issuetool.obj.TagFireIdxHistStat;

/**
 * 1. 5초단원 조회내역 일괄 입력
 * 2. 최근 1분, 10분, 1시간, 1일 데이터 조회(key: plantNo/plantPartCode/facilCode)
 * 1. 1분/10분/1시간/6시간/1일단위 평균값/최저값/최고값집계
 * @author jinno
 *
 */

@Component("TagFireIdxHistStatDao")
public class TagFireIdxHistStatDaoImpl extends AbstractDaoImpl implements TagFireIdxHistStatDao {

	final String fields= "created_dtm,time_mode,plant_no,plant_part_code,avg_fire_idx,min_fire_idx,max_fire_idx,alarm_cnt,critical_cnt";
	@Value("${chart-data-array-size:30}") int chartDataArraySize;

	
	public TagFireIdxHistStatDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(TagFireIdxHistStat t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO tag_fire_idx_hist_stat (created_dtm,time_mode,plant_no,plant_part_code,avg_fire_idx,min_fire_idx,max_fire_idx,alarm_cnt,critical_cnt) " + 
				"VALUES(:createdDtm,:timeMode,:interiorCode,:avgFireIdx,:minFireIdx,:maxFireIdx,:alarmCnt,:criticalCnt)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public int[] register(List<TagFireIdxHistStat> t) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public Optional<TagFireIdxHistStat> load(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException();
	}
	@Override
	public long update(TagFireIdxHistStat t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<TagFireIdxHistStat>> loadAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<TagFireIdxHistStat>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<TagFireIdxHistStat> t = namedParameterJdbcTemplate.query
				("select "+fields+" from tag_fire_idx_hist_stat where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<TagFireIdxHistStat>(TagFireIdxHistStat.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<TagFireIdxHistStat>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from tag_fire_idx_hist_stat where 1=1";
		return internalSearch(queryStr, req, TagFireIdxHistStat.class);
	}
	/**
	 * 최종건 삭제 후 재생성하기 위해 최종집계생성시간 리턴
	 * 최종건 없으면 삭제 스킵하도록 현재시간-1 리턴 
	 */
	public String getPreGenFromDtm(String timeMode) {
		String timeStr;
		String timeFrmt;
		if (timeMode.equals(Constant.DASHBOARD_STATS_TIME_MODE_1DAY.get())) {
			timeStr= "day";
			timeFrmt="00:00";
		}else if (timeMode.equals(Constant.DASHBOARD_STATS_TIME_MODE_1HOUR.get())) {
			timeStr= "hour";
			timeFrmt="%H:00";
		}else {//if (timeMode.equals("1M")) {
			timeStr= "minute";
			timeFrmt="%H:%i";
		}
		return jdbcTemplate.queryForObject("select ifnull(max(created_dtm), date_format(date_sub(now(), interval 1 "+timeStr+"), '%Y-%m-%d "+timeFrmt+"')) from tag_fire_idx_hist_stat where time_mode = '"+timeMode+"'", String.class);
			
	}
	
	int cleansePreviousDataGen(String createdDtm, String timeMode) {
		//String createdDtm = getPreGenFromDtm("1M");
		return namedParameterJdbcTemplate.update("delete from tag_fire_idx_hist_stat " + 
				"where created_dtm >= :createdDtm " + 
				"and time_mode = :timeMode",
				new MapSqlParameterSource() {{
					addValue("createdDtm",createdDtm);
					addValue("timeMode",timeMode);
				}});
	}

	@Override
	public String getCreatedDtmPreMinuteDataGen() {
		return getPreGenFromDtm(Constant.DASHBOARD_STATS_TIME_MODE_1MINUTE.get());
	}
	
	/** 4. 분단위 등록
		4.1. 분단위 등록시 기존등록 삭제
		hst테이블=>stat테이블
	 */
	@Override
	public int cleansePreMinuteDataGen(String createdDtm) {
		//String createdDtm = getPreGenFromDtm("1M");
		return cleansePreviousDataGen(createdDtm, Constant.DASHBOARD_STATS_TIME_MODE_1MINUTE.get());
	}
	/** 4. 분단위 등록
		4.2. 최종분이후 현재분 등록
		hst테이블=>stat테이블
	 */
	@Override
	public int generateMinuteData(String createdDtm) {
		return namedParameterJdbcTemplate.update("insert into tag_fire_idx_hist_stat\r\n" + 
				"select substr(created_dtm, 1,16) created_dtm , '1M', plant_no, plant_part_code,\r\n" + 
				"	avg(fire_idx) avg_fire_idx,\r\n" + 
				"	min(fire_idx) min_fire_idx,\r\n" + 
				"	max(max_fire_idx) max_fire_idx,\r\n" + 
				"	sum(case when (fire_idx > 4000) then 1 else 0 end) alarm_cnt,\r\n" + 
				"	sum(case when (fire_idx > 8000) then 1 else 0 end) critical_cnt\r\n" + 
				"from tag_fire_idx_hist\r\n" + 
				"where created_dtm >= :createdDtm \r\n" + 
				"group by substr(created_dtm, 1,16), plant_no, plant_part_code",
				new MapSqlParameterSource() {{
					addValue("createdDtm",createdDtm);
				}});
	}
	@Override
	public String getCreatedDtmPreHourlyDataGen() {
		return getPreGenFromDtm(Constant.DASHBOARD_STATS_TIME_MODE_1HOUR.get());
	}
	
	/**
	 * 5. 시간단위 등록
		5.1. 시간단위 등록시 기존등록 삭제
		stat테이블=>stat테이블
	 */
	@Override
	public int cleansePreHourlyDataGen(String createdDtm) {
		return cleansePreviousDataGen(createdDtm, Constant.DASHBOARD_STATS_TIME_MODE_1HOUR.get());
	}
	/**
	 * 5. 시간단위 등록
		5.2. 최종시간이후 현재시간 등록
		stat테이블=>stat테이블
	 */
	@Override
	public int generateHourlyData(String createdDtm) {
		return namedParameterJdbcTemplate.update("insert into tag_fire_idx_hist_stat\r\n" + 
				"select concat(substr(created_dtm, 1,13),':00') created_dtm , '1H', plant_no, plant_part_code,\r\n" + 
				"	avg(avg_fire_idx) avg_fire_idx,\r\n" + 
				"	min(min_fire_idx) min_fire_idx,\r\n" + 
				"	max(max_fire_idx) max_fire_idx,\r\n" + 
				"	sum(alarm_cnt) alarm_cnt,\r\n" + 
				"	sum(critical_cnt) critical_cnt\r\n" + 
				"from tag_fire_idx_hist_stat\r\n" + 
				"where created_dtm >= :createdDtm \r\n" + 
				"and time_mode='1M'\r\n" + 
				"group by concat(substr(created_dtm, 1,13),':00'), plant_no, plant_part_code",
				new MapSqlParameterSource() {{
					addValue("createdDtm",createdDtm);
				}});
	}
	@Override
	public String getCreatedDtmPreDailyDataGen() {
		return getPreGenFromDtm(Constant.DASHBOARD_STATS_TIME_MODE_1DAY.get());
	}
	/**
	 * 6. 일단위 등록
		6.1. 일단위 등록시 기존등록 삭제
	 */
	@Override
	public int cleansePreDailyDataGen(String createdDtm) {
		return cleansePreviousDataGen(createdDtm, Constant.DASHBOARD_STATS_TIME_MODE_1DAY.get());
	}
	/**
	 * 6. 일단위 등록
		6.2.최종일자 이후 오늘자 등록
		stat테이블=>stat테이블
	 */
	@Override
	public int generateDailyData(String createdDtm) {
		return namedParameterJdbcTemplate.update("insert into tag_fire_idx_hist_stat\r\n" + 
				"select concat(substr(created_dtm, 1,10),' 00:00') created_dtm , '1D', plant_no, plant_part_code,\r\n" + 
				"	avg(avg_fire_idx) avg_fire_idx,\r\n" + 
				"	min(min_fire_idx) min_fire_idx,\r\n" + 
				"	max(max_fire_idx) max_fire_idx,\r\n" + 
				"	sum(alarm_cnt) alarm_cnt,\r\n" + 
				"	sum(critical_cnt) critical_cnt\r\n" + 
				"from tag_fire_idx_hist_stat\r\n" + 
				"where created_dtm >= :createdDtm\r\n" + 
				"and time_mode = '1H'\r\n" + 
				"group by concat(substr(created_dtm, 1,10),' 00:00'), plant_no, plant_part_code",
				new MapSqlParameterSource() {{
					addValue("createdDtm",createdDtm);
				}});
	}
	/**
	 * 7. hst테이블 클랜징
	1시간전 데이터 삭제
	stat최종이 1시간전데이터이면 삭제안함
	==> TagFireIdxHistDao에서 처리
	*/
	/**
	 * 8. stat테이블 클랜징
		8.1. 분단위 => 1일전 데이터 삭제
		  - 시간단위가 1일전이면 삭제안함
	 */
	@Override
	public int cleanseMinuteData() {
		return jdbcTemplate.update("delete from tag_fire_idx_hist_stat \r\n" + 
				"where created_dtm < date_format(date_sub(now(), interval 1 day), '%Y-%m-%d %H:%i')\r\n" + 
				//"and created_dtm < (select ifnull(max(created_dtm), date_format(date_sub(now(), interval 1 day), '%Y-%m-%d %H:%i:00.000')) from tag_fire_idx_hist_stat where time_mode = '1M');\r\n" + 
				"and time_mode='1M'");
	}
	/**
	 * 8. stat테이블 클랜징
		8.2. 시간단위=> 30일전 데이터 삭제
	 */
	@Override
	public int cleanseHourlyData() {
		return jdbcTemplate.update("delete from tag_fire_idx_hist_stat \r\n" + 
				"where created_dtm < date_format(date_sub(now(), interval 30 day), '%Y-%m-%d %H:00')\r\n" + 
				//"and created_dtm < (select ifnull(max(created_dtm), date_format(date_sub(now(), interval 30 day), '%Y-%m-%d %H:00:00.000')) from tag_fire_idx_hist_stat where time_mode = '1H');\r\n" + 
				"and time_mode='1H'");
	}
	
	/**
	 * 최근1시간 timeMode=1H
	 * 최근1일 timeMode=1D
	 * 최근1달 timeMode=1m	 
	 * 단위: 1시간, 6시간, 1일, 1주, 1달
	 * 
	 */
	@Override
	public Optional<List<TagFireIdxHistStat>> getStatsCnt(PageRequest req) {
		String strTime;
		String strDateFrmt;
		String timeMode;
		if (req.getParamMap().get("timeMode").equals("1H")) {
			strTime = "1 hour";
			strDateFrmt = "%H:%i";
			timeMode="1M";
		} else if (req.getParamMap().get("timeMode").equals("1H")) {
			strTime = "6 hour";
			strDateFrmt = "%H:00";
			timeMode="1H";
		} else if (req.getParamMap().get("timeMode").equals("1D")) {
			strTime = "1 day";
			strDateFrmt = "%H:00";
			timeMode="1H";
		} else if (req.getParamMap().get("timeMode").equals("1m")) {
			strTime = "1 month";
			strDateFrmt = "%H:00";
			timeMode="1D";
		} else  {//if (mode.equals("M")) {
				strTime = "1 minute";
				strDateFrmt = "%H:%i";
				timeMode="1M";
		}
		
		SearchMapObj searchMapObj = new SearchMapObj(req.getSearchMap(), false);
		List<TagFireIdxHistStat> t = namedParameterJdbcTemplate.query
				("select plant_no,plant_part_code, " + 
						"avg(avg_fire_idx) avg_fire_idx," + 
						"	min(min_fire_idx) min_fire_idx," + 
						"	max(max_fire_idx) max_fire_idx," + 
						"	sum(alarm_cnt) alarm_cnt," + 
						"	sum(critical_cnt) critical_cnt " + 
						" from tag_fire_idx_hist_stat " +
						" where created_dtm >= date_format(DATE_SUB(NOW(), INTERVAL "+strTime+"),'%Y-%m-%d "+strDateFrmt+"') and time_mode='"+timeMode+"' " + 
						searchMapObj.andQuery() + 
						" group by plant_no,plant_part_code " + 
						" order by plant_no,plant_part_code"
						, new MapSqlParameterSource(req.getParamMap())
						, new BeanPropertyRowMapper<TagFireIdxHistStat>(TagFireIdxHistStat.class));
		return Optional.of(t);
	}

	@Override
	public Optional<List<TagFireIdxHistStat>> getRealtimeChartData(PageRequest req) {
		// TODO Auto-generated method stub
		return null;
	}
}
