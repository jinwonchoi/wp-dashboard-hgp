/**=========================================================================================
<overview>태그기준정보관련 DAO 처리구현
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
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.FacilTagInfo;

@Component("FacilTagInfoDao")
public class FacilTagInfoDaoImpl extends AbstractDaoImpl implements FacilTagInfoDao {

	final String fields= "id,tag_name,tag_desc,plant_type,facility_id,alrm_type,alrm_val1,alrm_val2,trip_type,trip_val1,trip_val2,plant_no,plant_part_code,facil_code,scr_seq,val_type,func_cd,start_register,address,data_len,tag_desc2, tag_print_name, redundancy, updated_dtm,created_dtm";
	
	public FacilTagInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(FacilTagInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO facil_tag_info (tag_name,tag_desc,plant_type,facility_id,alrm_type,alrm_val1,alrm_val2,trip_type,trip_val1,trip_val2,plant_no,plant_part_code,facil_code,scr_seq,val_type,func_cd,start_register,end_register,data_len,tag_desc2, tag_print_name, redundancy,updated_dtm,created_dtm) " + 
				"VALUES(:tagName,:tagDesc,:plantType,:facilityId,:alrmType,:alrmVal1,:alrmVal2,:tripType,:tripVal1,:tripVal2,:plantNo,:plantPartCode,:facilCode,:scrSeq,:valType,:funcCd,:startRegister,:address,:dataLen,:tagDesc2, :tagPrintName, :redundancy, now(), now())"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}
	
	@Override
	public Optional<FacilTagInfo> load(long id) {
		FacilTagInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from facil_tag_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toFacilTagInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("delete from facil_tag_info " + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(FacilTagInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE facil_tag_info SET " +
				"tag_name       =:tagName,"+
				"tag_desc       =:tagDesc,"+
				"plant_type     =:plantType,"+
				"facility_id    =:facilityId,"+
				"alrm_type      =:alrmType,"+
				"alrm_val1      =:alrmVal1,"+
				"alrm_val2      =:alrmVal2,"+
				"trip_type      =:tripType,"+
				"trip_val1      =:tripVal1,"+
				"trip_val2      =:tripVal2,"+
				"plant_no       =:plantNo,"+
				"plant_part_code=:plantPartCode,"+
				"facil_code     =:facilCode,"+
				"scr_seq        =:scrSeq,"+
				"val_type       =:valType,"+
				"func_cd        =:funcCd,"+
				"start_register =:startRegister,"+
				"address   =:address,"+
				"data_len       =:dataLen,"+
				"tag_desc2       =:tagDesc2,"+
				"tag_print_name  =:tagPrintName,"+
				"redundancy       =:redundancy,"+
				"updated_dtm    =now(3)"+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<FacilTagInfo>> loadAll() {
		List<FacilTagInfo> list = jdbcTemplate.query(
				"select "+fields+" from facil_tag_info where 1=1", (resultSet, i) -> {
            return ObjMapper.toFacilTagInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<FacilTagInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<FacilTagInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from facil_tag_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<FacilTagInfo>(FacilTagInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<FacilTagInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from facil_tag_info where 1=1";
		return internalSearch(queryStr, req, FacilTagInfo.class);
	}

}
