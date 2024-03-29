package com.gencode.issuetool.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.util.GsonUtils;
import com.gencode.issuetool.util.JsonUtils;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Key;
import com.google.api.client.util.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LogpressoClient<T> {
	LogWrapper logger = new LogWrapper(LogpressoClient.class);

	String token = null;
    public LogpressoClient() {
		super();
	}

	public LogpressoClient(String token) {
		super();
		this.token = token;
	}
	
	public T callLogpresso(String url, Object jsonObj, Type type) {
    	String body = JsonUtils.toJson(jsonObj);

		logger.info("url:"+url);
    	logger.info("body:"+body);
    	String resultJson = post(url, body);
    	logger.info("resultJson:"+resultJson);

    	//JSONObject jsonResult = new JSONObject(resultJson);
    	
    	Gson gson = GsonUtils.GetGson();
		T result = (T)gson.fromJson(resultJson, type);
    	//ResultObj<T> result = (ResultObj<T>)JsonUtils.toObject(resultJson, type.getClass());
		logger.info("result = "+result.toString());
    	return result;
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