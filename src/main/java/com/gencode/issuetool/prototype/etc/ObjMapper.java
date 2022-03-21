package com.gencode.issuetool.prototype.etc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.prototype.obj.ChatInfo;
import com.gencode.issuetool.prototype.obj.FileReference;
import com.gencode.issuetool.prototype.obj.MessageLog;
import com.gencode.issuetool.prototype.obj.NoticeBoard;
import com.gencode.issuetool.prototype.obj.NoticeBoardEx;
import com.gencode.issuetool.prototype.obj.UserInfo;

public class ObjMapper {
	static final public String USER_INFO_LIST = " ,ui.id ui_id,ui.login_id ui_login_id,ui.level ui_level,ui.role ui_role,ui.position ui_position,ui.group_id ui_group_id,ui.user_name ui_user_name,ui.user_email ui_user_email,ui.mobile_no ui_mobile_no,ui.phone_no ui_phone_no,ui.passwd ui_passwd,ui.auth_key ui_auth_key,ui.use_yn ui_use_yn,ui.passwd_update_date ui_passwd_update_date,ui.user_profile ui_user_profile,ui.user_status ui_user_status,ui.profile_url ui_profile_url,ui.avatar_url ui_avatar_url,ui.access_token ui_access_token,ui.notice_id ui_notice_id,ui.registered_dtm ui_registered_dtm,ui.updated_dtm ui_updated_dtm,ui.created_dtm ui_created_dtm ";
	
	
	static public FileReference toFileReference(ResultSet resultSet) throws SQLException {
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
		obj.setRegisterUserInfo(toUserInfo("UI_", resultSet));
		//obj.setDeleteYn(resultSet.getString("DELETE_YN"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		
		return obj;
	}
	
	static public UserInfo toUserInfo(String prefix, ResultSet resultSet) throws SQLException {
		UserInfo obj = new UserInfo();
		if (resultSet.getLong(prefix+"ID")==0) return null;
		obj.setId(resultSet.getLong(prefix+"ID"));	
		obj.setLoginId(resultSet.getString(prefix+"LOGIN_ID"));	
		obj.setLevel(resultSet.getString(prefix+"LEVEL"));	
		obj.setRole(resultSet.getString(prefix+"ROLE"));	
		obj.setPosition(resultSet.getString(prefix+"POSITION"));	
		obj.setGroupId(resultSet.getString(prefix+"GROUP_ID"));	
		obj.setUserName(resultSet.getString(prefix+"USER_NAME"));	
		obj.setUserEmail(resultSet.getString(prefix+"USER_EMAIL"));	
		obj.setMobileNo(resultSet.getString(prefix+"MOBILE_NO"));	
		obj.setPhoneNo(resultSet.getString(prefix+"PHONE_NO"));	
		obj.setPasswd(resultSet.getString(prefix+"PASSWD"));	
		obj.setAuthKey(resultSet.getString(prefix+"AUTH_KEY"));	
		obj.setUseYn(resultSet.getString(prefix+"USE_YN"));	
		obj.setPasswdUpdateDate(resultSet.getString(prefix+"PASSWD_UPDATE_DATE"));	
		obj.setUserProfile(resultSet.getString(prefix+"USER_PROFILE"));	
		obj.setUserStatus(resultSet.getString(prefix+"USER_STATUS"));
		obj.setProfileUrl(resultSet.getString(prefix+"PROFILE_URL"));
		obj.setAvatarUrl(resultSet.getString(prefix+"AVATAR_URL"));
		obj.setAccessToken(resultSet.getString(prefix+"ACCESS_TOKEN"));	
		obj.setNoticeId(resultSet.getLong(prefix+"NOTICE_ID"));	
		obj.setRegisteredDtm(Utils.DtToStr(resultSet.getTimestamp(prefix+"REGISTERED_DTM")));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp(prefix+"UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp(prefix+"CREATED_DTM")));

		return obj;
	}

	static public ChatInfo toChatInfo(ResultSet resultSet) throws SQLException {
		ChatInfo obj = new ChatInfo();
		obj.setId(resultSet.getLong("ID"));
		obj.setSenderId(resultSet.getLong("SENDER_ID"));
		obj.setReceiverId(resultSet.getLong("RECEIVER_ID"));
		obj.setLastMessageId(resultSet.getLong("LAST_MESSAGE_ID"));
		obj.setLastMessage(resultSet.getString("LAST_MESSAGE"));
		obj.setUnreadCnt(resultSet.getLong("UNREAD_CNT"));
		obj.setStatus(resultSet.getString("STATUS"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}

	static public MessageLog toMessageLog(ResultSet resultSet) throws SQLException {
		MessageLog obj = new MessageLog();
		obj.setId(resultSet.getLong("ID"));	
		obj.setChatId(resultSet.getLong("CHAT_ID"));	
		obj.setSenderId(resultSet.getLong("SENDER_ID"));	
		obj.setReceiverId(resultSet.getLong("RECEIVER_ID"));	
		obj.setStatus(resultSet.getString("STATUS"));	
		obj.setMessage(resultSet.getString("MESSAGE"));	
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}

	static public NoticeBoard toNoticeBoard(ResultSet resultSet) throws SQLException {
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
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}

	static public NoticeBoardEx toNoticeBoardEx(ResultSet resultSet) throws SQLException {
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
		obj.setAddFileCnt(resultSet.getInt("ADDFILE_CNT"));
		obj.setAddFileType(resultSet.getString("ADDFILE_TYPE"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}

}
