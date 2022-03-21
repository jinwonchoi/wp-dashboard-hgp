package com.gencode.issuetool.prototype.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

import com.gencode.issuetool.prototype.etc.ObjMapper;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.prototype.obj.ChatInfo;

@Component
public class PrtChatInfoDaoImpl extends PrtAbstractDaoImpl implements PrtChatInfoDao {

	final String queryStr = " id, sender_id, receiver_id, last_message_id, last_message, unread_cnt, status, updated_dtm, created_dtm ";
//	final String qeuryExStr = "select ci.id, ci.sender_id, ci.receiver_id, ci.last_message_id, ci.last_message, ci.unread_cnt, ci.status, ci.updated_dtm, ci.created_dtm "
//			+ ObjMapper.USER_INFO_LIST
//			+ " from proto_chat_info ci "
//			+ " left outer join user_info ui on (css.agent_id = ui.agent_id) ";
	public PrtChatInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(ChatInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO proto_chat_info(sender_id, receiver_id, last_message_id, last_message, unread_cnt, status, updated_dtm, created_dtm)\r\n" + 
				"VALUES(:senderId, :receiverId, :lastMessageId, :lastMessage, :unreadCnt, :status,now(3) ,now(3) )"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<ChatInfo> load(long id) {
		ChatInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query
				("select "+queryStr+" from proto_chat_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toChatInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("DELETE FROM proto_chat_info where id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public long update(ChatInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE proto_chat_info SET " + 
				"sender_id       = :senderId,"+
				"receiver_id     = :receiverId,"+
				"last_message_id = :lastMessageId,"+
				"last_message    = :lastMessage,"+
				"unread_cnt      = :unreadCnt,"+
				"status          = :status,"+
				"updated_dtm    =now(3) "+
				" WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public void resetUnreadCnt(long chatId) {
		namedParameterJdbcTemplate.update("UPDATE proto_chat_info SET unread_cnt=0 where id = :id"
				,new MapSqlParameterSource("id",chatId));
	}

	@Override
	public Optional<List<ChatInfo>> loadAll() {
		List<ChatInfo> list = jdbcTemplate.query
				("select "+queryStr+" from proto_chat_info where 1=1 ", (resultSet, i) -> {
            return ObjMapper.toChatInfo (resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<ChatInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<ChatInfo> tasks = namedParameterJdbcTemplate.query
				("select "+queryStr+" from proto_chat_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<ChatInfo>(ChatInfo.class));
		return Optional.of(tasks);
	}
	
	@Override
	public Optional<PageResultObj<List<ChatInfo>>> search(PageRequest req) {
		return internalSearch("select "+this.queryStr+" from proto_chat_info where 1=1", req, ChatInfo.class);
	}


}
