package com.gencode.issuetool.etc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;
import org.h2.util.MathUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.IotSensorData;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.util.JsonUtils;

public class UtilsTest extends Utils {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		long a = 3;
		long b = 7;
		double c= BigDecimal.valueOf((float)a/b).round(new MathContext(2)).doubleValue();
		String d = "";
		System.out.println("a="+a);
		System.out.println("b="+b);
		System.out.println("c="+c);
		System.out.println("d="+d);
		
		fail("Not yet implemented");
	}
	
	@Test
	public void  testArray() {
		ArrayList<String>[] listData = (ArrayList<String>[])new ArrayList[5];		
		for(int i=0;i<5;i++) {
			listData[i]= new ArrayList<String>();
		}
	}

	@Test
	public void  test2DArray() {
		ArrayList<ArrayList<String>> listData = new ArrayList<ArrayList<String>>();
		
		for(int i=0;i<5;i++) {
			listData.add(new ArrayList<String>());
		}
		System.out.println(listData.size());
		System.out.println(listData.get(1));
		
	}
	
	@Test
	public void testForEach() {
		int i=0;
		System.out.println(String.format("%02d %03d", i, ++i));
		System.out.println(i++);
	}

	@Test
	public void testDateFunc() {
		Date prevDate = Utils.addHoursToDate(new Date(), -1);
		System.out.println(Utils.yyyyMMddHHmmss(prevDate));///.substring(0,10));
	}
	@Test
	public void testStrToObj() {
		ResultObj<UserInfo> resultObj = new ResultObj<UserInfo>();
		UserInfo userInfo  = new UserInfo("loginid", "passwd1");
		resultObj.setResultCode(ReturnCode.ALREADY_EXISTS_SCHEDULE.get());
		resultObj.setItem(userInfo);
		try {
			System.out.println(Utils.objToStr(resultObj));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testWeekList() {
		Map<String, Object> workDays = new HashMap<String, Object>();
		Date date = new Date();
		for (int i=0;i<7;i++) {
			System.out.println(Utils.YYYYMMDD(date)+":"+Utils.YYYYMMDD(DateUtils.addDays(date, -7+i)));
			workDays.put(Utils.YYYYMMDD(DateUtils.addDays(date, -7+i)), new Integer(0));
		}
		workDays.forEach((k,v) -> System.out.println(k+":"+workDays.get(k)));
	}
	@Test
	public void testRandomNumber() {
		Random random = new Random();
		
		System.out.println((new Long(random.longs(200, 500).findFirst().getAsLong())).doubleValue()/10);
		System.out.println(random.longs(200, 500).findFirst().getAsLong());
		System.out.println(random.longs(200, 500).findFirst().getAsLong());
		System.out.println(random.longs(200, 500).findFirst().getAsLong());
		System.out.println(random.longs(200, 500).findFirst().getAsLong());
//		System.out.println(random.longs(200, 500).toString());
//		System.out.println(random.longs(200, 500).toString());
//		System.out.println(random.longs(200, 500).toString());
//		System.out.println(random.longs(200, 500).toString());
//		System.out.println(random.longs(200, 500).toString());
	}
	
//	@Test
//	public void generateDsMain1() {
//		List<Map<String, String>> arJsonObjList = new ArrayList<Map<String, String>>();
//		for ( String key: FakeDataUtil.dsMain1List) {
//			arJsonObjList.add(new HashMap<String, String>(){{put(key, Double.toString(FakeDataUtil.tempVal()));}});
//		}
//		arJsonObjList.add(new HashMap<String, String>(){{put("time", Utils.YYYYMMDDWithHypen());}});
//		System.out.println(JsonUtils.toJson(arJsonObjList).toString()); 	
//	}
	
	@Test
	public void parseLogpressoResult() {
		String strResult = "[{\"time\":\"2021-08-29 17:34:02\", \"INDEX\":\"SN210101-2-1\", \"VALUES\": {\"HUMID\":\"0\", \"SMOKE\":\"25\", \"TEMPERATURE\":\"0\", \"COgas\":\"0\", \"FLARE\":\"정상\"}}]";
		
		List<Map<String, Object>> arJsonObjList = new ArrayList<Map<String, Object>>();
		arJsonObjList = JsonUtils.toObject(strResult, List.class);
		System.out.println(arJsonObjList.get(0));
		
		IotSensorData iotSensorData = new IotSensorData();
		Map<String, Object> arJsonObj = arJsonObjList.get(0);
		Map<String, String> valMap=(Map<String, String>)arJsonObj.get(Constant.IOT_DEVICE_VALUES.get());
		iotSensorData.setDeviceId((String)arJsonObj.get(Constant.IOT_DEVICE_INDEX.get()));
		iotSensorData.setCreatedDate((String)arJsonObj.get(Constant.IOT_DEVICE_TIME.get()));
		iotSensorData.setHumid(Double.parseDouble(valMap.get(Constant.IOT_DEVICE_HUMID.get())));
		iotSensorData.setSmoke(Double.parseDouble(valMap.get(Constant.IOT_DEVICE_SMOKE.get())));
		iotSensorData.setTemperature(Double.parseDouble(valMap.get(Constant.IOT_DEVICE_TEMPERATURE.get())));
		iotSensorData.setCoGas(Double.parseDouble(valMap.get(Constant.IOT_DEVICE_COGAS.get())));
		iotSensorData.setFlare(valMap.get(Constant.IOT_DEVICE_HUMID.get()));
		System.out.println(iotSensorData);
	}
	
	@Test
	public void generateIotData() {
//		System.out.println("===========================================================");
//		System.out.println("total fire idx 좋합화재지수");
//		System.out.println("===========================================================");
//		System.out.println(FakeDataUtil.generateTotalFireIdxData());
//		System.out.println("===========================================================");
//		System.out.println("tag fire idx 설비화재지수");
//		System.out.println("===========================================================");
//		System.out.println(FakeDataUtil.generateTagFireIdx());
//		System.out.println("===========================================================");
//		System.out.println("iot fire idx iot화재지수");
//		System.out.println("===========================================================");
//		System.out.println(FakeDataUtil.generateIotFireIdx());
//		System.out.println("===========================================================");
//		System.out.println("tag 데이터");
//		System.out.println("===========================================================");
//		System.out.println(FakeDataUtil.generateTagData());
//		System.out.println("===========================================================");
//		System.out.println("iot 데이터");
//		System.out.println("===========================================================");
//		System.out.println(FakeDataUtil.generateIotData());
//		System.out.println("===========================================================");
//		System.out.println("Iot 이벤트");
//		System.out.println("===========================================================");
//		System.out.println(FakeDataUtil.generateIotMa());
//		System.out.println("===========================================================");
//		System.out.println("이벤트");
//		System.out.println("===========================================================");
//		System.out.println(FakeDataUtil.generateDvcEvent());
//		String strOut = FakeDataUtil.generateTotalFireIdxData();
//		System.out.println(strOut);
//		System.out.println(FakeDataUtil.getMapTotalFireIdxData(strOut));

//		String strOut = FakeDataUtil.generateTagData();
//		System.out.println(strOut);
//		System.out.println(FakeDataUtil.getMapTagData(strOut));
		
//		String strOut = FakeDataUtil.generateTagData();
//		System.out.println(strOut);
//		System.out.println(FakeDataUtil.getMapTagData(strOut));

//		String strOut = FakeDataUtil.generateIotData();
//		System.out.println(strOut);
//		System.out.println(FakeDataUtil.getMapIotData(strOut));
//		System.out.println(FakeDataUtil.getListIotData(strOut));
		
//		String strOut = FakeDataUtil.generateIotMain();
//		System.out.println(strOut);
//		System.out.println(FakeDataUtil.getMapIotMain(strOut));
//		System.out.println(FakeDataUtil.getListIotMain(strOut));
		
//		String strOut = FakeDataUtil.generateIotFireIdx();
//		System.out.println(strOut);
//		System.out.println(FakeDataUtil.getMapIotFireIdx(strOut));
////		System.out.println(FakeDataUtil.getListIotFireIdx(strOut));

//		String strOut = FakeDataUtil.generateTagFireIdx();
//		System.out.println(strOut);
//		System.out.println(FakeDataUtil.getMapTagFi reIdx(strOut));
//		System.out.println(FakeDataUtil.getListTagFireIdx(strOut));

		String strOut = FakeDataUtil.generateTagDvcEvent();
		System.out.println(strOut);
		//System.out.println(FakeDataUtil.getMapIotData(strOut));
		System.out.println(FakeDataUtil.getListTagDvcEvent(strOut));

	}
	
	@Test
	public void splitWord() {
		String testStr = "터빈 보일러 급수펌프- A 펌프 구동장치 측 진동. X (진동)";
		String[] arTestStr=testStr.split(" ");
		List<String> arResult= new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		int cnt=0;
		for (String item : arTestStr) {
			sb.append(item+" ");
			if (cnt%3==2) {
				arResult.add(sb.deleteCharAt(sb.length()-1).toString());
				sb.setLength(0);
			}
			cnt++;
		}
		if (sb.length() > 0) {
			arResult.add(sb.deleteCharAt(sb.length()-1).toString());
			sb.setLength(0);
		}

		for (String item : arResult) {
			System.out.println("["+item+"]");
		}
	}
}
