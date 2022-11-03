package com.gencode.issuetool.etc;

public enum Constant {
	DEFAULT_USER_NUM("defaultnum"),
	STR_DEFAULT_USER_NUM("default user number"),
	CALLING_NUM("callingNum"),
	FILE_PATH("filePath"),
	EXPIRED_DATE("expiredDate"),
	DURATION_TYPE("durationType"),
	USER_NUM("userNum"),
	CALLING_NAME("callingName"),
	REGISTER_DATE("registerDate"),
	JSON_MSG("jsonMsg"),
	DOWNLOAD_CNT("downloadCnt"),
	UPDATE_DATE("updateDate"),
	
	DEFAULT_PASSWORD("password"),
	
	DEFAULT_DURATION_TYPE("defaultDurationType"),
	DEFAULT_JSON_MESSAGE("defaultJsonMessage"),
	DEFAULT_EXPIRED_DATE("defaultExpiredDate"),
	JSON_MESSAGE("jsonMessage"),
	
	MAIL_REG_ACTIVATION_TITLE("IssueTool 가입 안내"),
	MAIL_USERID_NOTIFICATION_TITLE("IssueTool 사용자ID 안내"),
	MAIL_PASSWORD_NOTIFICATION_TITLE("IssueTool 임시비밀번호 안내"),
	
	CALENDAR_TYPE_GOOGLE("G"),
	CALENDAR_TYPE_FACEBOOK("F"),
	CALENDAR_TYPE_NAVER("N"),
	CALENDAR_TYPE_DAUM("D"),
	CALENDAR_VIEW_TYPE_BIWEEK("B"),
	CALENDAR_VIEW_TYPE_MONTHLY("M"),
	CALENDAR_VIEW_TYPE_JOBS("J"),
	FREQUENCY_PERIOD_MONTH("M"),
	FREQUENCY_PERIOD_WEEK("W"),
	FREQUENCY_PERIOD_DAY("D"),
	START_WEEK_SUNDAY("S"),
	START_WEEK_MONDAY("M"),
	TASK_TYPE_GOAL("G"),
	TASK_TYPE_ROUTINE("R"),
	USER_TYPE_GOOGLE("G"),
	USER_TYPE_FACEBOOK("F"),
	USER_TYPE_NORMAL("N"),
	
	FILE_TYPE_IMAGE("G"),
	FILE_TYPE_PLAY("P"), //embeded play용 파일
	FILE_TYPE_NORMAL("N"),
	MIME_TYPE_IMAGE("image"),
	
	ALBUM_DEFAULT_NAME("%s의 앨범"),
	ALBUM_DEFAULT_TAG("%s"),
	ALBUM_DEFAULT_DESC("%s의 앨범"),
	ALBUM_EXPOSE_MODE_DEFAULT("0"),//hidden
	ALBUM_EXPOSE_MODE_ME("1"), //me
	ALBUM_EXPOSE_MODE_ALL("2"), //all
	
	
	LOGIN_HISTORY_NORMAL_LOGOUT("N"),
	LOGIN_HISTORY_NORMAL_EXIT("X"),
	LOGIN_HISTORY_NORMAL_TIMEOUT("O"),
	
	CHAT_CONSULT_DETAILS_CONSULT_STATUS_OPEN("O"),//open
	CHAT_CONSULT_DETAILS_CONSULT_STATUS_CLOSE("C"),//close
	CHAT_CONSULT_DETAILS_CONSULT_STATUS_REJECT("R"),//반려처리
	CHAT_CONSULT_DETAILS_CONSULT_STATUS_PASS("P"),//타상담원 이관
	
	CHAT_CONSULT_DETAILS_CONSULT_TYPE_BASIC("B"),//단순문의
	CHAT_CONSULT_DETAILS_CONSULT_TYPE_COMPLAIN("C"),//불만접수
	CHAT_CONSULT_DETAILS_CONSULT_TYPE_AS("A"),//AS요청
	
	MESSAGE_LOG_DIRECTION_INBOUND("I"),
	MESSAGE_LOG_DIRECTION_OUTBOUND("O"),
	
	MESSAGE_QUEUE_DIRECTION_INBOUND("I"),
	MESSAGE_QUEUE_DIRECTION_OUTBOUND("O"),
	
	MESSAGE_QUEUE_STATUS_NON("N"), //상담원미지정
	MESSAGE_QUEUE_STATUS_OFFLINE("F"), //상담원 offline
	MESSAGE_QUEUE_STATUS_SEND_WAIT("S"), //전송대기
	MESSAGE_QUEUE_STATUS_READ_WAIT("R"), //읽기대기
	MESSAGE_QUEUE_STATUS_COMPLETE("C"), //확인
	
	NOTICE_BOARD_POST_TYPE_NORMAL("N"),//일반공지
	NOTICE_BOARD_POST_TYPE_SCHEDULE("S"),//일정공지
	NOTICE_BOARD_POST_TYPE_URGENT("U"),//긴급공지
	NOTICE_BOARD_POST_TYPE_WORK("W"),//업무공지
	
	NOTICE_BOARD_POST_LEVEL_NORMAL(0),
	NOTICE_BOARD_POST_LEVEL_REPLY(1),
	
	FILE_REFERENCE_REF_TYPE_ADDFILE("F"),
	FILE_REFERENCE_REF_TYPE_NORMAL("N"),
	FILE_REFERENCE_REF_TYPE_PROFILE("P"),
	FILE_REFERENCE_REF_TYPE_MANUAL("M"),
	
	FILE_REFERENCE_STATUS_COMPLETE("C"),
	FILE_REFERENCE_STATUS_NON("N"),
	
	FILE_INFO_STATUS_COMPLETE("C"),
	FILE_INFO_STATUS_NON("N"),
	
	FILE_INFO_FILE_TYPE_PROFILE("P"),
	FILE_INFO_FILE_TYPE_EMBED("E"),
	FILE_INFO_FILE_TYPE_ATTACHED("A"),
	
	USER_INFO_REGISTERED("R"),
	USER_INFO_CONFIRMED("C"),
	USER_INFO_ACTIVATED("A"),
	USER_INFO_DEACTIVATED("D"),
	
	LANG_EN("en"),
	LANG_KO("ko"),
	LANG_DEFAULT("ko"),
	/* mail auth message box */
	AUTH_RESULT_TITLE("authResultTitle"),
	AUTH_RESULT_MESSAGE("authResultMessage"),
	AUTH_RESULT_BUTTON("authResultButton"),
	AUTH_SUCCESS_TITLE_KO("등록 승인 완료"),
	AUTH_SUCCESS_TITLE_EN("Your subscription confirmed"),
	AUTH_FAIL_TITLE_KO("등록 승인 실패"),
	AUTH_FAIL_TITLE_EN("Your subscription failed"),
	AUTH_SUCCESS_MSG_KO("등록 요청이 승인되었습니다.<br/>one planner서비스를 하용해주셔서 감사합니다."),
	AUTH_SUCCESS_MSG_EN("Your subscription request is just confirmed.<br/>Thank you for using one planner service."),
	AUTH_FAIL_MSG_KO("등록 요청이  실해하였습니다.<br/>다시 등록 신청 부탁드립니다."),
	AUTH_FAIL_MSG_EN("Your subscription request is just failed.<br/>Please subscribe your account again."),
	AUTH_SUCCESS_BUTTON_KO("One Planner 열기"),
	AUTH_SUCCESS_BUTTON_EN("Open One Planner"),
	AUTH_FAIL_BUTTON_KO("One Planner 열기"),
	AUTH_FAIL_BUTTON_EN("Open One Planner"),

	/* 화재방호 대시보드*/
	WP_DEFAULT_MAIN("default_main"),
	WP_FACIL_MAIN_9("facil_main_9"),
	WP_FACIL_MAIN_10("facil_main_10"),
	WP_IOT_MAIN_9("iot_main_9"),
	WP_IOT_MAIN_10("iot_main_10"),
	
	
	/* push messsage */
	PUSH_TAG_NOTICE_BOARD_ADD("noticeBoard.add"),
	PUSH_TAG_NOTICE_BOARD_DELETE("noticeBoard.delete"),
	PUSH_TAG_NOTICE_BOARD_UPDATE("noticeBoard.update"),
	PUSH_TAG_PLANT_INFO_ADD("plantInfo.add"),
	PUSH_TAG_PLANT_INFO_DELETE("plantInfo.delete"),
	PUSH_TAG_PLANT_INFO_UPDATE("plantInfo.update"),
	PUSH_TAG_AREA_INFO_ADD("areaInfo.add"),
	PUSH_TAG_AREA_INFO_DELETE("areaInfo.delete"),
	PUSH_TAG_AREA_INFO_UPDATE("areaInfo.update"),
	PUSH_TAG_PLANT_PART_INFO_ADD("plantPartInfo.add"),
	PUSH_TAG_PLANT_PART_INFO_DELETE("plantPartInfo.delete"),
	PUSH_TAG_PLANT_PART_INFO_UPDATE("plantPartInfo.update"),
	PUSH_TAG_FACILITY_INFO_ADD("facilityInfo.add"),
	PUSH_TAG_FACILITY_INFO_DELETE("facilityInfo.delete"),
	PUSH_TAG_FACILITY_INFO_UPDATE("facilityInfo.update"),
	PUSH_TAG_INTERIOR_INFO_ADD("interiorInfo.add"),
	PUSH_TAG_INTERIOR_INFO_DELETE("interiorInfo.delete"),
	PUSH_TAG_INTERIOR_INFO_UPDATE("interiorInfo.update"),
	PUSH_TAG_IOT_DEVICE_INFO_ADD("iotDeviceInfo.add"),
	PUSH_TAG_IOT_DEVICE_INFO_DELETE("iotDeviceInfo.delete"),
	PUSH_TAG_IOT_DEVICE_INFO_UPDATE("iotDeviceInfo.update"),
	PUSH_TAG_ETC_DEVICE_INFO_ADD("etcDeviceInfo.add"),
	PUSH_TAG_ETC_DEVICE_INFO_DELETE("etcDeviceInfo.delete"),
	PUSH_TAG_ETC_DEVICE_INFO_UPDATE("etcDeviceInfo.update"),

	PUSH_TAG_IOT_RULE_INFO_ADD("iotRuleInfo.add"),
	PUSH_TAG_IOT_RULE_INFO_DELETE("iotRuleInfo.delete"),
	PUSH_TAG_IOT_RULE_INFO_UPDATE("iotRuleInfo.update"),

	PUSH_TAG_FACIL_RULE_INFO_ADD("facilRuleInfo.add"),
	PUSH_TAG_FACIL_RULE_INFO_DELETE("facilRuleInfo.delete"),
	PUSH_TAG_FACIL_RULE_INFO_UPDATE("facilRuleInfo.update"),

	PUSH_TAG_FACIL_TAG_INFO_ADD("facilTagInfo.add"),
	PUSH_TAG_FACIL_TAG_INFO_DELETE("facilTagInfo.delete"),
	PUSH_TAG_FACIL_TAG_INFO_UPDATE("facilTagInfo.update"),

	PUSH_TAG_FACIL_TAG_MAPPING_ADD("facilTagMapping.add"),
	PUSH_TAG_FACIL_TAG_MAPPING_DELETE("facilTagMapping.delete"),
	PUSH_TAG_FACIL_TAG_MAPPING_DELETE_BY_RULE_ID("facilTagMapping.deleteByRuleId"),
	PUSH_TAG_FACIL_TAG_MAPPING_UPDATE("facilTagMapping.update"),
	
	PUSH_TAG_TAG_DVC_PUSH_EVENT_ADD("tagDvcPushEvent.add"),
	
	/* chat, board */ 
	PUSH_TAG_PROTOTYPE_NOTICE_BOARD_ADD("prototype.noticeBoard.add"),
	PUSH_TAG_PROTOTYPE_NOTICE_BOARD_DELETE("prototype.noticeBoard.delete"),
	PUSH_TAG_PROTOTYPE_NOTICE_BOARD_UPDATE("prototype.noticeBoard.update"),
	PUSH_TAG_PROTOTYPE_BOARD_COMMENT_ADD("prototype.boardComment.add"),
	PUSH_TAG_PROTOTYPE_BOARD_COMMENT_DELETE("prototype.boardComment.delete"),
	PUSH_TAG_PROTOTYPE_BOARD_COMMENT_UPDATE("prototype.boardComment.update"),
	PUSH_TAG_PROTOTYPE_CHAT_ADD("prototype.chat.add"),
	PUSH_TAG_PROTOTYPE_CHAT_DELETE("prototype.chat.delete"),
	PUSH_TAG_PROTOTYPE_CHAT_UPDATE("prototype.chat.update"),
	PUSH_TAG_PROTOTYPE_CHATMSG_RESET("prototype.chat.reset"),
	PUSH_TAG_PROTOTYPE_CHATMSG_ADD("prototype.chatmsg.add"),
	PUSH_TAG_PROTOTYPE_CHATMSG_DELETE("prototype.chatmsg.delete"),
	PUSH_TAG_PROTOTYPE_CHATMSG_UPDATE("prototype.chatmsg.update"),
	PUSH_TAG_PROTOTYPE_CHATMSG_STATUS("prototype.chatmsg.status"),

	DASHBOARD_VALUE_DECIAML_SIZE(100),
	
	DASHBOARD_STATS_TIME_MODE_1MONTH("1m"),
	DASHBOARD_STATS_TIME_MODE_1DAY("1D"),
	DASHBOARD_STATS_TIME_MODE_1HOUR("1H"),
	DASHBOARD_STATS_TIME_MODE_1MINUTE("1M"),

	IOT_VALUE_NAME_TEMP("온도(℃)"),
	IOT_VALUE_NAME_HUMID("습도(%)"),
	IOT_VALUE_NAME_SMOKE("연기농도"),
	IOT_VALUE_NAME_CO("CO농도"),
	IOT_VALUE_NAME_FLARE("불꽃"),
	
	/*  */
	IOT_DEVICE_TIME("time"),
	IOT_DEVICE_INDEX("INDEX"),
	IOT_DEVICE_VALUES("VALUES"),
	IOT_DEVICE_HUMID("HUMID"),
	IOT_DEVICE_SMOKE("SMOKE"),
	IOT_DEVICE_TEMPERATURE("TEMPERATURE"),
	IOT_DEVICE_COGAS("COgas"),
	IOT_DEVICE_FLARE("FLARE"),
	;
	private Constant(String str) {
		value = str;
	}
	
	private Constant(int val) {
		intValue = val;
	}
	
	private int intValue;
	private String value;
	
	public boolean equalValues(String str) {
		return (str == null)?false:value.equals(str);
	}
	
	public String get() {
		return value;
	}
	
	public int val() {
		return intValue;
	}
}
