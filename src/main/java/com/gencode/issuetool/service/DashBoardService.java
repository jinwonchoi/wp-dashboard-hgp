package com.gencode.issuetool.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.dao.NoticeBoardDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.LogpressoConnector;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.logpresso.obj.DashBoardObj;
import com.gencode.issuetool.obj.GroupSum;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.StatsGoal;
import com.gencode.issuetool.obj.StatsPerDay;
import com.logpresso.client.Cursor;
import com.logpresso.client.Tuple;

@Service
public class DashBoardService {
	private final static Logger logger = LoggerFactory.getLogger(DashBoardService.class);

	@Autowired
	private NoticeBoardDao noticeBoardDao;

	public DashBoardObj getDashboardDataAll() throws IOException {
		DashBoardObj arResult = new DashBoardObj();
		
		arResult.setDefaultMain(getDashboardData("proc sp_dsMain1()"));
		arResult.setFacilMain09(getDashboardData("proc sp_dsSub11()"));
		arResult.setFacilMain10(getDashboardData("proc sp_dsSub12()"));
		
		return arResult;
	}

	public DashBoardObj getDashboardDataIot() throws IOException {
		DashBoardObj arResult = new DashBoardObj();
		
		arResult.setIotMain09(getDashboardDataList("proc sp_dsSub21()"));
		arResult.setIotMain10(getDashboardDataList("proc sp_dsSub22()"));
		
		return arResult;
	}

	
	String getDashboardData(String strCmd) throws IOException {
		
		LogpressoConnector conn = null;
		Cursor cursor = null;
		try {
			String strResult="";
			conn = new LogpressoConnector();
			//cursor = conn.executeQuery("proc sp_dsmain()");
			cursor = conn.executeQuery(strCmd);
			//cursor = conn.executeQuery("table if_wth_fcst");
	        while (cursor.hasNext()) {
	        	Tuple tuple = cursor.next();
	        	//logger.info(tuple.toString());
	        	strResult = tuple.get("_return").toString();
	        	//logger.info(strResult);
	        }
	        return strResult; 
		} finally {
	        if (cursor != null)
			cursor.close();
		    if (conn != null)
		    	conn.close();
		}
	}
	List<String> getDashboardDataList(String strCmd) throws IOException {
		
		LogpressoConnector conn = null;
		Cursor cursor = null;
		try {
			List<String> arResult= new ArrayList<String>();
			conn = new LogpressoConnector();
			//cursor = conn.executeQuery("proc sp_dsmain()");
			cursor = conn.executeQuery(strCmd);
			//cursor = conn.executeQuery("table if_wth_fcst");
	        while (cursor.hasNext()) {
	        	Tuple tuple = cursor.next();
	        	//logger.info(tuple.get("_return").toString());
	        	arResult.add(tuple.get("_return").toString());
	        }
	        return arResult; 
		} finally {
	        if (cursor != null)
			cursor.close();
		    if (conn != null)
		    	conn.close();
		}
	}

}
