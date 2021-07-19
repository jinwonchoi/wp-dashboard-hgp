package com.gencode.issuetool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.NotSupportedException;

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
import com.gencode.issuetool.etc.ObjMapper;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticePersonal;


@Component
public class NoticePersonalDaoImpl extends AbstractDaoImpl implements NoticePersonalDao {
	
	public NoticePersonalDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(NoticePersonal t) {
		return 0;
	}

	@Override
	public Optional<NoticePersonal> load(long id) {
		//throw new NotSupportedException();
		return null;
	}
	
	@Override
	public void delete(long id) {
		//
	}

	@Override
	public void deleteByNoticeId(long noticeId) {
		namedParameterJdbcTemplate.update("DELETE FROM notice_personal where notice_id = :noticeId",
			new MapSqlParameterSource("noticeId", noticeId));
	}
	
	@Override
	public void deleteByUserId(long userId) {
		namedParameterJdbcTemplate.update("DELETE FROM notice_personal where user_id = :userId",
			new MapSqlParameterSource("userId", userId));
	}
	
	@Override
	public void forceDelete(NoticePersonal t) {
		namedParameterJdbcTemplate.update("DELETE FROM notice_personal where notice_id = :noticeId and user_id = :userId",
				new BeanPropertySqlParameterSource(t));
	}
	
	@Override
	public void update(NoticePersonal t) {
		//throw new NotSupportedException();
	}

	@Override
	public Optional<List<NoticePersonal>> loadAll() {
		return null;
	}
		
	@Override
	public Optional<List<NoticePersonal>> search(Map<String, String> map) {
		return null;
	}
	
	private NoticePersonal toNoticePersonal(ResultSet resultSet) throws SQLException {
		NoticePersonal obj = new NoticePersonal();
		obj.setNoticeId(resultSet.getLong("NOTICE_ID"));
		obj.setUserId(resultSet.getLong("USER_ID"));
		return obj;
	}

	@Override
	public Optional<PageResultObj<List<NoticePersonal>>> search(PageRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insertAll(long noticeId) {
		return namedParameterJdbcTemplate.update("INSERT INTO notice_personal (notice_id, user_id) " + 
				"select :noticeId, id from user_info where user_status <> 'D'",
				new MapSqlParameterSource("noticeId", noticeId));
	}
}
