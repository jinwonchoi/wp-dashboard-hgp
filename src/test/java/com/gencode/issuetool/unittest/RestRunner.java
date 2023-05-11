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

public class RestRunner {
	LogWrapper logger = new LogWrapper(RestRunner.class);
	
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
	public void runEventPush() {
		try {
			String apiKey="K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6";
			String apiUrl="http://dt.rozetatech.com:3000/hg/api/event/ws/test";
			RestClient restClient = new RestClient<String>();
			//String url = String.format("%s?EVALUATIONLIST_CODE=%s&START_EVALUATION_DATE=%s&END_EVALUATION_DATE=%s", apiUrl, "100", "2023-01-01","2023-02-01");
			///TotalResultReqObj reqObj = new TotalResultReqObj("100");
			String body = JsonUtils.toJson(new HashMap<String, String>() {{
				put("type", "C");
				put("level", "F");
				put("id","ST40000C01");
				put("kind","FIRE");
				put("value","불꽃");
				put("apikey",apiKey);
			}});
			
			String result = (String) restClient.post(apiUrl, body);
			logger.info(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


//java -jar C:\Users\jinno\.m2\repository\logpresso\logpresso-sdk-java\1.0.1\logpresso-sdk-java-1.0.1.jar -e "system tables | fields table" -h "file.rozetatech.com" -P "8888" -u "root" -p "rozeta5308!"

//java -jar C:\Users\jinno\Downloads\logpresso-sdk-java-1.0.0-package.jar -e "system tables | fields table" -h "file.rozetatech.com" -P "8888" -u "root" -p "rozeta5308!"