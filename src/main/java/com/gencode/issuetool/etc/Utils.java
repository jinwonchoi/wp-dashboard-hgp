package com.gencode.issuetool.etc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.h2.util.DateTimeUtils;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gencode.issuetool.obj.FileInfo;

public class Utils {
	public static String inItems(List<String> itemList) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		int i = 0;
		for (String user : itemList) {
			if (i == 0)
				sb.append("'"+user+"'");
			else
				sb.append(",'"+user+"'");
			i++;						
		}
		if (i ==  0) {
			sb.append("'')");
		} else {
			sb.append(")");
		}
		return sb.toString();
	}
	
    public static String getBase64String(String str) {
        byte[] bytesEncoded = Base64Utils.encode(str.getBytes());
        String encodedStr = new String(bytesEncoded).replaceAll("(?:\\r\\n|\\n\\r|\\n|\\r)", "");
        System.out.println("Encoded value: "+encodedStr);
        return encodedStr;
    }
    
	public static String getBase64StringRecover(String str) {
    	if (str.length() % 4 != 0) 
    		str += new String(new char[(4 - str.length() % 4)]).replace('\0','=');
    	return str;
    }
	
	public static String getRandomPassword() {
    	return RandomStringUtils.randomAlphanumeric(8);
    }

	public static String getRandomNumber() {
    	return RandomStringUtils.randomNumeric(4);
    }

	public static <T> String getListToString(List<T> theList) {
		StringBuffer  sb =  new StringBuffer();
		for (T item : theList) {
			sb.append("["+item.toString()+"]");
		}
		return sb.toString();
	}

	public static String YYYYMMDD(){
		return new java.text.SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	public static String yyyyMMddHHmmss(){
		return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	public static String yyyyMMddHHmmssSSS(){
		return new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	public static String YYYYMMDD(Date _date){
		return new java.text.SimpleDateFormat("yyyyMMdd").format(_date);
	}
	public static String yyyyMMddHHmmss(Date _date){
		return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(_date);
	}
	public static String yyyyMMddHHmmssSSS(Date _date){
		return new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(_date);
	}
	public static String YYYYMMDDWithHypen(Date _date){
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(_date);
	}
	
	public static String fileNameByTimestamp() {
		return Long.toString(System.currentTimeMillis());
	}
	
	static DateTimeFormatter DTFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");//yyyy-MM-dd HH:mm:ss.SS
	public static String DtToStr(LocalDateTime lDt) {
		return lDt.format(DTFORMATTER);
		//return lDt.format(DateTimeFormatter.ISO_DATE_TIME);//Formatted LocalDateTime : 2018-07-14T17:45:55.9483536
	}
	
	/**
	 * 참고: 
	 * https://stackoverflow.com/questions/38063851/mysql-datetime-and-timestamp-to-java-sql-timestamp-to-zoneddatetime
	 * @param ts
	 * @return
	 */
	public static String DtToStr(java.sql.Timestamp ts) {
		return ts==null? null:ZonedDateTime.ofInstant(ts.toInstant(), ZoneId.of("Z")).format(DTFORMATTER);
		//return ts.toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME);//Formatted LocalDateTime : 2018-07-14T17:45:55.9483536
	}

	public static LocalDateTime StrToDt(String strDateTime) {
		//"2018-07-14T17:45:55.9483536";
		return LocalDateTime.parse(strDateTime, DTFORMATTER/* DateTimeFormatter.ISO_DATE_TIME */);
	}
	
	
	public static String unCamel(String str) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return str.replaceAll(regex, replacement)
                           .toLowerCase();
	}
	
	public static <T> String objToStr(T t) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(t);
	}
	
	public static <T> T strToObj(String str, Class<T> type) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(str, type);
	}
	
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        return getFileExtension(fileName);
    }

    public static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    public static String getFileMimeType(File file) throws IOException {
		return Files.probeContentType(file.toPath());
    }
    
	public static void delete(String path, String fileName) throws IOException {
		Path file = Paths.get(path, fileName);
		Files.delete(file);
	}
}
