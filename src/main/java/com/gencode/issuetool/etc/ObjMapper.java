package com.gencode.issuetool.etc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.UserInfo;

public class ObjMapper {
	static final public String USER_INFO_LIST = " ,ui.id ui_id,ui.login_id ui_login_id,ui.level ui_level,ui.role ui_role,ui.position ui_position,ui.group_id ui_group_id,ui.user_name ui_user_name,ui.user_email ui_user_email,ui.mobile_no ui_mobile_no,ui.phone_no ui_phone_no,ui.passwd ui_passwd,ui.auth_key ui_auth_key,ui.use_yn ui_use_yn,ui.passwd_update_date ui_passwd_update_date,ui.user_profile ui_user_profile,ui.user_status ui_user_status,ui.profile_url ui_profile_url,ui.avatar_url ui_avatar_url,ui.access_token ui_access_token,ui.notice_id ui_notice_id,ui.registered_dtm ui_registered_dtm,ui.updated_dtm ui_updated_dtm,ui.created_dtm ui_created_dtm ";
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
}
