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

import com.gencode.issuetool.util.GsonUtils;
import com.gencode.issuetool.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logpresso.client.Cursor;
import com.logpresso.client.Tuple;
import com.gencode.issuetool.config.JwtTokenProvider;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.exception.ApplicationException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.io.StompObj;
import com.gencode.issuetool.io.chart.ColumnChartObj;
import com.gencode.issuetool.io.chart.RealtimeChartObj;
import com.gencode.issuetool.logpresso.obj.DashboardObj;
import com.gencode.issuetool.obj.AuthUserInfo;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.GroupSum;
import com.gencode.issuetool.obj.IotData;
import com.gencode.issuetool.obj.IotDeviceInfo;
import com.gencode.issuetool.obj.IotSensorData;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.StatsGoal;
import com.gencode.issuetool.obj.StatsPerDay;
import com.gencode.issuetool.obj.TagDvcPushEvent;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.service.DashboardService;
import com.gencode.issuetool.service.LogpressoConnector;
import com.gencode.issuetool.service.MyUserDetailsService;

@RestController
@RequestMapping("/pwrplant")
@CrossOrigin(origins = "${cors_url}")
public class PwrPlantController {

	private final static Logger logger = LoggerFactory.getLogger(PwrPlantController.class);
	
	@Autowired
	private DashboardService dashBoardService;
	
//	@RequestMapping("/dashboard/all") 
//	ResultObj<DashboardObj> getDashboardAll() {
//		try {
//			DashboardObj dashBoardObj = new DashboardObj();
//			dashBoardObj = dashBoardService.getDashboardDataAll();
//	        ResultObj<DashboardObj> resultObj = ResultObj.success();
//	        //logger.info(dashBoardObj.toString());
//			resultObj.setItem(dashBoardObj);
//			return resultObj;
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//		} finally {
//		}
//	}

	@RequestMapping("/dashboard/total") 
	ResultObj<DashboardObj> getDashboardTotal() {

		try {
			DashboardObj dashBoardObj = new DashboardObj();
			dashBoardObj = dashBoardService.getDashboardTotal();
	        ResultObj<DashboardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	@RequestMapping("/dashboard/tagfireidx") 
	ResultObj<DashboardObj> getTagFireIdx() {

		try {
			DashboardObj dashBoardObj = new DashboardObj();
			dashBoardObj = dashBoardService.getTagFireIdx();
	        ResultObj<DashboardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, "/dashboard/tagfireidx/daily") 
	ResultObj<RealtimeChartObj> getTagFireIdxDaily(@RequestBody Map<String, String> map) {

		try {
			RealtimeChartObj dashBoardObj = new RealtimeChartObj();
			dashBoardObj = dashBoardService.getTagFireIdxDaily(map);
	        ResultObj<RealtimeChartObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/tagfireidx/cntOverStable") 
	ResultObj<ColumnChartObj> getTagFireIdxCntOverStable() {

		try {
			ColumnChartObj dashBoardObj = new ColumnChartObj();
			dashBoardObj = dashBoardService.getTagFireIdxCntOverStable();
	        ResultObj<ColumnChartObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/iotfireidx") 
	ResultObj<DashboardObj> getIotFireIdx() {

		try {
			DashboardObj dashBoardObj = new DashboardObj();
			dashBoardObj = dashBoardService.getIotFireIdx();
	        ResultObj<DashboardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/iotfireidx/daily") 
	ResultObj<RealtimeChartObj> getIotFireIdxDaily() {

		try {
			RealtimeChartObj dashBoardObj = new RealtimeChartObj();
			dashBoardObj = dashBoardService.getIotFireIdxDaily();
	        ResultObj<RealtimeChartObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/iotfireidx/cntOverStable") 
	ResultObj<ColumnChartObj> getIotFireIdxCntOverStable() {

		try {
			ColumnChartObj dashBoardObj = new ColumnChartObj();
			dashBoardObj = dashBoardService.getIotFireIdxCntOverStable();
	        ResultObj<ColumnChartObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/tagmain") 
	ResultObj<DashboardObj> getTagMain() {

		try {
			DashboardObj dashBoardObj = new DashboardObj();
			dashBoardObj = dashBoardService.getTagMain();
	        ResultObj<DashboardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	
	@RequestMapping("/dashboard/tagmain/realtime") 
	ResultObj<RealtimeChartObj> getTagDataRealTimeChart(@RequestBody PageRequest req) {

		try {
			RealtimeChartObj columnChartObj = new RealtimeChartObj();
			columnChartObj = dashBoardService.getTagDataRealTimeChart(req);
	        ResultObj<RealtimeChartObj> resultObj = ResultObj.success();
			resultObj.setItem(columnChartObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		}
		
	}

	@RequestMapping("/dashboard/tagmain/summary") 
	ResultObj<ColumnChartObj> getChartTagDataCntStats(@RequestBody PageRequest req) {

		try {
			ColumnChartObj columnChartObj = dashBoardService.getTagDataCntStatsChart(req);
	        ResultObj<ColumnChartObj> resultObj = ResultObj.success();
			resultObj.setItem(columnChartObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		}
		
	}
	
	@RequestMapping("/dashboard/iotdata") 
	ResultObj<DashboardObj> getIotData() {

		try {
			DashboardObj dashBoardObj = new DashboardObj();
			dashBoardObj = dashBoardService.getIotData();
	        ResultObj<DashboardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/iotmain") 
	ResultObj<DashboardObj> getIotMain() {

		try {
			DashboardObj dashBoardObj = new DashboardObj();
			dashBoardObj = dashBoardService.getIotMain();
	        ResultObj<DashboardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}

	@RequestMapping("/dashboard/iotdata/list") 
	ResultObj<List<IotData>> getIotDataList() {

		try {
			List<IotData> dashBoardObj = dashBoardService.getIotDataList();
	        ResultObj<List<IotData>> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/iotmain/tagdvc") 
	ResultObj<DashboardObj> getTagDvcEventList(@RequestBody PageRequest req) {

		try {
			DashboardObj dashBoardObj = new DashboardObj();
			dashBoardObj = dashBoardService.getTagDvcEventHistList(req);
	        ResultObj<DashboardObj> resultObj = ResultObj.success();
			resultObj.setItem(dashBoardObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/iotmain/tagdvcpush/search") 
	PageResultObj<List<TagDvcPushEvent>> getTagDvcPushEventList(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<TagDvcPushEvent>>> list = dashBoardService.getTagDvcPushEventHistList(req);
			if (list.isPresent()) {
				return new PageResultObj<List<TagDvcPushEvent>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<TagDvcPushEvent>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<TagDvcPushEvent>>errorUnknown();
		} finally {
		}
	}
	
	@RequestMapping("/dashboard/iotmain/realtime/overall") 
	ResultObj<RealtimeChartObj> getIotMainRealTimeChartOverall(@RequestBody PageRequest req) {

		try {
			RealtimeChartObj columnChartObj = new RealtimeChartObj();
			columnChartObj = dashBoardService.getIotMainRealTimeChartByAllInteriors(req);
	        ResultObj<RealtimeChartObj> resultObj = ResultObj.success();
			resultObj.setItem(columnChartObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		}
		
	}

	@RequestMapping("/dashboard/iotmain/summary/overall") 
	ResultObj<ColumnChartObj> getChartIotMainCntStatsOverall(@RequestBody PageRequest req) {

		try {
			ColumnChartObj columnChartObj = dashBoardService.getIotMainCntStatsChartOverall(req);
	        ResultObj<ColumnChartObj> resultObj = ResultObj.success();
			resultObj.setItem(columnChartObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		}
		
	}
	@RequestMapping("/dashboard/iotmain/realtime") 
	ResultObj<RealtimeChartObj> getIotMainRealTimeChart(@RequestBody PageRequest req) {

		try {
			RealtimeChartObj columnChartObj = new RealtimeChartObj();
			columnChartObj = dashBoardService.getIotMainRealTimeChartByInterior(req);
	        ResultObj<RealtimeChartObj> resultObj = ResultObj.success();
			resultObj.setItem(columnChartObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		}
		
	}

	@RequestMapping("/dashboard/iotmain/summary") 
	ResultObj<ColumnChartObj> getChartIotMainCntStats(@RequestBody PageRequest req) {

		try {
			ColumnChartObj columnChartObj = dashBoardService.getIotMainCntStatsChart(req);
	        ResultObj<ColumnChartObj> resultObj = ResultObj.success();
			resultObj.setItem(columnChartObj);
			return resultObj;
		} catch (Exception e) {
			logger.error("normal error",e);
			return ResultObj.errorUnknown();
		}
		
	}
}
