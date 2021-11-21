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

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.RoleDef;

@Component
public class RoleDefDaoImpl extends AbstractDaoImpl implements RoleDefDao {

	public RoleDefDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	
	@Override
	public long register(RoleDef t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO role_def(role_id,role_name)\r\n" + 
				"VALUES(:roleId,:roleName )"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<RoleDef> load(long id) {
		RoleDef t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select role_id,role_name from role_def where role_id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toRoleDef(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("DELETE FROM role_def where role_id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public long update(RoleDef t) {
		return namedParameterJdbcTemplate.update("UPDATE role_def SET " +
				"role_name =:roleName "+ 
				"WHERE role_id = :roleId"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<RoleDef>> loadAll() {
		List<RoleDef> list = jdbcTemplate.query(
				"select role_id,role_name from role_def where 1=1", (resultSet, i) -> {
            return toRoleDef(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<RoleDef>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<RoleDef> t = namedParameterJdbcTemplate.query
				("select role_id,role_name from role_def where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<RoleDef>(RoleDef.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<RoleDef>>> search(PageRequest req) {
		String queryStr = "select role_id,role_name from role_def where 1=1";
		return internalSearch(queryStr, req, RoleDef.class);
	}

	private RoleDef toRoleDef(ResultSet resultSet) throws SQLException {
		RoleDef obj = new RoleDef();
		obj.setRoleId(resultSet.getString("ROLE_ID"));
		obj.setRoleName(resultSet.getString("ROLE_NAME"));
		return obj;
	}		 
}
