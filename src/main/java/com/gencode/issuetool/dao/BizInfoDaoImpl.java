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

import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.BizInfo;

@Component
public class BizInfoDaoImpl extends AbstractDaoImpl implements BizInfoDao {

	public BizInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(BizInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO biz_info (name,country,lang,location)\r\n" + 
				"VALUES(:name,:country,:lang,:location)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<BizInfo> load(long id) {
		BizInfo bizInfo = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query("select * from biz_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toBizInfo(resultSet);
				}));
		return Optional.of(bizInfo);
	}

	@Override
	public void delete(long id) {
//		namedParameterJdbcTemplate.update("DELETE FROM biz_info where id = :id",
//				new MapSqlParameterSource("id", id));
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(BizInfo t) {
		namedParameterJdbcTemplate.update("UPDATE biz_info set name = :name,country = :country,lang = :lang,location = :location WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<BizInfo>> loadAll(String langFlag) {
		StringBuffer sb = new StringBuffer();
		if (Constant.LANG_EN.get().equals(langFlag)) {
			sb.append("select id,name,country,lang,location from biz_info where lang='EN'");
		} else {
			sb.append("select id,name,country,lang,location from biz_info where lang='KO'");
		}
		
		List<BizInfo> list = jdbcTemplate.query(sb.toString(), (resultSet, i) -> {
            return toBizInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<BizInfo>> loadAll() {
		return loadAll("");
	}

	@Override
	public Optional<List<BizInfo>> search(String langFlag, Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		if (Constant.LANG_EN.get().equals(langFlag)) {
			sb.append("select id,name,country,lang,location from biz_info where lang='EN' ");
		} else {
			sb.append("select id,name,country,lang,location from biz_info where lang='KO' ");
		}		
		
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<BizInfo> bizInfos = namedParameterJdbcTemplate.query
				(sb.toString()
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<BizInfo>(BizInfo.class));
		return Optional.of(bizInfos);
	}

	@Override
	public Optional<List<BizInfo>> search(Map<String, String> map) {
		return search("", map);
	}
	@Override
	public Optional<PageResultObj<List<BizInfo>>> search(PageRequest req) {
		String queryStr = "select id,name,country,lang,location from biz_info where 1 = 1";
		return internalSearch(queryStr, req, BizInfo.class);
	}

	private BizInfo toBizInfo(ResultSet resultSet) throws SQLException {
		BizInfo bizInfo = new BizInfo();
		bizInfo.setId(resultSet.getString("ID"));
		bizInfo.setName(resultSet.getString("NAME"));
		bizInfo.setCountry(resultSet.getString("COUNTRY"));
		bizInfo.setLang(resultSet.getString("LANG"));
		bizInfo.setLocation(resultSet.getString("LOCATION"));
		return bizInfo;
	}
	 
	 
}
