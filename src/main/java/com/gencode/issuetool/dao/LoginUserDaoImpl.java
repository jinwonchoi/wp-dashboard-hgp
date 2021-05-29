package com.gencode.issuetool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.BizInfo;

@Component
public class LoginUserDaoImpl implements LoginUserDao {

	private static final Logger log = LoggerFactory.getLogger(LoginUserDaoImpl.class);
	Map<String, String> userMap = new HashMap<String, String>(); 
	
	public LoginUserDaoImpl() {
	}

	public void register(String t) {
		userMap.put(t, t);
		userMap.forEach((k,v) -> {
			log.info("userMap:"+v);
		});
		log.info("loginId added:"+t);
	}

	public boolean contains(String t) {
		return userMap.containsKey(t);
	}

	public Optional<String> load(String t) {
		return Optional.of(userMap.get(t));
	}

	public void delete(String t) {
		userMap.remove(t);
		log.info("loginId removed:"+t);
	}

	public Optional<List<String>> loadAll() {
		return Optional.of(new ArrayList(userMap.values()));
	}
}
