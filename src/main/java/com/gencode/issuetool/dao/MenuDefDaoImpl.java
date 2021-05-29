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
import com.gencode.issuetool.obj.BizInfo;
import com.gencode.issuetool.obj.MenuDef;

@Component
public class MenuDefDaoImpl extends AbstractDaoImpl implements MenuDefDao {

	public MenuDefDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(MenuDef t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO menu_def(name,menu_path,roles_access,roles_read,roles_write)\r\n" + 
				"VALUES(:name,:menuPath,:rolesAccess,:rolesRead,:rolesWrite)"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<MenuDef> load(long id) {
		MenuDef task = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select id,name,menu_path,roles_access,roles_read,roles_write from menu_def where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toMenuDef(resultSet);
				}));
		return Optional.of(task);
	}

	@Override
	public void delete(long id) {
		namedParameterJdbcTemplate.update("DELETE FROM menu_def where id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public void update(MenuDef t) {
		namedParameterJdbcTemplate.update("UPDATE menu_def SET " + 
				"name        =:name,"+
				"menu_path   =:menuPath,"+
				"roles_access=:rolesAccess,"+
				"roles_read  =:rolesRead,"+
				"roles_write =:rolesWrite "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<MenuDef>> loadAll() {
		List<MenuDef> list = jdbcTemplate.query("select id,name,menu_path,roles_access,roles_read,roles_write from menu_def where 1=1", (resultSet, i) -> {
            return toMenuDef(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<MenuDef>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<MenuDef> tasks = namedParameterJdbcTemplate.query
				("select id,name,menu_path,roles_access,roles_read,roles_write from menu_def where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<MenuDef>(MenuDef.class));
		return Optional.of(tasks);
	}

	@Override
	public Optional<PageResultObj<List<MenuDef>>> search(PageRequest req) {
		String queryStr = "select id,name,menu_path,roles_access,roles_read,roles_write from menu_def where 1=1";
		return internalSearch(queryStr, req, MenuDef.class);
	}

	private MenuDef toMenuDef(ResultSet resultSet) throws SQLException {
		MenuDef obj = new MenuDef();
		obj.setId(resultSet.getLong("ID"));
		obj.setName(resultSet.getString("NAME"));
		obj.setMenuPath(resultSet.getString("MENU_PATH"));
		obj.setRolesAccess(resultSet.getString("ROLES_ACCESS"));
		obj.setRolesRead(resultSet.getString("ROLES_READ"));
		obj.setRolesWrite(resultSet.getString("ROLES_WRITE"));
		return obj;
	}
}
