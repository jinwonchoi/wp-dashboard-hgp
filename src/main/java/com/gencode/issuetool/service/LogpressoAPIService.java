package com.gencode.issuetool.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gencode.issuetool.logpresso.client.LogpressoClient;
import com.gencode.issuetool.logpresso.io.DvcEventReqObj;
import com.gencode.issuetool.logpresso.io.IotDataByDeviceReqObj;
import com.gencode.issuetool.logpresso.io.IotDataReqObj;
import com.gencode.issuetool.logpresso.io.IotFireIdxReqObj;
import com.gencode.issuetool.logpresso.io.IotInfoReqObj;
import com.gencode.issuetool.logpresso.io.IotMainReqObj;
import com.gencode.issuetool.logpresso.io.TagDataByTagReqObj;
import com.gencode.issuetool.logpresso.io.TagDataReqObj;
import com.gencode.issuetool.logpresso.io.TagFireIdxReqObj;
import com.gencode.issuetool.util.GsonUtils;
import com.gencode.issuetool.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class LogpressoAPIService {
	private final static Logger logger = LoggerFactory.getLogger(LogpressoAPIService.class);
	
	@Value("${logpresso.api.key:K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6}")
	String apiKey;//="K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6";
	@Value("${logpresso.api.url:http://dt.rozetatech.com:3000/hg/api}")
	String apiUrl;//="http://dt.rozetatech.com:3000/hg/api";

	public Map<String, List<Map<String, Object>>> getIotData(IotDataReqObj req) throws Exception{
		String urlPath =apiUrl+"/iot/iotdata";
		LogpressoClient<Map<String, List<Map<String, Object>>>> restClient = new LogpressoClient<Map<String, List<Map<String, Object>>>>();
		Type type = new TypeToken<Map<String, List<Map<String, Object>>>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, List<Map<String, Object>>> result = restClient.callLogpresso(urlPath,
				req, type);
		logger.debug(result.toString());
		return result;
	}

	public Map<String, List<Map<String, Object>>> getIotMain(IotMainReqObj req) throws Exception{
		String urlPath =apiUrl+"/iot/iotma";
		LogpressoClient<Map<String, List<Map<String, Object>>>> restClient = new LogpressoClient<Map<String, List<Map<String, Object>>>>();
		Type type = new TypeToken<Map<String, List<Map<String, Object>>>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, List<Map<String, Object>>> result = restClient.callLogpresso(urlPath,
				req, type);
		logger.debug(result.toString());
		return result;
	}

	public Map<String, List<Map<String, Object>>> getIotDataByDevice(IotDataByDeviceReqObj req) throws Exception{
		String urlPath =apiUrl+"/iot/iotdatabydevice";
		LogpressoClient<Map<String, List<Map<String, Object>>>> restClient = new LogpressoClient<Map<String, List<Map<String, Object>>>>();
		Type type = new TypeToken<Map<String, List<Map<String, Object>>>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, List<Map<String, Object>>> result = restClient.callLogpresso(urlPath,
				req, type);
		logger.debug(result.toString());
		return result;
	}

	public Map<String, List<Map<String, Object>>> getIotFireIdx(IotFireIdxReqObj req) throws Exception{
		String urlPath =apiUrl+"/iot/iotfireidx";
		LogpressoClient<Map<String, List<Map<String, Object>>>> restClient = new LogpressoClient<Map<String, List<Map<String, Object>>>>();
		Type type = new TypeToken<Map<String, List<Map<String, Object>>>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, List<Map<String, Object>>> result = restClient.callLogpresso(urlPath,
				req, type);
		//logger.info(GsonUtils.GetGson().toJson(result));
		logger.debug(result.toString());
		//return new HashMap<String, List<Map<String, Object>>>();
		return result;
	}

	public Map<String, Object> getTagData(TagDataReqObj req) throws Exception{
		String urlPath =apiUrl+"/tag/tagdata";
		logger.info("urlPath="+urlPath);
		LogpressoClient<Map<String, Object>> restClient = new LogpressoClient<Map<String, Object>>();
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, Object> result = restClient.callLogpresso(urlPath,
				req, type);
		logger.debug(JsonUtils.toJson(result));
		return result;
	}
	public Map<String, Object> getTagDataByTag(TagDataByTagReqObj req) throws Exception{
		String urlPath =apiUrl+"/tag/databytag";
		LogpressoClient<Map<String, Object>> restClient = new LogpressoClient<Map<String, Object>>();
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, Object> result = restClient.callLogpresso(urlPath,
				req, type);
		logger.debug(result.toString());
		return result;
	}

	public Map<String, Object> getTagFireIdx(TagFireIdxReqObj req) throws Exception{
		String urlPath =apiUrl+"/tag/tagfireidx";
		LogpressoClient<Map<String, Object>> restClient = new LogpressoClient<Map<String, Object>>();
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, Object> result = restClient.callLogpresso(urlPath,
				req, type);
		//logger.info(GsonUtils.GetGson().toJson(result));
		logger.debug(result.toString());
		return result;
		//return new HashMap<String, Object>();
	}
	
	public Map<String, Object> getTagDvcEvent(DvcEventReqObj req) throws Exception{
		String urlPath =apiUrl+"/event/dvcevent";
		LogpressoClient<Map<String, Object>> restClient = new LogpressoClient<Map<String, Object>>();
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, Object> result = restClient.callLogpresso(urlPath,
				req, type);
		logger.info(result.toString());
		return result;
	}
	
	public Map<String, Object> getIotInfo(IotInfoReqObj req) throws Exception{
		String urlPath =apiUrl+"/iot/iotinfo";
		LogpressoClient<Map<String, Object>> restClient = new LogpressoClient<Map<String, Object>>();
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		req.setApikey(apiKey);
		Map<String, Object> result = restClient.callLogpresso(urlPath,
				req, type);
		logger.info(result.toString());
		return result;
	}

}
