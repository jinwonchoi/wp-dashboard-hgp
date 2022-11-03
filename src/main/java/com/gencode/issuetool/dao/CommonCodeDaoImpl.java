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
import org.springframework.stereotype.Component;

import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.CommonCode;

@Component
public class CommonCodeDaoImpl extends AbstractDaoImpl implements CommonCodeDao {

	public CommonCodeDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(CommonCode t) {
			namedParameterJdbcTemplate.update("INSERT INTO common_code(group_id,item_key,item_value,description)\r\n" + 
					"VALUES(:groupId,:itemKey,:itemValue,:description )"
					,new BeanPropertySqlParameterSource(t));
			return -1;
	}

	@Override
	public Optional<CommonCode> load(long id) {
		throw new UnsupportedOperationException();
	}

	public Optional<CommonCode> load(String id) {
		CommonCode t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query("select group_id,item_key,item_value,description from common_code where group_id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toCommonCode(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException();
	}

	public long delete(String id) {
		return namedParameterJdbcTemplate.update("DELETE FROM common_code where group_id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public long update(CommonCode t) {
		return namedParameterJdbcTemplate.update("UPDATE common_code SET " + 
				"group_id        =:groupId,"+
				"item_key    =:itemKey,"+
				"item_value       =:itemValue,"+
				"description         =:description "+
				"WHERE group_id = :id"
				,new BeanPropertySqlParameterSource(t));		 
	}

	@Override
	public Optional<List<CommonCode>> loadAll(String langFlag) {
		StringBuffer sb = new StringBuffer();
		sb.append("select group_id,item_key,item_value,description from common_code");
		if (Constant.LANG_EN.get().equals(langFlag)) {
			sb.append("_en");
		} else { //if (Constant.LANG_KO.get().equals(langFlag)) {
			//
		}
		List<CommonCode> list = jdbcTemplate.query(sb.toString(), (resultSet, i) -> {
            return toCommonCode(resultSet);
        });
		return Optional.of(list);
	}
	
	@Override
	public Optional<List<CommonCode>> loadAll() {
		return loadAll("");
	}

	@Override
	public Optional<List<CommonCode>> search(String langFlag, Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("select group_id,item_key,item_value,description from common_code");
		if (Constant.LANG_EN.get().equals(langFlag)) {
			sb.append("_en");
		} else { //if (Constant.LANG_KO.get().equals(langFlag)) {
			//
		}
		sb.append(" where 1=1");
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<CommonCode> t = namedParameterJdbcTemplate.query
				(sb.toString()
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<CommonCode>(CommonCode.class));
		return Optional.of(t);
	}
	
	@Override
	public Optional<List<CommonCode>> search(Map<String, String> map) {
		return search("", map);
	}
	
	@Override
	public Optional<PageResultObj<List<CommonCode>>> search(String langFlag, PageRequest req) {
		StringBuffer sb = new StringBuffer();
		sb.append("select group_id,item_key,item_value,description from common_code");
		if (Constant.LANG_EN.get().equals(langFlag)) {
			sb.append("_en");
		} else { //if (Constant.LANG_KO.get().equals(langFlag)) {
			//
		}
		sb.append(" where 1=1");
		//String queryStr = "select group_id,item_key,item_value,description from common_code where 1=1";
		return internalSearch(sb.toString(), req, CommonCode.class);
	}
	@Override
	public Optional<PageResultObj<List<CommonCode>>> search(PageRequest req) {
		return search("", req);
	}
	
	private CommonCode toCommonCode(ResultSet resultSet) throws SQLException {
		CommonCode obj = new CommonCode();
		obj.setGroupId(resultSet.getString("GROUP_ID"));
		obj.setItemKey(resultSet.getString("ITEM_KEY"));
		obj.setItemValue(resultSet.getString("ITEM_VALUE"));
		obj.setDescription(resultSet.getString("DESCRIPTION"));
		return obj;
	}

}
