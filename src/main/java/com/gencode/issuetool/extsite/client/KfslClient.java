package com.gencode.issuetool.extsite.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.Charsets;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class KfslClient<T> {
	private final static Logger logger = LoggerFactory.getLogger(KfslClient.class);

	String token = null;
    public KfslClient() {
		super();
	}

	public KfslClient(String token) {
		super();
		this.token = token;
	}
	
	public T call(String url, Map<String, String > map, Type type) {
		logger.info("url:"+url);
    	logger.info("body:"+map);
    	String resultJson = post(url, map);
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
    
//    private static String getFormDataAsString(Map<String, String> formData) throws UnsupportedEncodingException {
//    	StringBuilder formBodyBuilder = new StringBuilder();
//    	for (Map.Entry<String, String> singleEntry: formData.entrySet()) {
//    		if (formBodyBuilder.length() > 0) {
//    			formBodyBuilder.append("&");
//    		}
//    		formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8.toString()));
//    		formBodyBuilder.append("=");
//    		formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8.toString()));
//    	}
//    	return formBodyBuilder.toString();
//    }
    public String post(String urlStr, Map<String, String> param) {
		try {
	        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
	     // Add parameters
	        MultipartContent content = new MultipartContent().setMediaType(
	                new HttpMediaType("multipart/form-data")
	                        .setParameter("boundary", "__END_OF_PART__"));
	        for (String name : param.keySet()) {
	            MultipartContent.Part part = new MultipartContent.Part(
	                    new ByteArrayContent(null, param.get(name).getBytes()));
	            part.setHeaders(new HttpHeaders().set(
	                    "Content-Disposition", String.format("form-data; name=\"%s\"", name)));
	            content.addPart(part);
	        }
	        
			OnePlannerUrl url = new OnePlannerUrl(urlStr);
			HttpRequest request = requestFactory.buildPostRequest(url, 
					content);
			if (token != null) {
				request.getHeaders().setAuthorization("Bearer " + token);
			}
			return request.execute().parseAsString();
//			String result = new String(request.execute().parseAsString().getBytes("UTF-8"),"UTF-8");
//			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    

}