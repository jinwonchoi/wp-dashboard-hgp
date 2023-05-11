package com.gencode.issuetool.unittest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gencode.issuetool.client.KfslClient;
import com.gencode.issuetool.client.LogWrapper;
import com.gencode.issuetool.client.RestClient;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.FakeDataUtil;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.extsite.io.TotalResultReqObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.logpresso.io.DvcEventReqObj;
import com.gencode.issuetool.logpresso.io.IotDataByDeviceReqObj;
import com.gencode.issuetool.logpresso.io.IotDataReqObj;
import com.gencode.issuetool.logpresso.io.IotFireIdxReqObj;
import com.gencode.issuetool.logpresso.io.IotMainReqObj;
import com.gencode.issuetool.logpresso.io.TagDataByTagReqObj;
import com.gencode.issuetool.logpresso.io.TagDataReqObj;
import com.gencode.issuetool.logpresso.io.TagFireIdxReqObj;
import com.gencode.issuetool.logpresso.obj.DashboardObj;
import com.gencode.issuetool.obj.AuthUserInfo;
import com.gencode.issuetool.obj.IotData;
import com.gencode.issuetool.obj.IotMain;
import com.gencode.issuetool.obj.TagData;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.service.LogpressoAPIService;
import com.gencode.issuetool.util.JsonUtils;
import com.google.gson.reflect.TypeToken;

public class KfslRunner {
	LogWrapper logger = new LogWrapper(KfslRunner.class);
	
//	@Test
//	public void doRunTableList() throws IOException {
//		 Logpresso client = null;
//		 Cursor cursor = null;
//		 
//		 try {
//		        client = new Logpresso();
//		        client.connect("file.rozetatech.com", 8888, "root", "rozeta5308!");
//		        cursor = client.query("system tables | fields table");
//		 
//		        while (cursor.hasNext()) {
//		                System.out.println(cursor.next());
//		        }
//		 } finally {
//		        if (cursor != null)
//		                cursor.close();
//		 
//		        if (client != null)
//		                client.close();
//		 }
//	}
	@Test
	public void runTotalResult() {
		try {
			KfslClient kfslClient = new KfslClient<String>();
			//String url = String.format("%s?EVALUATIONLIST_CODE=%s&START_EVALUATION_DATE=%s&END_EVALUATION_DATE=%s", apiUrl, "100", "2023-01-01","2023-02-01");
			///TotalResultReqObj reqObj = new TotalResultReqObj("100");
			String apiUrl="http://dt.rozetatech.com:89/Api/getTotalResult";
			Map<String, Object> result = (Map<String, Object>) kfslClient.call(apiUrl, new HashMap<String, String>(){{
				put("EVALUATIONLIST_CODE","100");
//				put("START_EVALUATION_DATE","2023-02-01");
//				put("END_EVALUATION_DATE","2023-02-01");
			}}, Map.class);
			logger.info(JsonUtils.toJson(result).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void runSpaceResult() {
		try {
			KfslClient kfslClient = new KfslClient<String>();
			//String url = String.format("%s?EVALUATIONLIST_CODE=%s&START_EVALUATION_DATE=%s&END_EVALUATION_DATE=%s", apiUrl, "100", "2023-01-01","2023-02-01");
			///TotalResultReqObj reqObj = new TotalResultReqObj("100");
			String apiUrl="http://dt.rozetatech.com:89/Api/getSpaceResult";
			Map<String, Object> result = (Map<String, Object>) kfslClient.call(apiUrl, new HashMap<String, String>(){{
				put("EVALUATIONLIST_CODE","100");
				put("AREA_CODE","10006");
//				put("START_EVALUATION_DATE","2023-02-01");
//				put("END_EVALUATION_DATE","2023-02-01");
			}}, Map.class);
			logger.info(JsonUtils.toJson(result).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void runEventPush() {
		try {
			KfslClient kfslClient = new KfslClient<String>();
			//String url = String.format("%s?EVALUATIONLIST_CODE=%s&START_EVALUATION_DATE=%s&END_EVALUATION_DATE=%s", apiUrl, "100", "2023-01-01","2023-02-01");
			///TotalResultReqObj reqObj = new TotalResultReqObj("100");
			String apiKey="K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6";
			String apiUrl="http://dt.rozetatech.com:3000/hg/api/event/ws/test";
			Map<String, Object> result = (Map<String, Object>) kfslClient.call(apiUrl, new HashMap<String, String>(){{
				put("type", "C");
				put("level", "F");
				put("id","ST40000C01");
				put("kind","FIRE");
				put("value","불꽃");
				put("apikey",apiKey);
			}}, Map.class);
			logger.info(JsonUtils.toJson(result).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @Test
    public void testUTF() throws UnsupportedEncodingException {
    	//String str = "F30 \ucd08\ub3d9\ub300\uc751";
    	String str="\"F20 \\uac10\uc9c0\\/\uacbd\\\\ubcf4"; 
    	//String str = "{\"result\":true,\"data\":[{\"EVALUATION_DATE\":\"2023-02-03\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"66.6\",\"SAFETY_TYPE1\":\"70.8\",\"SAFETY_TYPE2\":\"62.6\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/604\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"70.8\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"67.8\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"64.6\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"55.3\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"56.1\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]},{\"EVALUATION_DATE\":\"2023-01-02\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"66.6\",\"SAFETY_TYPE1\":\"70\",\"SAFETY_TYPE2\":\"63.3\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/595\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"70\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"69.9\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"64.6\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"55.3\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"56.1\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]},{\"EVALUATION_DATE\":\"2022-12-20\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"66.2\",\"SAFETY_TYPE1\":\"70\",\"SAFETY_TYPE2\":\"62.6\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/586\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"70\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"67.8\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"64.6\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"55.3\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"56.1\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]},{\"EVALUATION_DATE\":\"2022-10-20\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"66.2\",\"SAFETY_TYPE1\":\"70\",\"SAFETY_TYPE2\":\"62.6\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/577\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"70\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"67.8\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"64.6\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"55.3\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"56.1\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]},{\"EVALUATION_DATE\":\"2022-08-30\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"66.2\",\"SAFETY_TYPE1\":\"70\",\"SAFETY_TYPE2\":\"62.6\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/568\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"70\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"67.8\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"64.6\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"55.3\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"56.1\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]},{\"EVALUATION_DATE\":\"2022-06-30\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"66.2\",\"SAFETY_TYPE1\":\"70\",\"SAFETY_TYPE2\":\"62.6\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/559\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"70\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"67.8\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"64.6\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"55.3\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"56.1\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]},{\"EVALUATION_DATE\":\"2022-04-30\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"66.2\",\"SAFETY_TYPE1\":\"70\",\"SAFETY_TYPE2\":\"62.6\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/550\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"70\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"67.8\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"64.6\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"55.3\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"56.1\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]},{\"EVALUATION_DATE\":\"2022-01-30\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"68.6\",\"SAFETY_TYPE1\":\"71.6\",\"SAFETY_TYPE2\":\"65.8\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/541\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"71.6\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"67.8\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"65.9\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"68\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"58.6\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]},{\"EVALUATION_DATE\":\"2021-11-30\",\"EVALUATION\":\"FSA-T\",\"SAFETY_GRADE\":\"B\",\"SAFETY_SCORE\":\"66.2\",\"SAFETY_TYPE1\":\"70\",\"SAFETY_TYPE2\":\"62.6\",\"REPORT_URL\":\"http:\\/\\/dt.rozetatech.com:89\\/report\\/writeReport\\/6\\/505\\/4\",\"STEP1_RESULT\":[{\"STEP1_NAME\":\"F10 \\ud654\\uc7ac\\uc608\\ubc29\",\"STEP1_JISU\":\"70\"},{\"STEP1_NAME\":\"F20 \\uac10\\uc9c0\\/\\uacbd\\ubcf4\",\"STEP1_JISU\":\"67.8\"},{\"STEP1_NAME\":\"F30 \\ucd08\\ub3d9\\ub300\\uc751\",\"STEP1_JISU\":\"64.6\"},{\"STEP1_NAME\":\"F40 \\uc18c\\ud654\\uc124\\ube44\",\"STEP1_JISU\":\"55.3\"},{\"STEP1_NAME\":\"F50 \\uacf5\\uacf5\\uc18c\\ubc29\\ub300\",\"STEP1_JISU\":\"56.1\"},{\"STEP1_NAME\":\"F60 \\uac74\\ucd95\\ubc29\\uc7ac\",\"STEP1_JISU\":\"51.8\"}]}]}";
    	byte[] arr = str.getBytes("UTF-8");
    	System.out.println(new String(arr, "UTF-8"));
    }

}


//java -jar C:\Users\jinno\.m2\repository\logpresso\logpresso-sdk-java\1.0.1\logpresso-sdk-java-1.0.1.jar -e "system tables | fields table" -h "file.rozetatech.com" -P "8888" -u "root" -p "rozeta5308!"

//java -jar C:\Users\jinno\Downloads\logpresso-sdk-java-1.0.0-package.jar -e "system tables | fields table" -h "file.rozetatech.com" -P "8888" -u "root" -p "rozeta5308!"