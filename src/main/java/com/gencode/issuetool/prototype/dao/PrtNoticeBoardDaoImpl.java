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

import com.gencode.issuetool.prototype.etc.ObjMapper;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.prototype.obj.NoticeBoard;
import com.gencode.issuetool.prototype.obj.NoticeBoardEx;

@Component
public class PrtNoticeBoardDaoImpl extends PrtAbstractDaoImpl implements PrtNoticeBoardDao {
	final String queryStr = "select id,title,content,register_id,updated_dtm,created_dtm,post_type,post_level,content_type,ref_id, delete_yn, comment_cnt from notice_board where delete_yn='N' ";
	final String queryStrEx = "select nb.id,nb.title,nb.content,nb.register_id,nb.updated_dtm,nb.created_dtm,nb.post_type,nb.post_level,nb.content_type,nb.ref_id, nb.delete_yn, nb.comment_cnt " + 
			" , frt.addfile_cnt, frt.addfile_type " +
			ObjMapper.USER_INFO_LIST+ 
			"  from proto_notice_board nb " + 
			"left outer join (select fr.ref_id, count(*) addfile_cnt, min(case when fi.mime_type like 'image%' then 'I' else 'F' end) addfile_type " + 
			"					 from proto_file_info fi, proto_file_reference fr where fi.id = fr.file_id and fi.delete_yn = 'N' and fr.delete_yn = 'N' group by fr.ref_id) frt on (frt.ref_id = nb.id) " + 
			"left outer join proto_user_info ui on (nb.register_id = ui.id) " + 
			"where nb.delete_yn = 'N'";
	public PrtNoticeBoardDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
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
					return ObjMapper.toNoticeBoard(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public Optional<NoticeBoardEx> loadEx(long id) {
		NoticeBoardEx t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				this.queryStrEx + "and nb.id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toNoticeBoardEx(resultSet);
				}));
		return Optional.of(t);
	}

	
	@Override
	public long delete(long id) {
		//namedParameterJdbcTemplate.update("DELETE FROM notice_board where id = :id",
		return namedParameterJdbcTemplate.update("UPDATE proto_notice_board SET delete_yn = 'Y',updated_dtm =NOW(3)  where id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public long update(NoticeBoard t) {
		return namedParameterJdbcTemplate.update("UPDATE proto_notice_board SET " +
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
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
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
            return ObjMapper.toNoticeBoard(resultSet);
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
		return internalSearch(this.queryStrEx, req, (resultSet, i) -> { return ObjMapper.toNoticeBoardEx(resultSet);});
	}
	
}
