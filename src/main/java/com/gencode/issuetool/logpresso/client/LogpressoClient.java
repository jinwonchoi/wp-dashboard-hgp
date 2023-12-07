package com.gencode.issuetool.logpresso.client;

import java.io.IOException;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.gencode.issuetool.util.GsonUtils;
import com.gencode.issuetool.util.JsonUtils;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import com.google.gson.Gson;

public class LogpressoClient<T> {
	private final static Logger logger = LoggerFactory.getLogger(LogpressoClient.class);

	String token = null;
    public LogpressoClient() {
		super();
	}

	public LogpressoClient(String token) {
		super();
		this.token = token;
	}
	
	public T callLogpresso(String url, Object jsonObj, Type type, String printLog) {

		try {
			ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE); 
//	    	String body = objectMapper.writeValueAsString(JsonUtils.toJson(jsonObj));
			String body = objectMapper.writeValueAsString(jsonObj);
			logger.info("url:"+url);
	    	logger.info("body:"+body);
	    	String resultJson = post(url, body);
	    	logger.info("printLog="+printLog);
	    	if ("true".equals(printLog))
	    		logger.info("resultJson:"+resultJson);
	    	else
	    		logger.debug("resultJson:"+resultJson);

	    	//JSONObject jsonResult = new JSONObject(resultJson);
	    	
	    	Gson gson = GsonUtils.GetGson();
			T result = (T)gson.fromJson(resultJson, type);
	    	//ResultObj<T> result = (ResultObj<T>)JsonUtils.toObject(resultJson, type.getClass());
			logger.debug("result = "+result.toString());
	    	return result;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
	
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /** URL for Dailymotion API. */
    public static class OnePlannerUrl extends GenericUrl {

      public OnePlannerUrl(String encodedUrl) {
        super(encodedUrl);
      }

      @Key
      public String fields;
    }

    public String post(String urlStr, String body) {
		try {
	        HttpRequestFactory requestFactory =
	                HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
	                    @Override
	                  public void initialize(HttpRequest request) {
	                    request.setParser(new JsonObjectParser(JSON_FACTORY));
	                  }
	                });
			OnePlannerUrl url = new OnePlannerUrl(urlStr);
			HttpRequest request = requestFactory.buildPostRequest(url, ByteArrayContent.fromString("application/json", body));
			if (token != null) {
				request.getHeaders().setAuthorization("Bearer " + token);
			}
			return request.execute().parseAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}