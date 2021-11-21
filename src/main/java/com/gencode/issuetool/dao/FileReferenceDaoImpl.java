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
import com.gencode.issuetool.obj.FileReference;

@Component
public class FileReferenceDaoImpl extends AbstractDaoImpl implements FileReferenceDao {

	//final String queryStr = "select id, file_id, ref_id, ref_type, ref_desc, storage_dir, tag_info, register_id, delete_yn, updated_dtm, created_dtm from file_reference where delete_yn='N' ";
	final String queryStr = "select fr.id, fr.file_id, fr.ref_id, fr.ref_type, fr.ref_desc, fr.storage_dir, fr.tag_info, fr.status, fr.register_id, fr.delete_yn, fr.updated_dtm, fr.created_dtm "  
			 + ObjMapper.USER_INFO_LIST
			+" from file_reference fr " 
			+" left outer join user_info ui on (fr.register_id = ui.id) where fr.delete_yn='N' ";
	
	public FileReferenceDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(FileReference t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO file_reference(file_id, ref_id, ref_type, ref_desc, storage_dir, tag_info, status, register_id, updated_dtm, created_dtm) " + 
				"VALUES(:fileId, :refId, :refType, :refDesc, :storageDir, :tagInfo, :status, :registerId, NOW(3),NOW(3))"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<FileReference> load(long id) {
		FileReference t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				this.queryStr + 
				" and id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toFileReference(resultSet);
				}));
		return Optional.of(t);
	}

	
	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("UPDATE file_reference SET delete_yn = 'Y',updated_dtm =NOW(3) where id = :id",
			new MapSqlParameterSource("id", id));
	}
	@Override
	public long delete(FileReference t) {
		return namedParameterJdbcTemplate.update("UPDATE file_reference SET delete_yn = 'Y',updated_dtm =NOW(3) where ref_id = :refId and file_id = :fileId"
				,new BeanPropertySqlParameterSource(t));
	}
	@Override
	public long forceDelete(long id) {
		return namedParameterJdbcTemplate.update("DELETE FROM file_reference where id = :id",
			new MapSqlParameterSource("id", id));
	}
	@Override
	public long forceDelete(FileReference t) {
		return namedParameterJdbcTemplate.update("DELETE FROM file_reference where ref_id = :refId and file_id = :fileId"
				,new BeanPropertySqlParameterSource(t));
	}
	
	@Override
	public long update(FileReference t) {
		return namedParameterJdbcTemplate.update("UPDATE file_reference SET " +
				"file_id = :fileId,"+
				"ref_id = :refId,"+
				"ref_type = :refType,"+
				"ref_desc = :refDesc,"+
				"storage_dir = :storageDir,"+
				"tag_info = :tagInfo,"+
				"status = :status,"+
				"register_id = :registerId,"+
				"updated_dtm=NOW(3)"+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<FileReference>> loadAll() {
		List<FileReference> list = jdbcTemplate.query(
				this.queryStr, (resultSet, i) -> {
            return toFileReference(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<FileReference>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<FileReference> t = namedParameterJdbcTemplate.query
				(this.queryStr
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<FileReference>(FileReference.class));
		return Optional.of(t);
	}
	
	private FileReference toFileReference(ResultSet resultSet) throws SQLException {
		FileReference obj = new FileReference();
		obj.setId(resultSet.getLong("ID"));
		obj.setFileId(resultSet.getLong("FILE_ID"));
		obj.setRefId(resultSet.getLong("REF_ID"));
		obj.setRefType(resultSet.getString("REF_TYPE"));
		obj.setRefDesc(resultSet.getString("REF_DESC"));
		obj.setStorageDir(resultSet.getString("STORAGE_DIR"));
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
	public Optional<PageResultObj<List<FileReference>>> search(PageRequest req) {
		// TODO Auto-generated method stub
		return null;
	}
}
