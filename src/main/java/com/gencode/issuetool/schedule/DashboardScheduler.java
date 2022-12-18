/**=========================================================================================
<overview>대시보드 데이터처리 스케줄러
  </overview>
==========================================================================================*/
package com.gencode.issuetool.schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gencode.issuetool.service.DashboardService;

@Component
public class DashboardScheduler {
	private final static Logger logger = LoggerFactory.getLogger(DashboardScheduler.class);

	@Value("${logpresso.polling-time:3000}") 
	int pollingTime; //milliseconds

	
	int cnt=0;
	@Autowired
	DashboardService dashboardService;
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Scheduled(fixedRateString="${logpresso.polling-time:3000}")
	public void processDashboardDataHourly() {
		logger.info("DASH BOARD SCHEDULER RUN HOURLY:");
		try {
			dashboardService.addTagFireIdx();
			dashboardService.cleanseTagFireIdx();
		} catch (Exception e) {
			logger.error("Scheduler error", e);
		}
	}

	@Scheduled(fixedRateString="${logpresso.polling-time:3000}")
	public void processDashboardDataHourly2() {
		logger.info("DASH BOARD SCHEDULER RUN HOURLY:");
		try {
			dashboardService.addIotFireIdx();
			dashboardService.cleanseIotFireIdx();
		} catch (Exception e) {
			logger.error("Scheduler error", e);
		}
	}

	@Scheduled(fixedRateString="${logpresso.polling-time:3000}")
	public void collectDashboardTagDataPeriodic() {
		logger.info("DASH BOARD SCHEDULER RUN PERIODIC:"+pollingTime+" ADD TAG DATA");
		try {
			//TagData
			dashboardService.addTagData();
			dashboardService.cleanseTagData();
//			//IotData
			dashboardService.addIotData();
			dashboardService.cleanseIotData();
//			//IotMain
			dashboardService.addIotMain();
			dashboardService.cleanseIotMain();
		} catch (Exception e) {
			logger.error("Scheduler error", e);
		}
	}

	@Scheduled(cron="0 */10 * * * *")
	public void generateTagDvcPushEvent() {
		logger.info("DASH BOARD SCHEDULER RUN MINUTE: TAG DVC PUSH");
		try {
			dashboardService.addTagDvcPushEventHist();		
		} catch (Exception e) {
			logger.error("Scheduler error", e);
		}
	}
	
	@Scheduled(cron="0 * * * * *")
	public void generateDashboardTagDataStats() {
		logger.info("DASH BOARD SCHEDULER RUN MINUTE: GEN TAG DATA STAT");
		try {
			dashboardService.genTagFireIdxHistStats();
			dashboardService.genIotFireIdxHistStats();
			dashboardService.genTagDataHistStats();
			dashboardService.genIotDataHistStats();		
			dashboardService.genIotMainHistStats();		
		} catch (Exception e) {
			logger.error("Scheduler error", e);
		}
	}

}
