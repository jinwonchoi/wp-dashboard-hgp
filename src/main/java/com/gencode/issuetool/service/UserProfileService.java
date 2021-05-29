package com.gencode.issuetool.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.dao.FileInfoDao;
import com.gencode.issuetool.dao.FileReferenceDao;
import com.gencode.issuetool.dao.UserDao;
import com.gencode.issuetool.dao.UserInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.FileReference;
import com.gencode.issuetool.obj.User;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.storage.EmbedFileSystemStorageService;
import com.gencode.issuetool.storage.ProfileImgStorageService;

@Service
public class UserProfileService {
	private final static Logger logger = LoggerFactory.getLogger(UserProfileService.class);

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private FileReferenceDao fileReferenceDao;
	
	@Autowired
	private ProfileImgStorageService profileImgStorageService;

//	@Transactional
//	public Optional<User> findUser(String userId) throws TooManyRowException, IOException {
//		return userInfoDao.load(userId);		
//	}
	/** 
	 * 프로필 사진 등록
	 * @param multipartFile
	 * @param userInfo
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public Optional<UserInfo> uploadProfileImage(MultipartFile multipartFile, UserInfo userInfo) throws IOException {
		if (userInfo.getId() == 0) {
			long userId = userInfoDao.register(userInfo);
			userInfo = userInfoDao.load(userId).get();
		}
		FileInfo fileInfo = new FileInfo();
		fileInfo.setRegisterId(userInfo.getId());
		long fileId = fileInfoDao.register(fileInfo);
		fileInfo.setId(fileId);
		fileInfo.setFileType(Constant.FILE_INFO_FILE_TYPE_PROFILE.get());
		fileInfo = profileImgStorageService.store(multipartFile, fileInfo);
		fileInfo.setStatus(Constant.FILE_INFO_STATUS_COMPLETE.get());
		fileInfoDao.update(fileInfo);
		FileReference fileReference = new FileReference(fileInfo.getId(), userInfo.getId(), fileInfo.getFileType()
				,fileInfo.getFileOrgName()
				, fileInfo.getStorageDir(),null, Constant.FILE_REFERENCE_STATUS_COMPLETE.get(), 
				fileInfo.getRegisterId());
		fileReferenceDao.register(fileReference);
		
		userInfo.setProfileUrl(profileImgStorageService.generateProfileImage(fileInfo));
		userInfo.setAvatarUrl(profileImgStorageService.generateThumbImage(fileInfo));
		userInfoDao.update(userInfo);
		return getUserProfile(userInfo.getId());
	}
	
	/**
	 * 프로파일 사진삭제
	 * @param userId
	 */
	@Transactional
	public Optional<UserInfo> deleteProfileImage(long userId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("refId", Long.toString(userId));
		map.put("refType", Constant.FILE_REFERENCE_REF_TYPE_PROFILE.get());
		for (FileReference fileRef : fileReferenceDao.search(map).get()) {
			profileImgStorageService.deleteFile(userId, fileRef.getFileId());
			UserInfo userInfo = userInfoDao.load(userId).get();
			userInfo.setProfileUrl(null);
			userInfo.setAvatarUrl(null);
			userInfoDao.update(userInfo);
			break;
		};
		return getUserProfile(userId);
	}
	
	/**
	 * 개인정보 등록 
	 * @param userInfo
	 * @return
	 */
	@Transactional
	public Optional<UserInfo> updateUserProfile(UserInfo userInfo) {
		userInfoDao.update(userInfo);
		return getUserProfile(userInfo.getId());
	}
	
	/** 
	 * 개인정보 조회
	 * @param id
	 * @return
	 */
	public Optional<UserInfo> getUserProfile(long id) {
		UserInfo userInfo = userInfoDao.load(id).get();
		return Optional.of(userInfo);
	}
	

}
