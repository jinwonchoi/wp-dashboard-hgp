/**=========================================================================================
<overview>게시판댓글관련 DAO 처리구현
  </overview>
==========================================================================================*/
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

import com.gencode.issuetool.etc.ObjMapper;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.BoardComment;
@Component
public class BoardCommentDaoImpl extends AbstractDaoImpl implements BoardCommentDao {

	final String queryStr = "select bc.id, bc.level, bc.sort_key, bc.content, bc.register_id, bc.ref_board_id, bc.board_type, bc.ref_comment_id, bc.updated_dtm, bc.created_dtm " + ObjMapper.USER_INFO_LIST
			+" from board_comment bc " 
			+" left outer join user_info ui on (bc.register_id = ui.id) where bc.delete_yn='N' ";
	final String orderByIdStr = " order by case when bc.sort_key = '' then bc.id else concat(bc.sort_key,'.',bc.id) end ";
	
	public BoardCommentDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(BoardComment t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO board_comment(level, sort_key, content, register_id, ref_board_id, board_type, ref_comment_id, updated_dtm, created_dtm) " + 
				"VALUES(:level,:sortKey,:content,:registerId, :refBoardId, :boardType, :refCommentId, NOW(3),NOW(3))"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<BoardComment> load(long id) {
		BoardComment t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				this.queryStr + 
				" and bc.id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toBoardComment(resultSet);
				}));
		return Optional.of(t);
	}

	
	@Override
	public long delete(long id) {
		//namedParameterJdbcTemplate.update("DELETE FROM board_comment where id = :id",
		return namedParameterJdbcTemplate.update("UPDATE board_comment SET delete_yn = 'Y',updated_dtm =NOW(3)  where id = :id",
			new MapSqlParameterSource("id", id));
	}

	@Override
	public long update(BoardComment t) {
		return namedParameterJdbcTemplate.update("UPDATE board_comment SET " +
				"level    =:level,"+
				"sort_key    =:sortKey,"+
				"content    =:content,"+
				"register_id=:registerId,"+
				"ref_board_id  =:refBoardId,"+
				"board_type  =:boardType,"+
				"ref_comment_id  =:refCommentId,"+
				"updated_dtm =NOW(3) "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<BoardComment>> loadAll() {
		List<BoardComment> list = jdbcTemplate.query(
				this.queryStr, (resultSet, i) -> {
            return toBoardComment(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<BoardComment>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map,"bc");
		List<BoardComment> t = namedParameterJdbcTemplate.query
				(this.queryStr
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<BoardComment>(BoardComment.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<BoardComment>>> search(PageRequest req) {
		return internalSearch(this.queryStr, orderByIdStr, "bc", req, (resultSet, i) -> { return toBoardComment(resultSet);});
	}
	
	private BoardComment toBoardComment(ResultSet resultSet) throws SQLException {
		BoardComment obj = new BoardComment();
		obj.setId(resultSet.getLong("ID"));
		obj.setLevel(resultSet.getInt("LEVEL"));
		obj.setSortKey(resultSet.getString("SORT_KEY"));
		obj.setContent(resultSet.getString("CONTENT"));
		obj.setRegisterId(resultSet.getLong("REGISTER_ID"));
		obj.setRegisterUserInfo(ObjMapper.toUserInfo("UI_", resultSet));
		obj.setRefBoardId(resultSet.getLong("REF_BOARD_ID"));
		obj.setBoardType(resultSet.getString("BOARD_TYPE"));
		obj.setRefCommentId(resultSet.getLong("REF_COMMENT_ID"));
		//obj.setDeleteYn(resultSet.getString("DELETE_YN"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}
}
