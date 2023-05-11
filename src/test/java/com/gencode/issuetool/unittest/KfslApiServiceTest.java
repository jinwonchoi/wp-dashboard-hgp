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
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.extsite.obj.KfslResultObj;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.io.chart.RealtimeChartObj;
import com.gencode.issuetool.logpresso.obj.DashboardObj;
import com.gencode.issuetool.service.DashboardService;
import com.gencode.issuetool.service.KfslAPIService;

@SpringBootTest
public class KfslApiServiceTest {
	LogWrapper logger = new LogWrapper(KfslApiServiceTest.class);
	
	@Autowired
	KfslAPIService kfslAPIService;
	
	@DisplayName("TEST GetTotalResult")
	@Test
	void testGetTotalResult() throws Exception {
		KfslResultObj kfslResultObj = kfslAPIService.getTotalResultMap(null);
		logger.info(kfslResultObj.toString());
		//assertNotNull(chatService.l);
	}
	
	@DisplayName("TEST GetSpaceResult")
	@Test
	void testGetSpaceResult() throws Exception {
		Map<String, KfslResultObj> kfslResultMap = kfslAPIService.getSpaceResultMap(null);
		logger.info(kfslResultMap.toString());
		//assertNotNull(chatService.l);
	}
	
}
