package com.gencode.issuetool.unittest;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gencode.issuetool.client.LogWrapper;
import com.gencode.issuetool.client.RestClient;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.io.chart.RealtimeChartObj;
import com.gencode.issuetool.logpresso.obj.DashboardObj;
import com.gencode.issuetool.obj.AuthUserInfo;
import com.gencode.issuetool.service.DashboardService;
import com.gencode.issuetool.util.JsonUtils;

@SpringBootTest
public class DashboardServiceTest {
	LogWrapper logger = new LogWrapper(DashboardServiceTest.class);
	
	@Autowired
	DashboardService dashboardService;
	
	@DisplayName("TEST getDashboardTotal")
	@Test
	void testGetDashboardTotal() throws Exception {
		DashboardObj dashboardObj = dashboardService.getDashboardTotal();
		logger.info(dashboardObj.toString());
		//assertNotNull(chatService.l);
	}
	
	@DisplayName("TEST getTagFireIdx")
	@Test
	void testGetTagFireIdx() throws Exception {
		DashboardObj dashboardObj = dashboardService.getTagFireIdx();
		logger.info(dashboardObj.toString());
		//assertNotNull(chatService.l);
	}

	@DisplayName("TEST addTagFireIdx")
	@Test
	void testAddTagFireIdx() throws Exception {
		dashboardService.addTagFireIdx();
		//assertNotNull(chatService.l);
	}
	
	@DisplayName("TEST addIotFireIdx")
	@Test
	void testAddIotFireIdx() throws Exception {
		dashboardService.addIotFireIdx();
		//assertNotNull(chatService.l);
	}
	//{\"pageNo\":1,\"pageSize\":1,\"lastOffset\":0,\"sortField\":\"\",\"sortDir\":\"DESC\",\"searchMap\":{},\"searchByOrMap\":{},\"paramMap\":{\"timeMode\":\"5S\",\"realtimeCount\":50}}
	//
	@DisplayName("TEST getTagFireIdxRealtimeChart")
	@Test
	void testGetTagFireIdxRealtimeChart() throws Exception {
		PageRequest req = new PageRequest();
		req.setPageNo(1);
		req.setPageSize(1);
		req.setLastOffset(0);
		req.setSortDir(SortDirection.DESC);
		req.setParamMap(new HashMap<String,String>(){{
			put("timeMode","5S");
			put("realtimeCount","50");
			
		}});
		RealtimeChartObj realtimeChartObj= dashboardService.getTagFireIdxRealtimeChart(req);
		//assertNotNull(chatService.l);
	}
	@DisplayName("TEST getIotMainRealtimeChartByInterior")
	@Test
	void getIotMainRealtimeChartByInterior() throws Exception {
		PageRequest req = new PageRequest();
		req.setPageNo(1);
		req.setPageSize(1);
		req.setLastOffset(0);
		req.setSortDir(SortDirection.DESC);
		req.setParamMap(new HashMap<String,String>(){{
			put("timeMode","5H");
			put("valType","max");
			put("fieldType","temp");			
			put("realtimeCount","50");
		}});
		req.setSearchMap(new HashMap<String,String>(){{
			put("interiorCode","S09T04");			
		}});
		RealtimeChartObj realtimeChartObj= dashboardService.getIotMainRealtimeChartByInterior(req);
		logger.info(realtimeChartObj.toString());
	}
	
	
	//{"pageNo":1,"pageSize":1,"offset":0,"limit":1,"lastOffset":0,"sortDir":"DESC","sortField":"","searchMap":{},"searchByOrMap":{},"paramMap":{"timeMode":"10S","valType":"max","mode":"area","realtimeCount":"50"},"paramValMap":null}
	@DisplayName("TEST getIotMainRealtimeChartListByInteriorList")
	@Test
	void getIotMainRealtimeChartListByInteriorList() throws Exception {
		PageRequest req = new PageRequest();
		req.setPageNo(1);
		req.setPageSize(1);
		req.setLastOffset(0);
		req.setLimit(1);
		req.setSortDir(SortDirection.DESC);
		req.setParamMap(new HashMap<String,String>(){{
			put("timeMode","10S");
			put("valType","max");
			put("mode","area");
			put("realtimeCount","50");
		}});
		 Map<String, RealtimeChartObj> realtimeChartObj= dashboardService.getIotMainRealtimeChartListByInteriorList(req);
		logger.info(realtimeChartObj.toString());
	}

	@DisplayName("TEST getTagMain")
	@Test
	void testGetTagMain() throws Exception {
		DashboardObj dashboardObj = dashboardService.getTagMain();
		logger.info(dashboardObj.toString());
		//assertNotNull(chatService.l);
	}

	@DisplayName("TEST addTagMain")
	@Test
	void testAddTagMain() throws Exception {
		dashboardService.addTagData();
		//assertNotNull(chatService.l);
	}

	@DisplayName("TEST getIotMain")
	@Test
	void testGetIotMain() throws Exception {
		DashboardObj dashboardObj = dashboardService.getIotMain();
		logger.info(dashboardObj.toString());
		//assertNotNull(chatService.l);
	}
	
	@DisplayName("TEST getIotMainArea")
	@Test
	void testGetIotMainArea() throws Exception {
		DashboardObj dashboardObj = dashboardService.getIotMainArea();
		logger.info(dashboardObj.toString());
		//assertNotNull(chatService.l);
	}
	
	@DisplayName("TEST getTagDvcEventHistList")
	@Test
	void testGetTagDvcEventHistList() throws Exception {
		PageRequest req = new PageRequest();
		req.setParamMap(new HashMap<String,String>(){{
			put("deviceType","P");
			put("fromDate",Utils.yyyyMMddHHmmssHypen(Utils.addHoursToDate(new Date(), -24*7)));
			put("fromDate",Utils.yyyyMMddHHmmssHypen());
			put("eventLevel","00");
		}});
		
		DashboardObj arResult = dashboardService.getTagDvcEventHistList(req);
		logger.info(arResult.getItemList());
		//assertNotNull(chatService.l);
	}
	
	@DisplayName("TEST testPushEvent")
	@Test
	void testPushEvent() throws Exception {
		dashboardService.testPushEvent();
	}
	
	
	@DisplayName("TEST getIotInfo")
	@Test
	void testGetIotInfo() throws Exception {
		dashboardService.getIotInfo();
	}
	
	
	
}
