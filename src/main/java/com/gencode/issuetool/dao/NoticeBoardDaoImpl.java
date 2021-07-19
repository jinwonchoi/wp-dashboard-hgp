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
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticeBoardEx;

@Component
public class NoticeBoardDaoImpl extends AbstractDaoImpl implements NoticeBoardDao {
	final String queryStr = "select id,title,content,register_id,updated_dtm,created_dtm,post_type,post_level,content_type,ref_id, delete_yn, comment_cnt, read_cnt from notice_board where delete_yn='N' ";
	final String queryStrEx = "select nb.id,nb.title,nb.content,nb.register_id,nb.updated_dtm,nb.created_dtm,nb.post_type,nb.post_level,nb.content_type,nb.ref_id, nb.delete_yn, nb.comment_cnt, nb.read_cnt " + 
			" , frt.addfile_cnt, frt.addfile_type " +
			 ObjMapper.USER_INFO_LIST +
			"  from notice_board nb " + 
			"left outer join (select fr.ref_id, count(*) addfile_cnt, min(case when fi.mime_type like 'image%' then 'I' else 'F' end) addfile_type " + 
			"					 from file_info fi, file_reference fr where fi.id = fr.file_id and fi.delete_yn = 'N' and fr.delete_yn = 'N' group by fr.ref_id) frt on (frt.ref_id = nb.id) " + 
			"left outer join user_info ui on (nb.register_id = ui.id) " + 
			"where nb.delete_yn = 'N'";
	public NoticeBoardDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(NoticeBoard t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO notice_board(title,content,register_id,updated_dtm,created_dtm,post_type,post_level,content_type,ref_id )" + 
				"VALUES(:title,:content,:registerId,NOW(3),NOW(3),:postType,:postLevel,:contentType,:refId )"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<NoticeBoard> load(long id) {
		NoticeBoard t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				this.queryStr+" and id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toNoticeBoard(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public Optional<NoticeBoardEx> loadEx(long id) {
		NoticeBoardEx t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				this.queryStrEx + "and nb.id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toNoticeBoardEx(resultSet);
				}));
		return Optional.of(t);
	}

	
	@Override
	public void delete(long id) {
		//namedParameterJdbcTemplate.update("DELETE FROM notice_board where id = :id",
		namedParameterJdbcTemplate.update("UPDATE notice_board SET delete_yn = 'Y',updated_dtm =NOW(3)  where id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public void update(NoticeBoard t) {
		namedParameterJdbcTemplate.update("UPDATE notice_board SET " +
				"title      =:title,"+ 
				"content    =:content,"+
				"register_id=:registerId,"+
				"updated_dtm =NOW(3),"+
				"post_type   =:postType,"+
				"post_level  =:postLevel,"+
				"content_type=:contentType,"+
				"ref_id      =:refId, "+
				"delete_yn   =:deleteYn, "+
				"comment_cnt =:commentCnt "+
				"read_cnt =:readCnt "+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public void incReadCnt(long id) {
		namedParameterJdbcTemplate.update("UPDATE notice_board SET read_cnt = (read_cnt+1) ,updated_dtm =NOW(3)  where id = :id",
				new MapSqlParameterSource("id", id));
	}

//	@Override
//	public void incCommentCnt(long id) {
//		namedParameterJdbcTemplate.update("UPDATE notice_board SET comment_cnt = (comment_cnt+1) ,updated_dtm =NOW(3)  where id = :id",
//				new MapSqlParameterSource("id", id));
//	}
//	@Override
//	public void decCommentCnt(long id) {
//		namedParameterJdbcTemplate.update("UPDATE notice_board SET comment_cnt = (comment_cnt-1) ,updated_dtm =NOW(3)  where id = :id",
//				new MapSqlParameterSource("id", id));
//	}

	@Override
	public Optional<List<NoticeBoard>> loadAll() {
		List<NoticeBoard> list = jdbcTemplate.query(
				this.queryStr, (resultSet, i) -> {
            return toNoticeBoard(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<NoticeBoard>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<NoticeBoard> t = namedParameterJdbcTemplate.query(
				this.queryStr
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<NoticeBoard>(NoticeBoard.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<NoticeBoard>>> search(PageRequest req) {
		return internalSearch(this.queryStr, req, NoticeBoard.class);
	}
	
	@Override
	public Optional<PageResultObj<List<NoticeBoardEx>>> searchEx(PageRequest req) {
		return internalSearch(this.queryStrEx, req, (resultSet, i) -> { return toNoticeBoardEx(resultSet);});
	}
	
	@Override
	public Optional<List<NoticeBoard>> searchMyNotice(Map<String, String> map) {
		String queryStr = "select id,title,content,register_id,updated_dtm,created_dtm,post_type,post_level,content_type,ref_id, delete_yn, comment_cnt, read_cnt from notice_board b, notice_personal a "
				+ " where b.id = a.notice_id "
				+ "  and a.user_id =:userId "
				+ "  and delete_yn='N' "
				;
		String userId = map.get("userId");
		List<NoticeBoard> t = namedParameterJdbcTemplate.query(
				queryStr, 
				new MapSqlParameterSource("userId", userId),
				new BeanPropertyRowMapper<NoticeBoard>(NoticeBoard.class));
		return Optional.of(t);
	}
		
	private NoticeBoard toNoticeBoard(ResultSet resultSet) throws SQLException {
		NoticeBoard obj = new NoticeBoard();
		obj.setId(resultSet.getLong("ID"));
		obj.setTitle(resultSet.getString("TITLE"));
		obj.setContent(resultSet.getString("CONTENT"));
		obj.setRegisterId(resultSet.getLong("REGISTER_ID"));
		obj.setPostType(resultSet.getString("POST_TYPE"));
		obj.setPostLevel(resultSet.getInt("POST_LEVEL"));
		obj.setContentType(resultSet.getString("CONTENT_TYPE"));
		obj.setRefId(resultSet.getLong("REF_ID"));
		obj.setDeleteYn(resultSet.getString("DELETE_YN"));
		obj.setCommentCnt(resultSet.getInt("COMMENT_CNT"));
		obj.setReadCnt(resultSet.getInt("READ_CNT"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}

	private NoticeBoardEx toNoticeBoardEx(ResultSet resultSet) throws SQLException {
		NoticeBoardEx obj = new NoticeBoardEx();
		obj.setId(resultSet.getLong("ID"));
		obj.setTitle(resultSet.getString("TITLE"));
		obj.setContent(resultSet.getString("CONTENT"));
		obj.setRegisterId(resultSet.getLong("REGISTER_ID"));
		obj.setRegisterUserInfo(ObjMapper.toUserInfo("UI_", resultSet));
		obj.setPostType(resultSet.getString("POST_TYPE"));
		obj.setPostLevel(resultSet.getInt("POST_LEVEL"));
		obj.setContentType(resultSet.getString("CONTENT_TYPE"));
		obj.setRefId(resultSet.getLong("REF_ID"));
		obj.setDeleteYn(resultSet.getString("DELETE_YN"));
		obj.setCommentCnt(resultSet.getInt("COMMENT_CNT"));
		obj.setCommentCnt(resultSet.getInt("COMMENT_CNT"));
		obj.setReadCnt(resultSet.getInt("READ_CNT"));
		obj.setAddFileCnt(resultSet.getInt("ADDFILE_CNT"));
		obj.setAddFileType(resultSet.getString("ADDFILE_TYPE"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}
}
