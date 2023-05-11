package com.gencode.issuetool.unittest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gencode.issuetool.client.LogWrapper;
import com.gencode.issuetool.client.RestClient;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.FakeDataUtil;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.logpresso.io.DvcEventReqObj;
import com.gencode.issuetool.logpresso.io.IotDataByDeviceReqObj;
import com.gencode.issuetool.logpresso.io.IotDataReqObj;
import com.gencode.issuetool.logpresso.io.IotFireIdxReqObj;
import com.gencode.issuetool.logpresso.io.IotMainReqObj;
import com.gencode.issuetool.logpresso.io.TagDataByTagReqObj;
import com.gencode.issuetool.logpresso.io.TagDataReqObj;
import com.gencode.issuetool.logpresso.io.TagFireIdxReqObj;
import com.gencode.issuetool.logpresso.obj.DashboardObj;
import com.gencode.issuetool.obj.AuthUserInfo;
import com.gencode.issuetool.obj.IotData;
import com.gencode.issuetool.obj.IotMain;
import com.gencode.issuetool.obj.TagData;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.service.LogpressoAPIService;
import com.gencode.issuetool.util.JsonUtils;
import com.google.gson.reflect.TypeToken;

public class LogpressoRunner {
	LogWrapper logger = new LogWrapper(LogpressoRunner.class);
	
//	@Test
//	public void doRunTableList() throws IOException {
//		 Logpresso client = null;
//		 Cursor cursor = null;
//		 
//		 try {
//		        client = new Logpresso();
//		        client.connect("file.rozetatech.com", 8888, "root", "rozeta5308!");
//		        cursor = client.query("system tables | fields table");
//		 
//		        while (cursor.hasNext()) {
//		                System.out.println(cursor.next());
//		        }
//		 } finally {
//		        if (cursor != null)
//		                cursor.close();
//		 
//		        if (client != null)
//		                client.close();
//		 }
//	}
		
	String apiKey="K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6";
	String apiUrl="http://dt.rozetatech.com:3000/hg/api";
	
	@Test
	public void runIotMainAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		//logger.info(FakeDataUtil.generateIotMain());
		try {
			arResult.setIotMain(FakeDataUtil.getMapIotMain(logpressoAPIService.getIotMain(new IotMainReqObj("ST4,ST3,SC3,SC2,SC1,SU1,SI2,SI1,SI0","5s"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void runIotMainListAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		//logger.info(FakeDataUtil.generateIotMain());
		try {
			List<IotMain> t = FakeDataUtil.getListIotMain(logpressoAPIService.getIotMain(new IotMainReqObj("ST4,ST3,SC3,SC2,SC1,SU1,SI2,SI1,SI0","5s")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void runIotDataAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		//arResult.setIotData(FakeDataUtil.getMapIotData(FakeDataUtil.generateIotData()));
		try {
			arResult.setIotData(FakeDataUtil.getMapIotData(logpressoAPIService.getIotData(new IotDataReqObj("ST4,ST3,SC3,SC2,SC1,SU1,SI2,SI1,SI0","5s"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(arResult.toString());
	}
	
	@Test
	public void runIotDataListAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		//arResult.setIotData(FakeDataUtil.getMapIotData(FakeDataUtil.generateIotData()));
		try {
			List<IotData> t = FakeDataUtil.getListIotData(logpressoAPIService.getIotData(new IotDataReqObj("ST4,ST3,SC3,SC2,SC1,SU1,SI2,SI1,SI0","5s")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(arResult.toString());
	}

	/*
	 * 	String interiorList;//"ST4,ST3,SC3,SC2,SC1,SU1,SI2,SI1,SI0"
	String plantNoList="M1,M2,MC";
	String plantPartList="FP,MAA,MAV,MAB,MAK,MKD";
	String areaCodeList="TURBIN,MCTRL,UNDERCBL,INVERTER";
	 */
	@Test
	public void runTagFireIdxAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		try {
//			String strTagFireIdx = FakeDataUtil.generateTagFireIdx();
//			logger.info(strTagFireIdx);
//			arResult.setTagFireIdx(FakeDataUtil.getMapTagFireIdx(
//					strTagFireIdx
//				)); //설비별화재지수
			arResult.setTagFireIdx(FakeDataUtil.getMapTagFireIdx(
					logpressoAPIService.getTagFireIdx(new TagFireIdxReqObj("TBN,BFP","M1,M2,MC","5s"))
				));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //설비별화재지수
		logger.info(arResult.toString());
	}
	
	@Test
	public void runIotFireIdxAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		try {
//			String strIotFireIdx = FakeDataUtil.generateIotFireIdx();
//			logger.info(strIotFireIdx);
//			arResult.setIotFireIdx(FakeDataUtil.getMapIotFireIdx(
//					strIotFireIdx
//				)); //공간별화재지수
			
			arResult.setIotFireIdx(FakeDataUtil.getMapIotFireIdx(null,
					logpressoAPIService.getIotFireIdx(new IotFireIdxReqObj(
							//"ST4"
							"ST3,SC3,SC2,SC1,SU1,SI2,SI1,SI0"
							,"5s"))
				)); //공간별화재지수
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //설비별화재지수
		logger.info(arResult.toString());
	}
	
	@Test
	public void runTotalFireIdxAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		try {
//			String strIotFireIdx = FakeDataUtil.generateIotFireIdx();
//			logger.info(strIotFireIdx);
//			arResult.setIotFireIdx(FakeDataUtil.getMapIotFireIdx(
//					strIotFireIdx
//				)); //공간별화재지수
			
			arResult.setIotFireIdx(FakeDataUtil.getMapIotFireIdx(null,
					logpressoAPIService.getIotFireIdx(new IotFireIdxReqObj(
							//"ST4"
							"ST3,SC3,SC2,SC1,SU1,SI2,SI1,SI0"
							,"5s"))
				)); //공간별화재지수
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //설비별화재지수
		logger.info(arResult.toString());
	}
	
	
	@Test
	public void runTagMainAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		
		try {
			
			arResult.setTagMain(FakeDataUtil.getMapTagData(FakeDataUtil.generateTagData())); //설비별위험현황
			logger.info(FakeDataUtil.generateTagData());
			arResult.setTagMain(FakeDataUtil.getMapTagData(
					logpressoAPIService.getTagData(
							new TagDataReqObj("BFP,TBN",
									"M1,M2,MC",
									"FP,MAA,MAV,MAB,MAK,MKD",
									"5m"
									))
					)); //설비별위험현황
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //설비별화재지수
		logger.info(arResult.toString());
	}

	
	@Test
	public void testInt() {
		String aa="1.0";
		int b=(int)Double.parseDouble(aa);
		System.out.println(b);
		
		System.out.println(((int)Double.parseDouble((String)"1.0")));
//		System.out.println((int)(Double.parseDouble((String)mapData.get("val"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()));
//								((int)Double.parseDouble((String)mapData.get("seq"))),
		//(int)(Double.parseDouble((String)mapData.get("val"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),

		//{val=0.05, tagname=1MAA10CG005XE01, max_val=0.05, time=2023-01-04 08:22:26.731, seq=1.0, status=}
	}
	
	@Test
	public void runTagMainListAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		
		try {
			
//			List<TagData> t0=FakeDataUtil.getListTagData(FakeDataUtil.generateTagData()); //설비별위험현황
//			logger.info(FakeDataUtil.generateTagData());
//			logger.info(t0.toString());
			List<TagData> t = FakeDataUtil.getListTagData(
					logpressoAPIService.getTagData(
							new TagDataReqObj("TBN",
									"M1,M2,MC",
									"FP,MAA,MAV,MAB,MAK,MKD",
									"5m"
									))
					); //설비별위험현황
			logger.info(t.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //설비별화재지수
		logger.info(arResult.toString());
	}
	
	@Test
	public void runTabDvcEventAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		boolean _useFake = false;
		try {
			if (_useFake) {
				logger.info(FakeDataUtil.generateTagDvcEvent("I,P,C"));
			} else {
				logger.info(JsonUtils.toJson(FakeDataUtil.getListTagDvcEvent(
						//FakeDataUtil.generateTagDvcEvent()
						logpressoAPIService.getTagDvcEvent(
								new DvcEventReqObj("I,P,C",
									Utils.yyyyMMddHHmmssHypen(Utils.addMinutesToDate(new Date(), -10)),
									Utils.yyyyMMddHHmmssHypen(),
									"00"
								)
						)))); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //설비별화재지수
		logger.info(arResult.toString());
	}


	@Test
	public void runIotDataByDeviceAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		boolean _useFake = false;
		try {
			if (_useFake) {
				//logger.info(FakeDataUtil.generateTagDvcEvent());
			} else {
				logger.info(
						//FakeDataUtil.generateTagDvcEvent()
						logpressoAPIService.getIotDataByDevice(
								new IotDataByDeviceReqObj("SC10101N01")
						).toString()); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //설비별화재지수
		logger.info(arResult.toString());
	}

	@Test
	public void runDataByTagAPI() {
		DashboardObj arResult = new DashboardObj();
		LogpressoAPIService logpressoAPIService= new LogpressoAPIService();
		boolean _useFake = false;
		try {
			if (_useFake) {
				//logger.info(FakeDataUtil.generateTagDvcEvent());
			} else {
				logger.info(
						//FakeDataUtil.generateTagDvcEvent()
						logpressoAPIService.getTagDataByTag(
								new TagDataByTagReqObj("2MAK10CT005XE01")
						).toString()); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //설비별화재지수
		logger.info(arResult.toString());
	}

	
	@Test 
	public void trivial() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE,  -10);
		System.out.println(calendar.getTime().toString());

	}
}


//java -jar C:\Users\jinno\.m2\repository\logpresso\logpresso-sdk-java\1.0.1\logpresso-sdk-java-1.0.1.jar -e "system tables | fields table" -h "file.rozetatech.com" -P "8888" -u "root" -p "rozeta5308!"

//java -jar C:\Users\jinno\Downloads\logpresso-sdk-java-1.0.0-package.jar -e "system tables | fields table" -h "file.rozetatech.com" -P "8888" -u "root" -p "rozeta5308!"