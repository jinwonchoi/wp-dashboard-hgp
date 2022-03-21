package com.gencode.issuetool.prototype.dao;

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
import com.gencode.issuetool.prototype.etc.ObjMapper;
import com.gencode.issuetool.prototype.obj.MessageLog;

@Component
public class PrtMessageLogDaoImpl extends PrtAbstractDaoImpl implements PrtMessageLogDao {
	final String queryStr = "select id, chat_id, sender_id, receiver_id, status, message, updated_dtm, created_dtm from proto_message_log where 1=1";

	public PrtMessageLogDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(MessageLog t) {
		namedParameterJdbcTemplate.update(
				"INSERT INTO proto_message_log(chat_id, sender_id, receiver_id, status, message, updated_dtm, created_dtm)\r\n"
						+ "VALUES(:chatId, :senderId, :receiverId, :status, :message,NOW(3),NOW(3) )",
				new BeanPropertySqlParameterSource(t));
		return 0;
	}

	@Override
	public Optional<MessageLog> load(long id) {
		MessageLog t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select id, chat_id, sender_id, receiver_id, status, message, updated_dtm, created_dtm from proto_message_log where id = :id",
				new MapSqlParameterSource("id", id), (resultSet, i) -> {
					return ObjMapper.toMessageLog(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("DELETE FROM proto_message_log where id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public long update(MessageLog t) {
		return namedParameterJdbcTemplate.update(
				"UPDATE proto_message_log SET "+
						"sender_id       = :senderId,"+
						"receiver_id     = :receiverId,"+
						"last_message_id = :lastMessageId,"+
						"last_message    = :lastMessage,"+
						"unread_cnt      = :unreadCnt,"+
						"status          = :status,"+
						"updated_dtm    =now(3) "+
						"WHERE id = :id",
				new BeanPropertySqlParameterSource(t));
	}
	
	@Override
	public long updateByChatId(long chatId, String status) {
		MessageLog log = new MessageLog();
		log.setChatId(chatId);
		log.setStatus(status);
		return namedParameterJdbcTemplate.update(
				"UPDATE proto_message_log SET status=:status WHERE id = :chatId",
				new BeanPropertySqlParameterSource(log));
	}

	@Override
	public Optional<List<MessageLog>> loadAll() {
		List<MessageLog> list = jdbcTemplate.query(
				queryStr,
				(resultSet, i) -> {
					return ObjMapper.toMessageLog(resultSet);
				});
		return Optional.of(list);
	}

	@Override
	public Optional<List<MessageLog>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<MessageLog> t = namedParameterJdbcTemplate.query(
				queryStr + searchMapObj.andQuery(),
				searchMapObj.params(), new BeanPropertyRowMapper<MessageLog>(MessageLog.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<MessageLog>>> search(PageRequest req) {
		return internalSearch(queryStr, req, MessageLog.class);
	}

}
