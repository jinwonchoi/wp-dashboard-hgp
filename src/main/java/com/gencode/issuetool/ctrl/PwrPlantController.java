package com.gencode.issuetool.ctrl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.service.DashBoardService;
import com.gencode.issuetool.service.MyUserDetailsService;
import com.gencode.issuetool.util.GsonUtils;
import com.gencode.issuetool.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logpresso.client.Cursor;
import com.logpresso.client.Tuple;
import com.gencode.issuetool.config.JwtTokenProvider;
import com.gencode.issuetool.etc.LogpressoConnector;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.io.StompObj;
import com.gencode.issuetool.logpresso.obj.DashBoardObj;
import com.gencode.issuetool.obj.AuthUserInfo;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.GroupSum;
import com.gencode.issuetool.obj.IotSensorData;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.StatsGoal;
import com.gencode.issuetool.obj.StatsPerDay;
import com.gencode.issuetool.obj.UserInfo;

@RestController
@RequestMapping("/pwrplant")
@CrossOrigin(origins = "${cors_url}")
public class PwrPlantController {

	private final static Logger logger = LoggerFactory.getLogger(PwrPlantController.class);
	
	@Autowired
	private DashBoardService dashBoardService;
	
	
	/* 사업부 사용자 인입 건수
	 * @return StatsPerDay 
	 *
	 */    
	@RequestMapping("/dashboard/all") 
	ResultObj<DashBoardObj> getDashboardAll() {

		LogpressoConnector conn = null;
		Cursor cursor = null;
		try {
			DashBoardObj dashBoardObj = new DashBoardObj();
			dashBoardObj = dashBoardService.getDashboardDataAll();
	        ResultObj<DashBoardObj> resultObj = ResultObj.success();
	        //logger.info(dashBoardObj.toString());
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (IOException e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		} finally {
			try {
		        if (cursor != null)
				cursor.close();
			    if (conn != null)
			    	conn.close();
			} catch (IOException e) {}
		}
	}

	/* 사업부 사용자 인입 건수
	 * @return StatsPerDay 
	 *
	 */    
	@RequestMapping("/dashboard/iot") 
	ResultObj<DashBoardObj> getDashboardIot() {

		LogpressoConnector conn = null;
		Cursor cursor = null;
		try {
			DashBoardObj dashBoardObj = new DashBoardObj();
			dashBoardObj = dashBoardService.getDashboardDataIot();
	        ResultObj<DashBoardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (IOException e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		} finally {
			try {
		        if (cursor != null)
				cursor.close();
			    if (conn != null)
			    	conn.close();
			} catch (IOException e) {}
		}
	}

	@RequestMapping("/dashboard/iotpilot") 
	ResultObj<DashBoardObj> getDashboardIotPilot() {

		LogpressoConnector conn = null;
		Cursor cursor = null;
		try {
			DashBoardObj dashBoardObj = new DashBoardObj();
			dashBoardObj = dashBoardService.getDashboardDataIotPilot();
	        ResultObj<DashBoardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (IOException e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		} finally {
			try {
		        if (cursor != null)
				cursor.close();
			    if (conn != null)
			    	conn.close();
			} catch (IOException e) {}
		}
	}

	@RequestMapping(value="/dashboard/iotpilot/list", method=RequestMethod.POST) 
	PageResultObj<List<IotSensorData>> getDashboardIotPilotList(@RequestBody PageRequest req) {

		try {
	        Optional<PageResultObj<List<IotSensorData>>> list = dashBoardService.getDashboardDataIotPilotDetailList(req);
			if (list.isPresent()) {
				return new PageResultObj<List<IotSensorData>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<IotSensorData>>dataNotFound();
			}

		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<IotSensorData>>errorUnknown();
		}
	}
}
