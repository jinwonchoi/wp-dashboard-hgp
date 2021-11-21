package com.gencode.issuetool.etc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gencode.issuetool.obj.FacilityInfo;
import com.gencode.issuetool.obj.InteriorInfo;
import com.gencode.issuetool.obj.EtcDeviceInfo;
import com.gencode.issuetool.obj.EventTimeLine;
import com.gencode.issuetool.obj.FacilRuleInfo;
import com.gencode.issuetool.obj.FacilTagInfo;
import com.gencode.issuetool.obj.FacilTagMapping;
import com.gencode.issuetool.obj.AreaInfo;
import com.gencode.issuetool.obj.IotDeviceInfo;
import com.gencode.issuetool.obj.IotRuleInfo;
import com.gencode.issuetool.obj.LocationInfo;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.PlantInfo;
import com.gencode.issuetool.obj.UserInfo;

public class ObjMapper {
	static final public String USER_INFO_LIST = " ,ui.id ui_id,ui.login_id ui_login_id,ui.level ui_level,ui.role ui_role,ui.position ui_position,ui.group_id ui_group_id,ui.user_name ui_user_name,ui.user_email ui_user_email,ui.mobile_no ui_mobile_no,ui.phone_no ui_phone_no,ui.passwd ui_passwd,ui.auth_key ui_auth_key,ui.use_yn ui_use_yn,ui.passwd_update_date ui_passwd_update_date,ui.user_profile ui_user_profile,ui.user_status ui_user_status,ui.profile_url ui_profile_url,ui.avatar_url ui_avatar_url,ui.access_token ui_access_token,ui.notice_id ui_notice_id,ui.registered_dtm ui_registered_dtm,ui.updated_dtm ui_updated_dtm,ui.created_dtm ui_created_dtm ";
	
	static final public String PLANT_INFO_LIST = " ,li.id li_id,li.loc_type li_loc_type,li.size_x li_size_x,li.size_y li_size_y,li.size_z li_size_z,li.parent_loc_id li_parent_loc_id ";
	static final public String LOCATION_INFO_LIST = " ,li.id li_id,li.loc_type li_loc_type,li.size_x li_size_x,li.size_y li_size_y,li.size_z li_size_z ";
	static final public String AREA_INFO_LIST = ",ai.id ai_id, ai.area_code ai_area_code, ai.area_name ai_area_name, ai.plant_id ai_plant_id, ai.size_x ai_size_x, ai.size_y ai_size_y, ai.size_z ai_size_z, ai.description ai_description";
	static final public String FACILITY_INFO_LIST = ",fi.id fi_id, fi.facil_code fi_facil_code, fi.facil_name fi_facil_name, fi.area_id fi_area_id, fi.description fi_description";
	static final public String INTERIOR_INFO_LIST = ", ii.id ii_id, ii.interior_code ii_interior_code , ii.interior_name ii_interior_name, ii.area_id ii_area_id, ii.pos_x ii_pos_x, ii.pos_y ii_pos_y, ii.pos_z ii_pos_z, ii.size_x ii_size_x, ii.size_y ii_size_y, ii.size_z ii_size_z, ii.description ii_description";
	static final public String SIZE_INFO_LIST = " ,si.id si_id,si.loc_type si_loc_type,si.size_x si_size_x,si.size_y si_size_y,si.size_z si_size_z ";
	static final public String DIRECTION_INFO_LIST = " ,di.id di_id,di.loc_type di_loc_type,di.size_x di_size_x,di.size_y di_size_y,di.size_z di_size_z ";
	static final public String IOT_DEVICE_INFO_LIST = ",idi.id idi_id,idi.device_id idi_device_id,idi.device_type idi_device_type,idi.area_id idi_area_id,idi.pos_x idi_pos_x,idi.pos_y idi_pos_y,idi.pos_z idi_pos_z,idi.updated_dtm idi_updated_dtm,idi.created_dtm idi_created_dtm";
	static final public String ETC_DEVICE_INFO_LIST ="  ,edi.id edi_id,edi.device_id edi_device_id,edi.device_type edi_device_type,edi.device_name edi_device_name,edi.device_serno edi_device_serno,edi.device_desc edi_device_desc,edi.area_id edi_area_id,edi.pos_x edi_pos_x,edi.pos_y edi_pos_y,edi.pos_z edi_pos_z,edi.dir_x edi_dir_x,edi.dir_y edi_dir_y,edi.dir_z edi_dir_z,edi.register_date edi_register_date,edi.install_date edi_install_date,edi.term_date edi_term_date,edi.updated_dtm edi_updated_dtm,edi.created_dtm edi_created_dtm ";
	static final public String IOT_RULE_INFO_LIST =" ,iri.id, iri.iot_rule_code, iri.iot_rule_name, iri.data_type, iri.interior_id, iri.critic_val, iri.rule_desc, iri.tip_desc, iri.updated_dtm, iri.created_dtm ";
	static final public String FACIL_RULE_INFO_LIST =" ,fri.id, fri.facil_rule_code, fri.facil_rule_name, fri.data_type, fri.facility_id, fri.critic_val, fri.rule_desc, fri.tip_desc, fri.updated_dtm, fri.created_dtm ";
	static final public String FACIL_TAG_INFO_LIST = " ,fti.id, fti.tag_name, fti.facility_id, fti.data_type, fti.tag_desc, fti.tag_style, fti.updated_dtm, fti.created_dtm ";
	static final public String FACIL_TAG_MAPPING_LIST = " ,ftp.facil_rule_id, ftp.tag_id";
	static final public String EVENT_TIME_LINE_LIST = " ,etl.id, etl.reason_cd, etl.reason_id, etl.event_type, etl.event_msg, etl.updated_dtm, etl.created_dtm";
			
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
	
	static public LocationInfo toLocationInfo(ResultSet resultSet, String prefix) throws SQLException {
		LocationInfo obj = new LocationInfo();
		if (resultSet.getLong(prefix+"ID")==0) return null;
		obj.setId(resultSet.getLong(prefix+"ID"));	
		obj.setLocType(resultSet.getString(prefix+"LOC_TYPE"));	
		obj.setSizeX(resultSet.getFloat(prefix+"SIZE_X"));	
		obj.setSizeY(resultSet.getFloat(prefix+"SIZE_Y"));	
		obj.setSizeZ(resultSet.getFloat(prefix+"SIZE_Z"));	
		obj.setParentLocId(resultSet.getLong(prefix+"PARENT_LOC_ID"));
		return obj;
	}

	static public LocationInfo toLocationInfo(ResultSet resultSet) throws SQLException {
		return toLocationInfo(resultSet, null);
	}
	
	static public PlantInfo toPlantInfo(ResultSet resultSet, String prefix) throws SQLException {
		PlantInfo obj = new PlantInfo();
		if (resultSet.getLong(prefix+"ID")==0) return null;
		obj.setId(resultSet.getLong(prefix+"ID"));
		obj.setPlantNo(resultSet.getString(prefix+"PLANT_NO"));
		obj.setPlantName(resultSet.getString(prefix+"PLANT_NAME"));
		obj.setDescription(resultSet.getString(prefix+"DESCRIPTION"));
		return obj;
	}

	static public PlantInfo toPlantInfo(ResultSet resultSet) throws SQLException {
		return toPlantInfo(resultSet, "");
	}

	static public AreaInfo toAreaInfo(ResultSet resultSet, String prefix) throws SQLException {
		AreaInfo obj = new AreaInfo();
		if (resultSet.getLong(prefix+"ID")==0) return null;
		obj.setId(resultSet.getLong(prefix+"ID"));
		obj.setAreaCode(resultSet.getString(prefix+"AREA_CODE"));
		obj.setAreaName(resultSet.getString(prefix+"AREA_NAME"));
		obj.setPlantId(resultSet.getLong(prefix+"PLANT_ID"));
		obj.setSizeX(resultSet.getFloat("SIZE_X"));
		obj.setSizeY(resultSet.getFloat("SIZE_Y"));
		obj.setSizeZ(resultSet.getFloat("SIZE_Z"));
		obj.setDescription(resultSet.getString(prefix+"DESCRIPTION"));
		return obj;
	}
	static public AreaInfo toAreaInfo(ResultSet resultSet) throws SQLException {
		return toAreaInfo(resultSet, "");
	}	

	static public FacilityInfo toFacilityInfo(ResultSet resultSet) throws SQLException {
		FacilityInfo obj = new FacilityInfo();
		obj.setId(resultSet.getLong("ID"));
		obj.setFacilCode(resultSet.getString("FACIL_CODE"));
		obj.setFacilName(resultSet.getString("FACIL_NAME"));
		obj.setAreaId(resultSet.getLong("AREA_ID"));
		obj.setDescription(resultSet.getString("DESCRIPTION"));

		return obj;
	}

	static public InteriorInfo toInteriorInfo(ResultSet resultSet) throws SQLException {
		InteriorInfo obj = new InteriorInfo();
		obj.setId(resultSet.getLong("ID"));
		obj.setInteriorCode (resultSet.getString("INTERIOR_CODE"));
		obj.setInteriorName(resultSet.getString("INTERIOR_NAME"));
		obj.setAreaId(resultSet.getLong("AREA_ID"));
		obj.setPosX(resultSet.getFloat("POS_X"));
		obj.setPosY(resultSet.getFloat("POS_Y"));
		obj.setPosZ(resultSet.getFloat("POS_Z"));
		obj.setSizeX(resultSet.getFloat("SIZE_X"));
		obj.setSizeY(resultSet.getFloat("SIZE_Y"));
		obj.setSizeZ(resultSet.getFloat("SIZE_Z"));
		obj.setDescription(resultSet.getString("DESCRIPTION"));

		return obj;
	}

	static public IotDeviceInfo toIotDeviceInfo(ResultSet resultSet) throws SQLException {
		IotDeviceInfo obj = new IotDeviceInfo();
		obj.setId(resultSet.getLong("ID"));
		obj.setDeviceId(resultSet.getString("DEVICE_ID"));
		obj.setDeviceType(resultSet.getString("DEVICE_TYPE"));
		obj.setInteriorId(resultSet.getLong("INTERIOR_ID"));
		obj.setPosX(resultSet.getFloat("POS_X"));
		obj.setPosY(resultSet.getFloat("POS_Y"));
		obj.setPosZ(resultSet.getFloat("POS_Z"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));

		return obj;
	}
	
	static public EtcDeviceInfo toEtcDeviceInfo(ResultSet resultSet) throws SQLException {
		EtcDeviceInfo obj = new EtcDeviceInfo();
		obj.setId(resultSet.getLong("ID"));
		obj.setDeviceId(resultSet.getString("DEVICE_ID"));
		obj.setDeviceType(resultSet.getString("DEVICE_TYPE"));
		obj.setDeviceName(resultSet.getString("DEVICE_NAME"));
		obj.setDeviceSerno(resultSet.getString("DEVICE_SERNO"));
		obj.setDeviceDesc(resultSet.getString("DEVICE_DESC"));
		obj.setInteriorId(resultSet.getLong("INTERIOR_ID"));
		obj.setPosX(resultSet.getFloat("POS_X"));
		obj.setPosY(resultSet.getFloat("POS_Y"));
		obj.setPosZ(resultSet.getFloat("POS_Z"));
		obj.setDirX(resultSet.getFloat("DIR_X"));
		obj.setDirY(resultSet.getFloat("DIR_Y"));
		obj.setDirZ(resultSet.getFloat("DIR_Z"));
		obj.setRegisterDate(resultSet.getString("REGISTER_DATE"));
		obj.setInstallDate(resultSet.getString("INSTALL_DATE"));
		obj.setTermDate(resultSet.getString("TERM_DATE"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));

		return obj;
	}

	static public IotRuleInfo toIotRuleInfo(ResultSet resultSet) throws SQLException {
		IotRuleInfo obj = new IotRuleInfo();
		
		obj.setId(resultSet.getLong("ID"));
		obj.setIotRuleCode(resultSet.getString("IOT_RULE_CODE"));
		obj.setIotRuleName(resultSet.getString("IOT_RULE_NAME"));
		obj.setDataType(resultSet.getString("DATA_TYPE"));
		obj.setInteriorId(resultSet.getLong("INTERIOR_ID"));
		obj.setCriticVal(resultSet.getFloat("CRITIC_VAL"));
		obj.setRuleDesc(resultSet.getString("RULE_DESC"));
		obj.setTipDesc(resultSet.getString("TIP_DESC"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));

		return obj;
	}
	
	static public FacilRuleInfo toFacilRuleInfo(ResultSet resultSet) throws SQLException {
		FacilRuleInfo obj = new FacilRuleInfo();
		
		obj.setId(resultSet.getLong("ID"));
		obj.setFacilRuleCode(resultSet.getString("FACIL_RULE_CODE"));
		obj.setFacilRuleName(resultSet.getString("FACIL_RULE_NAME"));
		obj.setDataType(resultSet.getString("DATA_TYPE"));
		obj.setFacilityId(resultSet.getLong("FACILITY_ID"));
		obj.setCriticVal(resultSet.getFloat("CRITIC_VAL"));
		obj.setRuleDesc(resultSet.getString("RULE_DESC"));
		obj.setTipDesc(resultSet.getString("TIP_DESC"));
		obj.setUpdatedDtm(Utils.DtToStr(resultSet.getTimestamp("UPDATED_DTM")));
		obj.setCreatedDtm(Utils.DtToStr(resultSet.getTimestamp("CREATED_DTM")));

		return obj;
	}
	
	static public FacilTagInfo toFacilTagInfo(ResultSet resultSet) throws SQLException {
		FacilTagInfo obj = new FacilTagInfo();
		
		obj.setId(resultSet.getLong("ID"));
		obj.setTagName(resultSet.getString("TAG_NAME"));
		obj.setFacilityId(resultSet.getLong("FACILITY_ID"));
		obj.setDataType(resultSet.getString("DATA_TYPE"));
		obj.setTagDesc(resultSet.getString("TAG_DESC"));
		obj.setTagStyle(resultSet.getString("TAG_STYLE"));
		obj.setUpdatedDtm(resultSet.getString("UPDATED_DTM"));
		obj.setCreatedDtm(resultSet.getString("CREATED_DTM"));

		return obj;
	}
	
	static public FacilTagMapping toFacilTagMapping(ResultSet resultSet) throws SQLException {
		FacilTagMapping obj = new FacilTagMapping();
		
		obj.setFacilRuleId(resultSet.getLong("FACIL_RULE_ID"));
		obj.setTagId(resultSet.getLong("TAG_ID"));

		return obj;
	}

	static public EventTimeLine toEventTimeLine(ResultSet resultSet) throws SQLException {
		EventTimeLine obj = new EventTimeLine();
		
		obj.setId(resultSet.getLong("ID"));
		obj.setReasonCd(resultSet.getString("REASON_CD"));
		obj.setReasonId(resultSet.getLong("REASON_ID"));
		obj.setEventType(resultSet.getString("EVENT_TYPE"));
		obj.setEventMsg(resultSet.getString("EVENT_MSG"));
		obj.setUpdatedDtm(resultSet.getString("UPDATED_DTM"));
		obj.setCreatedDtm(resultSet.getString("CREATED_DTM"));

		return obj;
	}
}
