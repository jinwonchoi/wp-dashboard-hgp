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

import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.ObjMapper;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.FileInfo;


@Component
public class FileInfoDaoImpl extends AbstractDaoImpl implements FileInfoDao {
	//final String queryStr = " select id, file_name, file_size, file_ext, mime_type, storage_dir, file_org_name, file_desc, tag_info, register_id, delete_yn, updated_dtm, created_dtm FROM file_info where delete_yn='N' ";
	final String queryStr = "select fi.id, fi.file_name,fi.file_type, fi.file_size, fi.file_ext, fi.mime_type, fi.storage_dir, fi.file_org_name, fi.file_desc, fi.tag_info, fi.status, fi.register_id, fi.delete_yn, fi.updated_dtm, fi.created_dtm "  
			 + ObjMapper.USER_INFO_LIST
			+" from file_info fi " 
			+" left outer join user_info ui on (fi.register_id = ui.id) where fi.delete_yn='N' ";
	final String queryStr2 = "select fi.id, fi.file_name, fi.file_type, fi.file_size, fi.file_ext, fi.mime_type, fi.storage_dir, fi.file_org_name, fi.file_desc, fi.tag_info, fi.status, fi.register_id, fi.delete_yn, fi.updated_dtm, fi.created_dtm "
			 + ObjMapper.USER_INFO_LIST
			+" from file_info fi"
			+" left outer join user_info ui on (fi.register_id = ui.id) "
			+", file_reference fr where fi.id = fr.file_id and fr.ref_id = :refId and fi.delete_yn = 'N' and fr.delete_yn = 'N' ";
	
	public FileInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(FileInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO file_info(file_name, file_type, file_size, file_ext, mime_type, storage_dir, file_org_name, file_desc, tag_info, status, register_id, updated_dtm, created_dtm) " + 
				"VALUES(:fileName,:fileType,:fileSize, :fileExt,:mimeType, :storageDir, :fileOrgName, :fileDesc, :tagInfo, :status, :registerId, NOW(3),NOW(3))"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<FileInfo> load(long id) {
		FileInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				this.queryStr + 
				" and fi.id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toFileInfo(resultSet);
				}));
		return Optional.of(t);
	}

	
	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("UPDATE file_info SET delete_yn = 'Y',updated_dtm =NOW(3)  where id = :id",
			new MapSqlParameterSource("id", id));
	}

	@Override
	public long forceDelete(long id) {
		return namedParameterJdbcTemplate.update("DELETE FROM file_info where id = :id",
			new MapSqlParameterSource("id", id));
	}
	
	@Override
	public long completeUpload(long id) {
		return namedParameterJdbcTemplate.update("UPDATE file_info SET status = 'C',updated_dtm =NOW(3)  where id = :id",
				new MapSqlParameterSource("id", id));
	}
	
	@Override
	public long update(FileInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE file_info SET " +
				"file_name =:fileName,"+ 
				"file_type =:fileType,"+ 
				"file_size =:fileSize,"+
				"file_ext =:fileExt, "+
				"mime_type=:mimeType, "+
				"storage_dir=:storageDir,"+ 
				"file_org_name =:fileOrgName,"+ 
				"file_desc=:fileDesc, "+
				"tag_info=:tagInfo, "+
				"status=:status, "+
				"register_id=:registerId,"+ 
				"updated_dtm=NOW(3)"+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<FileInfo>> loadAll() {
		List<FileInfo> list = jdbcTemplate.query(
				this.queryStr, (resultSet, i) -> {
            return toFileInfo(resultSet);
        });
		return Optional.of(list);
	}
	
	/**
	 * 미완료 파일은 하루 이상지나고 status=N인 registerId가 나인 파일들 
	 */
	@Override
	public Optional<List<FileInfo>> getUncompletedFiles(long registerId) {
		List<FileInfo> list = namedParameterJdbcTemplate.query(
				this.queryStr
				+" and fi.updated_dtm <= DATE_ADD(now(), INTERVAL -1 DAY) and fi.status='N' and fi.register_id = :registerId ",
				new MapSqlParameterSource("registerId", registerId),
				(resultSet, i) -> {
            return toFileInfo(resultSet);
        });
		return Optional.of(list);
	}

	/**
	 * 첨부파일목록 / 콘텐츠 링크파일목록 조회
	 */
	@Override
	public Optional<List<FileInfo>> getFilesByRefId(long refId, String refType) {
		
		StringBuffer sb =new StringBuffer();
		sb.append(this.queryStr2); 
		if (refType.equals(Constant.FILE_REFERENCE_REF_TYPE_ADDFILE.get())) {
			sb.append(" and fr.ref_type = 'A'");
		} else if (refType.equals("")) {
			//
		} else {
			sb.append(" and fr.ref_type in ('E')");
		}
		List<FileInfo> list = namedParameterJdbcTemplate.query(
				sb.toString(),
				new MapSqlParameterSource("refId", refId),
				(resultSet, i) -> {
            return toFileInfo(resultSet);
        });
		return Optional.of(list);
	}
	
	@Override
	public Optional<List<FileInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<FileInfo> t = namedParameterJdbcTemplate.query
				(this.queryStr
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<FileInfo>(FileInfo.class));
		return Optional.of(t);
	}
	
	private FileInfo toFileInfo(ResultSet resultSet) throws SQLException {
		FileInfo obj = new FileInfo();
		obj.setId(resultSet.getLong("ID"));
		obj.setFileName(resultSet.getString("FILE_NAME"));
		obj.setFileType(resultSet.getString("FILE_TYPE"));
		obj.setFileSize(resultSet.getLong("FILE_SIZE"));
		obj.setFileExt(resultSet.getString("FILE_EXT"));
		obj.setMimeType(resultSet.getString("MIME_TYPE"));
		obj.setStorageDir(resultSet.getString("STORAGE_DIR"));
		obj.setFileOrgName(resultSet.getString("FILE_ORG_NAME"));
		obj.setFileDesc(resultSet.getString("FILE_DESC"));
		obj.setTagInfo(resultSet.getString("TAG_INFO"));
		obj.setStatus(resultSet.getString("STATUS"));
		obj.setRegisterId(resultSet.getLong("REGISTER_ID"));
		obj.setRegisterUserInfo(ObjMapper.toUserInfo("UI_", resultSet));
		//obj.setDeleteYn(resultSet.getString("DELETE_YN"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}

	@Override
	public Optional<PageResultObj<List<FileInfo>>> search(PageRequest req) {
		// TODO Auto-generated method stub
		return null;
	}
}
