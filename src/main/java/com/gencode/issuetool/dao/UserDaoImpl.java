package com.gencode.issuetool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.User;

@Component("UserDao")
public class UserDaoImpl extends AbstractDaoImpl implements UserDao {

	UserDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}
	
	@Override
	public long register(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO users (userid,password,username,email,role,description,email_auth_key, create_date,update_date)" + 
				"VALUES (:userid, :password, :username, :email, :role, :description, :emailAuthKey, NOW(3), NOW(3))"
				,new BeanPropertySqlParameterSource(user), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<User> load(long id) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public Optional<User> login(String userid) {
		User user = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query("select * from users where delete_yn = 'N' and email_auth_yn = 'Y' and userid = :userid",
				new MapSqlParameterSource("userid",userid), (resultSet,i)->{
					return toUser(resultSet);
				}));
		return  Optional.of(user);
	}

	
	@Override
	public Optional<User> load(String userid) {
		User user = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query("select * from users where delete_yn = 'N' and userid = :userid",
				new MapSqlParameterSource("userid",userid), (resultSet,i)->{
					return toUser(resultSet);
				}));
		return  Optional.of(user);
	}
	
	@Override
	public void delete(long id) {
		//namedParameterJdbcTemplate.update("DELETE FROM biz_info where id = :id",
		throw new UnsupportedOperationException();
	}	
	
	@Override
	public void delete(String userid) {
		namedParameterJdbcTemplate.update("update user_info set delete_yn = 'Y' where userid = :userid",
				new MapSqlParameterSource("userid", userid));
	}

	@Override
	public void activate(User user) {
		namedParameterJdbcTemplate.update("UPDATE user_info SET email_auth_yn = :emailAuthYn" + 
				" WHERE userid = :userid and email_auth_key = :emailAuthKey"
				,new BeanPropertySqlParameterSource(user));
	}
	
	@Override
	public void update(User user) {
		String queryStr = null;
		if (user.getPassword()!=null&&!user.getPassword().trim().equals("")) {
			queryStr = "UPDATE user_info SET userid = :userid,password = :password,username = :username,email = :email,role = :role" + 
					",description = :description,update_date = NOW(3) WHERE userid = :userid";
		} else {
			queryStr = "UPDATE user_info SET userid = :userid,username = :username,email = :email,role = :role" + 
					",description = :description,update_date = NOW(3) WHERE userid = :userid";
		}
		namedParameterJdbcTemplate.update(queryStr,new BeanPropertySqlParameterSource(user));
	}
	
	@Override
	public Optional<List<User>> loadAll() {
		List<User> users = namedParameterJdbcTemplate.query
				//("select * from user_info where delete_yn='N'"
						("select userid,password,username,email,role,description,create_date,update_date from user_info where delete_yn='N'"
						, new BeanPropertyRowMapper<User>(User.class));
		logger.debug("users.size():"+users.size());
		return Optional.of(users);		
	}

	@Override
	public Optional<List<User>> search(Map<String, String> map) {
		
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<User> users = namedParameterJdbcTemplate.query
				//("select * from user_info where delete_yn='N'"
						("select userid,password,username,email,role,description,create_date,update_date from user_info where delete_yn='N'"
				+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<User>(User.class));
		logger.info("  "+users.size());
		return Optional.of(users);
	}

	@Override
	public Optional<PageResultObj<List<User>>> search(PageRequest req) {
		
		String queryStr ="select userid,password,username,email,role,description,create_date,update_date from user_info where delete_yn='N'";
		return internalSearch(queryStr, req, User.class);
	}

	private User toUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setUserid(resultSet.getString("USERID"));
		user.setUsername(resultSet.getString("USERNAME"));
		user.setPassword(resultSet.getString("PASSWORD"));
		user.setEmail(resultSet.getString("EMAIL"));
		user.setRole(resultSet.getString("ROLE"));
		user.setEmailAuthKey(resultSet.getString("EMAIL_AUTH_KEY"));
		user.setDescription(resultSet.getString("DESCRIPTION"));
		user.setCreateDate(resultSet.getTimestamp("CREATE_DATE").toLocalDateTime());
		user.setUpdateDate(resultSet.getTimestamp("UPDATE_DATE").toLocalDateTime());
		return user;
	}
}
