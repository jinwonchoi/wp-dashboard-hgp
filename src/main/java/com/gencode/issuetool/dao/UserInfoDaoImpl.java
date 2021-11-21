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

import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.User;
import com.gencode.issuetool.obj.UserInfo;

@Component("UserInfoDao")
public class UserInfoDaoImpl extends AbstractDaoImpl implements UserInfoDao {

	final String fields= "id,login_id,level,role,position,group_id,user_name,user_email,mobile_no,phone_no,passwd,auth_key,use_yn,passwd_update_date,user_profile,user_status,profile_url,avatar_url,access_token,notice_id,registered_dtm,updated_dtm,created_dtm";
	
	public UserInfoDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(UserInfo t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO user_info(login_id,level,role,position,group_id,user_name,user_email,mobile_no,phone_no,passwd,auth_key,use_yn,passwd_update_date,user_profile,user_status,profile_url,avatar_url,access_token,notice_id,registered_dtm,updated_dtm,created_dtm)\r\n" + 
				"VALUES(:loginId,:level,:role,:position,:groupId,:userName,:userEmail,:mobileNo,:phoneNo,:passwd,:authKey,:useYn,:passwdUpdateDate,:userProfile,:userStatus,:profileUrl,:avatarUrl,:accessToken,:noticeId,NOW(3),NOW(3),NOW(3) )"
				,new BeanPropertySqlParameterSource(t), keyHolder);
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Optional<UserInfo> login(String loginId) {
		UserInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from user_info where use_yn = 'Y' and user_status in ('R', 'C', 'A') and login_id = :loginId",
				new MapSqlParameterSource("loginId",loginId ), (resultSet,i)->{
					return toUserInfo(resultSet);
				}));
		return Optional.of(t);
	}

	/**
	 * 사용안함.
	 *  
	 */
	@Override
	public void activate(UserInfo userInfo) {
		namedParameterJdbcTemplate.update("UPDATE user_info SET user_status = 'A'" + 
				" WHERE login_id = :loginId and auth_key = :authKey"
				,new BeanPropertySqlParameterSource(userInfo));
	}
	
	@Override
	public Optional<UserInfo> load(long id) {
		UserInfo t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select "+fields+" from user_info where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return toUserInfo(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long forceDelete(long id) {
		return namedParameterJdbcTemplate.update("DELETE FROM user_info where id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public long delete(long id) {
		return namedParameterJdbcTemplate.update("UPDATE user_info SET user_status = 'D', use_yn='N'" + 
				" WHERE id=:id"
				,new MapSqlParameterSource("id",id ));
	}

	@Override
	public long update(UserInfo t) {
		return namedParameterJdbcTemplate.update("UPDATE user_info SET " +
				"login_id			=:loginId,"+
				"level				=:level,"+
				"role				=:role,"+
				"position			=:position,"+
				"group_id			=:groupId,"+
				"user_name			=:userName,"+
				"user_email			=:userEmail,"+
				"mobile_no			=:mobileNo,"+
				"phone_no			=:phoneNo,"+
				"passwd				=:passwd,"+
				"auth_key			=:authKey,"+
				"use_yn				=:useYn,"+
				"passwd_update_date	=:passwdUpdateDate,"+
				"user_profile		=:userProfile,"+
				"user_status		=:userStatus,"+
				"profile_url		=:profileUrl,"+
				"avatar_url			=:avatarUrl,"+
				"access_token		=:accessToken,"+
				"notice_id			=:noticeId,"+
				"registered_dtm		=:registeredDtm,"+
				"updated_dtm		=NOW(3) "+				
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));
	}

	@Override
	public Optional<List<UserInfo>> loadAll() {
		List<UserInfo> list = jdbcTemplate.query(
				"select "+fields+" from user_info where 1=1", (resultSet, i) -> {
            return toUserInfo(resultSet);
        });
		return Optional.of(list);
	}

	@Override
	public Optional<List<UserInfo>> searchExtrict(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map, false);
		List<UserInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from user_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<List<UserInfo>> search(Map<String, String> map) {
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<UserInfo> t = namedParameterJdbcTemplate.query
				("select "+fields+" from user_info where 1=1"
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
		return Optional.of(t);
	}

	@Override
	public Optional<PageResultObj<List<UserInfo>>> search(PageRequest req) {
		String queryStr = "select "+fields+" from user_info where 1=1";
		return internalSearch(queryStr, req, UserInfo.class);
	}

	private UserInfo toUserInfo(ResultSet resultSet) throws SQLException {
		UserInfo obj = new UserInfo();
		obj.setId(resultSet.getLong("ID"));	
		obj.setLoginId(resultSet.getString("LOGIN_ID"));	
		obj.setLevel(resultSet.getString("LEVEL"));	
		obj.setRole(resultSet.getString("ROLE"));	
		obj.setPosition(resultSet.getString("POSITION"));	
		obj.setGroupId(resultSet.getString("GROUP_ID"));	
		obj.setUserName(resultSet.getString("USER_NAME"));	
		obj.setUserEmail(resultSet.getString("USER_EMAIL"));	
		obj.setMobileNo(resultSet.getString("MOBILE_NO"));	
		obj.setPhoneNo(resultSet.getString("PHONE_NO"));	
		obj.setPasswd(resultSet.getString("PASSWD"));	
		obj.setAuthKey(resultSet.getString("AUTH_KEY"));	
		obj.setUseYn(resultSet.getString("USE_YN"));	
		obj.setPasswdUpdateDate(resultSet.getString("PASSWD_UPDATE_DATE"));	
		obj.setUserProfile(resultSet.getString("USER_PROFILE"));	
		obj.setUserStatus(resultSet.getString("USER_STATUS"));
		obj.setProfileUrl(resultSet.getString("PROFILE_URL"));
		obj.setAvatarUrl(resultSet.getString("AVATAR_URL"));
		obj.setAccessToken(resultSet.getString("ACCESS_TOKEN"));	
		obj.setNoticeId(resultSet.getLong("NOTICE_ID"));	
		obj.setRegisteredDtm(Utils.DtToStr(resultSet.getTimestamp("REGISTERED_DTM")));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));
		return obj;
	}

}
