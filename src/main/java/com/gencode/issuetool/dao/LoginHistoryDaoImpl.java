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

import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.LoginHistory;

@Component
public class LoginHistoryDaoImpl extends AbstractDaoImpl implements LoginHistoryDao {

	public LoginHistoryDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(LoginHistory t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update("INSERT INTO login_history(user_id,login_dtm,logout_dtm,logout_type)\r\n" + 
				"VALUES(:userId,NOW(3),null,null )"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<LoginHistory> load(long id) {
		LoginHistory t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select id,user_id,login_dtm,logout_dtm,logout_type from login_history where id=:id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toLoginHistory(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException();
		/*
		namedParameterJdbcTemplate.update("DELETE FROM biz_info where id = :id",
				new MapSqlParameterSource("id", id));
	 
		 */
	}

	@Override
	public long update(LoginHistory t) {
		return namedParameterJdbcTemplate.update("UPDATE login_history SET " + 
				"logout_dtm = NOW(3), " +
				"logout_type=:logoutType " +
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<LoginHistory>> loadAll() {
		List<LoginHistory> list = jdbcTemplate.query("select id,user_id,login_dtm,logout_dtm,logout_type from login_history where 1=1", (resultSet, i) -> {
            return toLoginHistory(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<LoginHistory>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<LoginHistory> t = namedParameterJdbcTemplate.query
				("select id, user_id,login_dtm,logout_dtm,logout_type from login_history where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<LoginHistory>(LoginHistory.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<LoginHistory>>> search(PageRequest req) {
		String queryStr = "select id, user_id,login_dtm,logout_dtm,logout_type from login_history where 1=1";
		return internalSearch(queryStr, req, LoginHistory.class);
	}

	private LoginHistory toLoginHistory(ResultSet resultSet) throws SQLException {
		LoginHistory obj = new LoginHistory();
		obj.setId(resultSet.getLong("ID"));
		obj.setUserId(resultSet.getLong("USER_ID"));
		obj.setLoginDtm(Utils.DtToStr(resultSet.getTimestamp("LOGIN_DTM")));
		obj.setLogoutDtm(Utils.DtToStr(resultSet.getTimestamp("LOGOUT_DTM")));
		obj.setLogoutType(resultSet.getString("LOGOUT_TYPE"));
		return obj;
	}
	 
}
