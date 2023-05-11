/**=========================================================================================
<overview>로그프레소연동 테스트데이터핸들러
  </overview>
==========================================================================================*/
package com.gencode.issuetool.etc;

import static org.hamcrest.CoreMatchers.equalTo;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.gencode.issuetool.obj.TagData;
import com.gencode.issuetool.obj.TagDvcEventHist;
import com.gencode.issuetool.obj.TagDvcPushEvent;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.logpresso.obj.AverageObj;
import com.gencode.issuetool.obj.AreaInfo;
import com.gencode.issuetool.obj.AuthUserInfo;
import com.gencode.issuetool.obj.CommonCode;
import com.gencode.issuetool.obj.EtcDeviceInfo;
import com.gencode.issuetool.obj.FacilTagInfo;
import com.gencode.issuetool.obj.InteriorInfo;
import com.gencode.issuetool.obj.IotData;
import com.gencode.issuetool.obj.IotDeviceInfo;
import com.gencode.issuetool.obj.IotFireIdx;
import com.gencode.issuetool.obj.IotMain;
import com.gencode.issuetool.obj.PlantInfo;
import com.gencode.issuetool.obj.PlantPartInfo;
import com.gencode.issuetool.obj.TagFireIdx;
import com.gencode.issuetool.service.CacheMapManager;
import com.gencode.issuetool.util.JsonUtils;
import com.google.gson.reflect.TypeToken;

public class FakeDataUtil {

	//20~50값 생성
	static public double tempVeryLowVal() {
		return (new Long((new Random()).longs(50, 100).findFirst().getAsLong())).doubleValue()/10;
	}
	static public double tempLowVal() {
		return (new Long((new Random()).longs(200, 250).findFirst().getAsLong())).doubleValue()/10;
	}
	static public double tempMidVal() {
		return (new Long((new Random()).longs(400, 450).findFirst().getAsLong())).doubleValue()/10;
	}

	static public String tempValStr() {
		return Double.toString((new Long((new Random()).longs(10, 210).findFirst().getAsLong())).doubleValue()/10);
	}

	static public double alarmVal() {
		return (new Long((new Random()).longs(600, 800).findFirst().getAsLong())).doubleValue()/10;
	}
	static public double fireIdxVal() {
		return (new Long((new Random()).longs(60, 80).findFirst().getAsLong())).doubleValue()/10;
	}
	static public double fireIdxMaxVal() {
		return (new Long((new Random()).longs(80, 100).findFirst().getAsLong())).doubleValue()/10;
	}
	
	static public int randomVal(int max) {
		return (new Random()).nextInt(max);
	}

//	static public String dsMain1Val() {
//		return generateLogpressoVal(null);
//	}
//	
//	static public String dsSub11Val() {
//		return generateLogpressoVal(null);
//	}
//	static public String dsSub12Val() {
//		return generateLogpressoVal(null);
//	}
//	static public String dsSub21Val() {
//		return generateLogpressoVal(null);
//	}
//	static public String dsSub209Val() {
//		return generateLogpressoVal(null);
//	}
//	static public String dsSub210Val() {
//		return generateLogpressoVal(null);
//	}
//	static public String dsSub200Val() {
//		return generateLogpressoVal(null);
//	}

	
	
//	static String generateLogpressoVal(String[] dsList) {
//		List<Map<String, String>> arJsonObjList = new ArrayList<Map<String, String>>();
//		for ( String key: dsList) {
//			arJsonObjList.add(new HashMap<String, String>(){{put(key, Double.toString(FakeDataUtil.tempLowVal()));}});
//		}
//		arJsonObjList.add(new HashMap<String, String>(){{put("time", Utils.YYYYMMDDWithHypen());}});
//		//System.out.println();
//		return JsonUtils.toJson(arJsonObjList).toString();
//	}
	
	
	public static String generateTotalFireIdxData() {
		/*
{
	totalfireidx: [
		{plant_no: "M1",idx: 12.3,},
		{plant_no: "M2",idx: 12.3,},
		{plant_no: "MC",idx: 12.3,}
	],
	facilfireidx: [
		{
			plant_no: "M1",
			items: [
				{plant_part: "FP", idx: 12.3, },
				{plant_part: "MAA",idx: 12.3,},
				{plant_part: "MAV",idx: 12.3,},
				{plant_part: "MAB",idx: 12.3,},
				{plant_part: "MAK",idx: 12.3,},
				{plant_part: "MKD",idx: 12.3,},
			],
		},
		{
			plant_no: "M2",
			items: [
				{plant_part: "FP",idx: 12.3,},
				{plant_part: "MAA",idx: 12.3,},
				{plant_part: "MAV",idx: 12.3,},
				{plant_part: "MAB",idx: 12.3,},
				{plant_part: "MAK",idx: 12.3,},
				{plant_part: "MKD",idx: 12.3,},
			],
		},
	],
	iotfireidx: [
		{area_code: "TURBIN01",idx: 12.3,},
		{area_code: "TURBIN02",idx: 12.3,},
		{area_code: "MCTRL",idx: 12.3,},
		{area_code: "UNDERCBL",idx: 12.3,},
	],
}
		*/
		Map<String, List<Map<String, Object>>> mapTotal= new HashMap<String, List<Map<String, Object>>>();
		
		List<Map<String, Object>> totalFireIdxItems= new ArrayList<Map<String, Object>>();
		for (String plant: plantList2) {
			totalFireIdxItems.add(new HashMap<String, Object>() {{
				put("plant_no",plant);
				put("idx", tempValStr());
			}});
		}
		totalFireIdxItems.add(new HashMap<String, Object>() {{
			put("plant_no","all");
			put("idx", tempValStr());
		}});
		mapTotal.put("totalfireidx", totalFireIdxItems);
		
		List<Map<String, Object>> facilFireIdxItems = new ArrayList<Map<String, Object>>();
		for (String plant: plantList) {
			facilFireIdxItems.add(new HashMap<String, Object>() {{
				put("plant_no", plant);
				List<Map<String, String>> itemList =  new ArrayList<Map<String, String>>();
				for (String plantPart: plantPartList) {
					itemList.add(new HashMap<String, String>() {{
						put("plant_part", plantPart);
						put("idx", tempValStr());
					}});
				}
				put("items", itemList);
			}});
		}
		mapTotal.put("tagfireidx", facilFireIdxItems);
		
		List<Map<String, Object>> iotFireIdxItems = new ArrayList<Map<String, Object>>();
		for (String areaCode : areaCodeList) {
			iotFireIdxItems.add(new HashMap<String, Object>() {{
				put("area_code", areaCode);
				put("idx", tempValStr());
			}});
		}
		mapTotal.put("iotfireidx", iotFireIdxItems);
		return JsonUtils.toJson(mapTotal);
	}
	
//	public static String getMapTotalFireIdxData(String jsonStr) {
//		Map<String, Object> mapTotalFireIdxData = JsonUtils.toObject(jsonStr, Map.class);
//		Map<String, Object> mapMainOut=new HashMap<String,Object>();
//		Map<String, Object> mapTotalOut=new HashMap<String,Object>();
//		Map<String, Map<String, String>> mapTagOut=new HashMap<String,Map<String, String>>();
//		Map<String, Object> mapIotOut=new HashMap<String,Object>();
//		((List<Map<String, String>>)mapTotalFireIdxData.get("totalfireidx")).forEach(e->{
//			mapTotalOut.put(e.get("plant_no"), e.get("idx"));
//		});
//		((List<Map<String, Object>>)mapTotalFireIdxData.get("tagfireidx")).forEach(e->{
//			Map<String, String> items = new HashMap<String, String>();
//			((List<Map<String,String>>)e.get("items")).forEach( ex-> {
//				
//				
//				items.put(ex.get("plant_part"), ex.get("idx"));
//			});
//			mapTagOut.put((String)e.get("plant_no"), items);
//		});
//		
//		((List<Map<String, String>>)mapTotalFireIdxData.get("iotfireidx")).forEach(e->{
//			mapIotOut.put(e.get("area_code"), e.get("idx"));
//		});
//		mapMainOut.put("totalfireidx", mapTotalOut);
//		mapMainOut.put("tagfireidx", mapTagOut);
//		mapMainOut.put("iotfireidx", mapIotOut);
//		
//		return JsonUtils.toJson(mapMainOut);
//	}

	/**
	 --mapTagFireIdx
	{"M1":{
		"MAV":{"max_fire_idx":"6","time":"2023-01-05 00:05:19.000","fire_idx":"6"},
		"AVERAGE":{"max_fire_idx":"7.0","time":"2023-01-05 00:05:19.000","fire_idx":"7.0"},
		"MAK":{"max_fire_idx":"9","time":"2023-01-05 00:05:19.000","fire_idx":"9"},
		"MKD":{"max_fire_idx":"5","time":"2023-01-05 00:05:19.000","fire_idx":"5"},
		"FP":{"max_fire_idx":"9","time":"2023-01-05 00:05:19.000","fire_idx":"9"},"MAA":{"max_fire_idx":"7","time":"2023-01-05 00:05:19.000","fire_idx":"7"},"MAB":{"max_fire_idx":"6","time":"2023-01-05 00:05:19.000","fire_idx":"6"}},"M2":{"MAV":{"max_fire_idx":"6","time":"2023-01-05 00:05:19.000","fire_idx":"6"},"AVERAGE":{"max_fire_idx":"8.16","time":"2023-01-05 00:05:19.000","fire_idx":"8.16"},"MAK":{"max_fire_idx":"10","time":"2023-01-05 00:05:19.000","fire_idx":"10"},"MKD":{"max_fire_idx":"9","time":"2023-01-05 00:05:19.000","fire_idx":"9"},"FP":{"max_fire_idx":"8","time":"2023-01-05 00:05:19.000","fire_idx":"8"},"MAA":{"max_fire_idx":"9","time":"2023-01-05 00:05:19.000","fire_idx":"9"},"MAB":{"max_fire_idx":"7","time":"2023-01-05 00:05:19.000","fire_idx":"7"}}}
	-- mapIotFireIdx
	{"SC1":{"max_fire_idx":"17","time":"2023-01-05 00:06:40.000","fire_idx":"15"},"SC3":{"max_fire_idx":"17","time":"2023-01-05 00:06:40.000","fire_idx":"15"},"SC2":{"max_fire_idx":"17","time":"2023-01-05 00:06:40.000","fire_idx":"15"},"ST3":{"max_fire_idx":"17","time":"2023-01-05 00:06:40.000","fire_idx":"15"},"AVERAGE":{"max_fire_idx":"17.0","time":"2023-01-05 00:06:40.000","fire_idx":"15.0"},"SI1":{"max_fire_idx":"17","time":"2023-01-05 00:06:40.000","fire_idx":"15"},"SI0":{"max_fire_idx":"17","time":"2023-01-05 00:06:40.000","fire_idx":"15"},"SI2":{"max_fire_idx":"17","time":"2023-01-05 00:06:40.000","fire_idx":"15"},"SU1":{"max_fire_idx":"17","time":"2023-01-05 00:06:40.000","fire_idx":"15"}}

	--total fireidx output
	{
		"tagfireidx":
		{
			"M1":{"MAV":"8.2","MAK":"19.8","MKD":"14.5","FP":"17.8","MAA":"15.0","MAB":"4.4"},
			"M2":{"MAV":"12.8","MAK":"10.1","MKD":"19.0","FP":"6.1","MAA":"14.1","MAB":"11.8"}
		},
		"totalfireidx":
		{
			"all":"1.8",
			"M1":"19.5",
			"M2":"20.8",
			"MC":"1.4"
		},
		"iotfireidx":
		{
			"UNDERCBL":"4.4",
			"MCTRL":"1.3",
			"INVERTER":"14.4",
			"TURBIN":"4.2"
		}
	}
	*/
	public static String getMapTotalFireIdxData(Map<String, Object> mapIotFireIdx, Map<String, Object> mapTagFireIdx,
												List<PlantInfo> plantInfos,
												List<PlantPartInfo> plantPartInfos,
												List<InteriorInfo> interiorInfos,
												List<AreaInfo> areaInfos,
												Map<Long, AreaInfo> mapAreaInfo, 
												Map<Long, InteriorInfo> mapInteriorInfo ) {
		
		Map<String, Object> mapTotalFireIdxOut =new HashMap<String,Object>();

		Map<String, Object> mapTagFireIdxOut =new HashMap<String,Object>();
		Map<String, Object> mapTagPlantPartOut =new HashMap<String,Object>();
		String time = "";
		plantInfos.stream().filter(e-> mapTagFireIdx.get(e.getPlantNo())!=null/* not 'M1','M2' */)
		.forEach( e -> {
			Map<String, Object> mapPlantPartIn = (Map<String, Object>)mapTagFireIdx.get(e.getPlantNo());
			plantPartInfos.forEach( ex -> {
				if (ex.getPlantId()  == e.getId()) {
					final Map<String, String> mapItem = (mapPlantPartIn.get(ex.getPlantPartCode())!=null)?(Map<String, String>)mapPlantPartIn.get(ex.getPlantPartCode()):
						new HashMap<String, String>(){{ put("max_fire_idx","0");}};
					mapTagPlantPartOut.put(ex.getPlantPartCode(), mapItem.get("max_fire_idx"));
					
					//좋합지수 
					if (mapTotalFireIdxOut.get(e.getPlantNo())==null) {
						mapTotalFireIdxOut.put(e.getPlantNo(), new ArrayList<Double>() {{
							add(new Double((String)mapItem.get("max_fire_idx")));
						}});
					} else {
						List<Double> fireIdxs = (List<Double>)mapTotalFireIdxOut.get(e.getPlantNo());
						fireIdxs.add(new Double((String)mapItem.get("max_fire_idx")));
						mapTotalFireIdxOut.put(e.getPlantNo(), fireIdxs);
					}
				}
			});
			mapTagFireIdxOut.put(e.getPlantNo(), new HashMap<String, Object>(){{
				putAll(mapTagPlantPartOut);
			}});
		});
		mapTagFireIdxOut.put("time", mapTagFireIdx.get("time"));

		Map<String, Object> mapIotFireIdxOut =new HashMap<String,Object>();
		
		interiorInfos.stream()//.filter(e-> mapIotFireIdx.get(e.getInteriorCode())!=null)
				.forEach( e -> {
			final Map<String, Object> mapInteriorIn = (mapIotFireIdx.get(e.getInteriorCode())!=null)?(Map<String, Object>)mapIotFireIdx.get(e.getInteriorCode())
					:new HashMap<String, Object>(){{ put("max_fire_idx","0");}};
			String areaCode = mapAreaInfo.get(mapInteriorInfo.get(e.getId()).getAreaId()).getAreaCode();
			if (mapIotFireIdxOut.get(areaCode)==null) {
				mapIotFireIdxOut.put(areaCode, new ArrayList<Double>() {{
					add(new Double((String)mapInteriorIn.get("max_fire_idx")));
				}});
			} else {
				List<Double> fireIdxs = (List<Double>)mapIotFireIdxOut.get(areaCode);
				fireIdxs.add(new Double((String)mapInteriorIn.get("max_fire_idx")));
				mapIotFireIdxOut.put(areaCode, fireIdxs);
			}
			String plantNoCommon="MC";
			//좋합지수 공통호기로 처리
			if (mapTotalFireIdxOut.get(plantNoCommon)==null) {
				mapTotalFireIdxOut.put(plantNoCommon, new ArrayList<Double>() {{
					add(new Double((String)mapInteriorIn.get("max_fire_idx")));
				}});
			} else {
				List<Double> fireIdxs = (List<Double>)mapTotalFireIdxOut.get(plantNoCommon);
				fireIdxs.add(new Double((String)mapInteriorIn.get("max_fire_idx")));
				mapTotalFireIdxOut.put(plantNoCommon, fireIdxs);
			}

		});
		mapIotFireIdxOut.put("time", mapIotFireIdx.get("time"));
		
		areaInfos.forEach(e -> {
			double avgFireIdx = ((List<Double>)mapIotFireIdxOut.get(e.getAreaCode())).stream().mapToDouble(d-> d.doubleValue()).average().getAsDouble();
			mapIotFireIdxOut.put(e.getAreaCode(), (new BigDecimal(avgFireIdx)).setScale(1, RoundingMode.HALF_UP));
		});
		
		// 전체 화재지수
		List<Double> fireIdxAlls = new ArrayList<Double>(); 
		plantInfos.forEach(e -> {
			//fireIdxAlls.addAll((List<Double>)mapTotalFireIdxOut.get(e.getPlantNo()));
			double avgFireIdx = ((List<Double>)mapTotalFireIdxOut.get(e.getPlantNo())).stream().mapToDouble(d-> d.doubleValue()).average().getAsDouble();
			mapTotalFireIdxOut.put(e.getPlantNo(),(new BigDecimal(avgFireIdx)).setScale(1, RoundingMode.HALF_UP));
			fireIdxAlls.add((new BigDecimal(avgFireIdx)).setScale(1, RoundingMode.HALF_UP).doubleValue());

		});
		double avgFireIdx = ((List<Double>)fireIdxAlls).stream().mapToDouble(d-> d.doubleValue()).average().getAsDouble();
		mapTotalFireIdxOut.put("all", (new BigDecimal(avgFireIdx)).setScale(1, RoundingMode.HALF_UP));
		
		Map<String, Object> mapMainOut=new HashMap<String,Object>();
		mapMainOut.put("time", mapIotFireIdxOut.get("time"));
		mapMainOut.put("totalfireidx", mapTotalFireIdxOut);
		mapMainOut.put("tagfireidx", mapTagFireIdxOut);
		mapMainOut.put("iotfireidx", mapIotFireIdxOut);
		
		return JsonUtils.toJson(mapMainOut);
	}

	
	/**
	 * 
	 * @return
	 */
	public static String generateIotData() {
		/*
		{
			   "iotdata":[
			      {
			         "interior":"ST3",
			         "data":[
			            {
			               "time":"2021-06-14 17:38:19.000",
			               "device_id":"0101L01",
			               "humid":"29.2",
			               "smoke":"26.4",
			               "temp":"29.5",
			               "co":"19.2",
			               "flame":"정상"
			            },
			            */
		String time =  Utils.yyyyMMddHHmmssSSSHypen();
		Map<String, ArrayList<Map<String, String>>> mapInterior = new HashMap<String, ArrayList<Map<String, String>>>();
		
		for (String interior : interiorList) {
			mapInterior.put(interior, new ArrayList<Map<String, String>>());
		}
		
		for (String deviceId: iotDeviceList) {
			mapInterior.get(deviceId.substring(0,3)).add(new HashMap<String, String>(){{
				put("flame", "정상");
				put("device_id",deviceId);
				put("time", time);
				put("humid", Double.toString(FakeDataUtil.tempMidVal()));
				put("smoke", Double.toString(FakeDataUtil.tempVeryLowVal()));
				put("co", Double.toString(FakeDataUtil.tempLowVal()));
				put("temp", Double.toString(FakeDataUtil.tempLowVal()));
			}});
		}

		Map<String, Object> iotMap = new HashMap<String, Object>();
		List<Map<String, Object>> arIotDataList= new ArrayList<Map<String, Object>>();
		for (String interior: interiorList) {
			arIotDataList.add(new HashMap<String, Object>() {{
				put("interior",interior);
				put("data", mapInterior.get(interior));
			}});
		}
		return "{\"iotdata\":"+JsonUtils.toJson(arIotDataList).toString()+"}";
	}
	
	public static String getMapIotData(String jsonStr) {
		return getMapIotData(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static String getMapIotData(Map<String, List<Map<String, Object>>> mapIotData) {
		Map<String, Object> mapIotDataOut = new HashMap<String, Object>();
		mapIotData.get("iotdata").forEach(e -> {
			mapIotDataOut.put(e.get("interior").toString(), e.get("data"));
		});
		return JsonUtils.toJson(mapIotDataOut);
	}
	
	public static List<IotData> getListIotData(String jsonStr) {
		//Map<String, Object> mapIotData = JsonUtils.toObject(jsonStr, Map.class);
		return getListIotData(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static List<IotData> getListIotData(Map<String, List<Map<String, Object>>> mapIotData ) {
				List<IotData> result = new ArrayList<IotData>();
		((List<Map<String, Object>>)mapIotData.get("iotdata")).forEach(e -> {
			//String plantNo = (String)e.get("plant_no");
			((List<Map<String, Object>>)e.get("data")).forEach(ex -> {
				result.add(new IotData(
						(String)ex.get("time"),
						(String)e.get("interior"),
						(String)ex.get("device_id"),
						(int)(Float.parseFloat((String)ex.get("humid"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
						(int)(Float.parseFloat((String)ex.get("smoke"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
						(int)(Float.parseFloat((String)ex.get("temp"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
						(int)(Float.parseFloat((String)ex.get("co"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
						((String)ex.get("flame")).equals("정상")?0:1
					));
			});
		});
		return result;
	}
	
	
	public static String generateIotMain() {
/*		
		{
			   "iotma":[
			      {
			         " interior":"ST3",
			         "data":{
			            "time":"2021-06-14 17:38:19.000",
			            "max_humid":"29.2",
			            "max_smoke":"26.4",
			            "max_temp":"29.5",
			            "max_co":"19.2",
			            "max_flame":"정상",
			            "avg_humid":"29.2",
			            "avg_smoke":"26.4",
			            "avg_temp":"29.5",
			            "avg_co":"19.2",
			            "avg_flame":"정상"
			         }
			      }
			      */
		String time =  Utils.yyyyMMddHHmmssSSSHypen();
		List<Map<String, Object>> arIotMaList = new ArrayList<Map<String, Object>>();
		
		for (String interior : interiorList) {
			arIotMaList.add(
					new HashMap<String, Object>(){{
						put("interior",interior);
						put("data",new HashMap<String, String>() {{
							put("time", time);
							put("max_flame", "정상");
							put("max_humid", Double.toString(FakeDataUtil.tempMidVal()));
							put("max_smoke", Double.toString(FakeDataUtil.tempVeryLowVal()));
							put("max_co", Double.toString(FakeDataUtil.tempLowVal()));
							put("max_temp", Double.toString(FakeDataUtil.tempLowVal()));
							put("avg_flame", "정상");
							put("avg_humid", Double.toString(FakeDataUtil.tempMidVal()));
							put("avg_smoke", Double.toString(FakeDataUtil.tempVeryLowVal()));
							put("avg_co", Double.toString(FakeDataUtil.tempLowVal()));
							put("avg_temp", Double.toString(FakeDataUtil.tempLowVal()));
						}});
			}});
		}
		return "{\"iotma\":"+JsonUtils.toJson(arIotMaList).toString()+"}";
	}
	
	public static String getMapIotMain(String jsonStr) {
		return getMapIotMain(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static String getMapIotMain(Map<String, List<Map<String, Object>>> mapIotData) {
		//Map<String, List<Map<String, Object>>> mapIotData = JsonUtils.toObject(jsonStr, Map.class);
		Map<String, Object> mapIotDataOut = new HashMap<String, Object>();
		mapIotData.get("iotma").forEach(e -> {
			mapIotDataOut.put(e.get("interior").toString(), e.get("data"));
			if (mapIotDataOut.get("time")==null) {
				mapIotDataOut.put("time", ((Map<String, String>)e.get("data")).get("time"));
			}
		});
		return JsonUtils.toJson(mapIotDataOut);
	}
	public static String getMapIotMainArea(String jsonStr, Map<Long, AreaInfo> mapAreaInfo, Map<String, InteriorInfo> mapInteriorInfoByCode) {
		return getMapIotMainArea(JsonUtils.toObject(jsonStr, Map.class), mapAreaInfo, mapInteriorInfoByCode);
	}

	public static String getMapIotMainArea(Map<String, List<Map<String, Object>>> mapIotData, Map<Long, AreaInfo> mapAreaInfo, Map<String, InteriorInfo> mapInteriorInfoByCode) {
		//Map<String, List<Map<String, Object>>> mapIotData = JsonUtils.toObject(jsonStr, Map.class);
		Map<String, Object> mapIotDataOut = new HashMap<String, Object>();
		for (Map<String, Object> e: mapIotData.get("iotma")) {
		//mapIotData.get("iotma").forEach(e -> {
			InteriorInfo interiorInfo = mapInteriorInfoByCode.get(e.get("interior").toString());
			String areaCode = mapAreaInfo.get(interiorInfo.getAreaId()).getAreaCode();
			
			Map<String, Object> data=(Map<String, Object>)e.get("data");
			Map<String, Object> dataItem =(mapIotDataOut.get(areaCode)==null)?(new HashMap<String, Object>()):(Map<String, Object>)mapIotDataOut.get(areaCode);
			if (dataItem.size()>0) {
				float prevMaxHumid = Float.parseFloat((String)dataItem.get("max_humid"));
				float maxHumid = Float.parseFloat((String)data.get("max_humid"));
				if (maxHumid > prevMaxHumid) {
					dataItem.put("max_humid", Double.toString(maxHumid));
				}

				float prevMaxSmoke= Float.parseFloat((String)dataItem.get("max_smoke"));
				float maxSmoke= Float.parseFloat((String)data.get("max_smoke"));
				if (maxSmoke > prevMaxSmoke) {
					dataItem.put("max_smoke", Float.toString(maxSmoke));
				}

				float prevMaxTemp= Float.parseFloat((String)dataItem.get("max_temp"));
				float maxTemp= Float.parseFloat((String)data.get("max_temp"));
				if (maxTemp > prevMaxTemp) {
					dataItem.put("max_temp", Float.toString(maxTemp));
				}
				
				float prevMaxCo= Float.parseFloat((String)dataItem.get("max_co"));
				float maxCo= Float.parseFloat((String)data.get("max_co"));
				if (maxCo > prevMaxCo) {
					dataItem.put("max_co", Float.toString(maxCo));
				}
				
				if (!"정상".equals((String)dataItem.get("max_flame"))) {
					dataItem.put("max_flame", "불꽃");
				}
				mapIotDataOut.put(areaCode, dataItem);
			} else {
				mapIotDataOut.put(areaCode, data);
			}
			if (mapIotDataOut.get("time")==null) {
				mapIotDataOut.put("time", ((Map<String, String>)e.get("data")).get("time"));
			}
		}
		
		return JsonUtils.toJson(mapIotDataOut);
	}

	public static List<IotMain> getListIotMain(String jsonStr) {
		return getListIotMain(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static List<IotMain> getListIotMain(Map<String, List<Map<String, Object>>> mapIotMain) {
		//Map<String, Object> mapIotMain = JsonUtils.toObject(jsonStr, Map.class);

		List<IotMain> result = new ArrayList<IotMain>();
		//((List<Map<String, Object>>)
		mapIotMain.get("iotma").forEach(e -> {
			//String plantNo = (String)e.get("plant_no");
			Map<String, Object> data=(Map<String, Object>)e.get("data");
			result.add(new IotMain(
					(String)data.get("time"),
					(String)e.get("interior"),
					(int)(Float.parseFloat((String)data.get("max_humid"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(int)(Float.parseFloat((String)data.get("max_smoke"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(int)(Float.parseFloat((String)data.get("max_temp"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(int)(Float.parseFloat((String)data.get("max_co"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					((String)data.get("max_flame")).equals("정상")?0:1,
					(int)(Float.parseFloat((String)data.get("avg_humid"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(int)(Float.parseFloat((String)data.get("avg_smoke"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(int)(Float.parseFloat((String)data.get("avg_temp"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(int)(Float.parseFloat((String)data.get("avg_co"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					((String)data.get("avg_flame")).equals("정상")?0:1
				));
		});
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String generateIotDataByDevice() {
		/*
		{
			   "iotdatabydevice":[
			      {
			         "interior":"ST3",
			         "data":[
			            {
			               "time":"2021-06-14 17:38:19.000",
			               "device_id":"0101L01",
			               "humid":"29.2",
			               "smoke":"26.4",
			               "temp":"29.5",
			               "co":"19.2",
			               "flame":"정상"
			            },
			            */
		String time =  Utils.yyyyMMddHHmmssSSSHypen();
		Map<String, ArrayList<Map<String, String>>> mapInterior = new HashMap<String, ArrayList<Map<String, String>>>();
		
		for (String interior : interiorList) {
			mapInterior.put(interior, new ArrayList<Map<String, String>>());
		}
		
		for (String deviceId: iotDeviceList) {
			mapInterior.get(deviceId.substring(0,3)).add(new HashMap<String, String>(){{
				put("flame", "정상");
				put("device_id",deviceId);
				put("time", time);
				put("humid", Double.toString(FakeDataUtil.tempMidVal()));
				put("smoke", Double.toString(FakeDataUtil.tempVeryLowVal()));
				put("co", Double.toString(FakeDataUtil.tempLowVal()));
				put("temp", Double.toString(FakeDataUtil.tempLowVal()));
			}});
			break;
		}

		Map<String, Object> iotMap = new HashMap<String, Object>();
		List<Map<String, Object>> arIotDataList= new ArrayList<Map<String, Object>>();
		for (String interior: interiorList) {
			arIotDataList.add(new HashMap<String, Object>() {{
				put("interior",interior);
				put("data", mapInterior.get(interior));
			}});
		}
		return "{\"iotdata\":"+JsonUtils.toJson(arIotDataList).toString()+"}";
	}
	
	public static String getMapIotDataByDevice(String jsonStr) {
		return getMapIotDataByDevice(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static String getMapIotDataByDevice(Map<String, List<Map<String, Object>>> mapIotData) {
		Map<String, Object> mapIotDataOut = new HashMap<String, Object>();
		mapIotData.get("iotdata").forEach(e -> {
			mapIotDataOut.put(e.get("interior").toString(), e.get("data"));
		});
		return JsonUtils.toJson(mapIotDataOut);
	}
	
	
	
	public static String generateIotFireIdx() {
		/*
			{
			   "iotfireidx":[
			      {
			         " interior":"ST1",
			         "data":{
			            "fire_idx":"15",
			            "max_fire_idx":"17",
			            "time":"2021-06-14 17:38:19.000"
			         }
			      },
	*/
		String time =  Utils.yyyyMMddHHmmssSSSHypen();
		List<Map<String, Object>> arIotFireIdxList = new ArrayList<Map<String, Object>>();
		
		for (String interior : interiorList) {
			arIotFireIdxList.add(
					new HashMap<String, Object>(){{
						put("interior",interior);
						put("data",new HashMap<String, String>() {{
							put("time", time);
							put("max_fire_idx", "17.5");//Double.toString(FakeDataUtil.tempLowVal()));
							put("fire_idx", "15.1");//Double.toString(FakeDataUtil.tempLowVal()));
						}});
			}});
		}
		return "{\"iotfireidx\":"+JsonUtils.toJson(arIotFireIdxList).toString()+"}";
//		return "{\"iotfireidx\":[{\"data\":{\"max_fire_idx\":\"17.4\",\"time\":\""+Utils.yyyyMMddHHmmssSSSHypen()+"\",\"fire_idx\":\"15.2\"},\"interior\":\"SC1\"},{\"data\":{\"max_fire_idx\":\"17.4\",\"time\":\""+Utils.yyyyMMddHHmmssSSSHypen()+"\",\"fire_idx\":\"15.2\"},\"interior\":\"SC2\"},{\"data\":{\"max_fire_idx\":\"17.4\",\"time\":\""+Utils.yyyyMMddHHmmssSSSHypen()+"\",\"fire_idx\":\"15.2\"},\"interior\":\"SC3\"},{\"data\":{\"max_fire_idx\":\"17.4\",\"time\":\""+Utils.yyyyMMddHHmmssSSSHypen()+"\",\"fire_idx\":\"15.2\"},\"interior\":\"SI0\"},{\"data\":{\"max_fire_idx\":\"17.4\",\"time\":\""+Utils.yyyyMMddHHmmssSSSHypen()+"\",\"fire_idx\":\"15.2\"},\"interior\":\"SU1\"}]}";
	}
	public static String getMapIotFireIdx(List<InteriorInfo> interiorInfos, String jsonStr) {
		return getMapIotFireIdx(interiorInfos, JsonUtils.toObject(jsonStr, Map.class));
	}
	
	public static Map<String, Object> getMapObjectIotFireIdx(List<InteriorInfo> interiorInfos, String jsonStr) {
		return getMapObjectIotFireIdx(interiorInfos, JsonUtils.toObject(jsonStr, Map.class));
	}
	
	public static String getMapIotFireIdx(List<InteriorInfo> interiorInfos, Map<String, List<Map<String, Object>>> mapIotFireIdx) {
		return JsonUtils.toJson(getMapObjectIotFireIdx(interiorInfos, mapIotFireIdx));
	}
	
	public static Map<String, Object> getMapObjectIotFireIdx(List<InteriorInfo> interiorInfos, Map<String, List<Map<String, Object>>> mapIotFireIdx) {
		//Map<String, List<Map<String, Object>>> mapIotData = JsonUtils.toObject(jsonStr, Map.class);
		Map<String, Object> mapIotDataOut = new HashMap<String, Object>();
		AverageObj averageObj = new AverageObj();
		mapIotFireIdx.get("iotfireidx").forEach(e -> {
			Map<String, String> data = (Map<String, String>)e.get("data");
			mapIotDataOut.put("time", data.get("time"));
			mapIotDataOut.put(e.get("interior").toString(), e.get("data"));
			averageObj.add("", data.get("time"), data.get("fire_idx"), data.get("max_fire_idx"));
		});
//		interiorInfos.forEach(e -> {
//			if (mapIotDataOut.get(e.getInteriorCode())==null) {
//				mapIotDataOut.put(e.getInteriorCode(), new HashMap<String, String>(){{
//					put("time",averageObj.getTime());
//					put("fire_idx","0");
//					put("max_fire_idx","0");
//				}});
//				averageObj.add("", averageObj.getTime(), "0","0");
//			}
//		});
		
		mapIotDataOut.put("AVERAGE", new HashMap<String, String>(){{
			put("time", averageObj.getTime());
			put("max_fire_idx", averageObj.getMaxFireIdxStr());
			put("fire_idx", averageObj.getFireIdxStr());
		}});
		return mapIotDataOut;
		//return JsonUtils.toJson(mapIotDataOut);
	}
	
	public static List<IotFireIdx> getListIotFireIdx(String jsonStr) {
		return getListIotFireIdx(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static List<IotFireIdx> getListIotFireIdx(Map<String, List<Map<String, Object>>> mapIotFireIdx) {
		//Map<String, Object> mapIotFireIdx = JsonUtils.toObject(jsonStr, Map.class);

		List<IotFireIdx> result = new ArrayList<IotFireIdx>();
		AverageObj averageObj = new AverageObj();
		mapIotFireIdx.get("iotfireidx").forEach(e -> {
			//String plantNo = (String)e.get("plant_no");
			Map<String, Object> data =(Map<String, Object>)e.get("data");
			averageObj.add((String)e.get("interior"),(String)data.get("time"), (String)data.get("fire_idx"), (String)data.get("max_fire_idx"));
			result.add(new IotFireIdx(
					(String)data.get("time"),
					(String)e.get("interior"),
					(long)(Float.parseFloat((String)data.get("fire_idx"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(long)(Float.parseFloat((String)data.get("max_fire_idx"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val())
					));
		});
		result.add(new IotFireIdx(
				averageObj.getTime(),
				"AVERAGE",
				averageObj.getFireIdx(),
				averageObj.getMaxFireIdx()
				));
		return result;
	}
	
	public static String generateTagData() {
		String time =  Utils.yyyyMMddHHmmssSSSHypen();
		List<Map<String, Object>> arTagMainList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listTagDataTBN = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listTagDataBFP = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapTagDataItem = new HashMap<String, Object>();
		Map<String, Object> mapData = new HashMap<String, Object>();
		String[] tagMainFieldsPrev = {"","",""};
		for (String[] tagMainFields : tagMainList) {
			if (!tagMainFieldsPrev[0].equals(tagMainFields[0])
					||!tagMainFieldsPrev[1].equals(tagMainFields[1])
					||!tagMainFieldsPrev[2].equals(tagMainFields[2])) {
				if (mapTagDataItem.size()>0) {
					mapTagDataItem.put("data", new HashMap<String, Object>(){{
						putAll(mapData);
					}});
					if ("TBN".equals(mapTagDataItem.get("plant_part"))) {
						listTagDataTBN.add(new HashMap<String, Object>(){{
							putAll(mapTagDataItem);
							}});
					} else {
						listTagDataBFP.add(new HashMap<String, Object>(){{
							putAll(mapTagDataItem);
							}});
					}
					mapTagDataItem.clear();
					mapData.clear();
				}				
			} 
			mapData.put("tagname", tagMainFields[3]);
			mapData.put("val", Double.toString(FakeDataUtil.tempMidVal()));
			mapData.put("seq", tagMainFields[4]);
			mapData.put("status", "");
			mapData.put("max_val", Double.toString(FakeDataUtil.tempMidVal()));
			mapData.put("time", time);
			mapTagDataItem.put("plant_no",tagMainFields[0]);
			mapTagDataItem.put("plant_part",tagMainFields[1]);
			mapTagDataItem.put("facil_code",tagMainFields[2]);
			tagMainFieldsPrev=tagMainFields;
		}
		if (mapTagDataItem.size()>0) {
			mapTagDataItem.put("data", new HashMap<String, Object>(){{
				putAll(mapData);
				}});
			if ("TBN".equals(mapTagDataItem.get("plant_part"))) {
				listTagDataTBN.add(new HashMap<String, Object>(){{
					putAll(mapTagDataItem);
					}});
			} else {
				listTagDataBFP.add(new HashMap<String, Object>(){{
					putAll(mapTagDataItem);
					}});
			}
			mapTagDataItem.clear();
			mapData.clear();
		}
		
		
		//return "{\"tagdata\":"+JsonUtils.toJson(listTagData).toString()+"}";
		return JsonUtils.toJson(
				
				new HashMap<String, Object>(){{
					put("tagdata",
						new ArrayList<HashMap>() {{
							add(
									new HashMap<String, Object>(){{
										put("tagdata",listTagDataTBN);
										put("plant_type","TBN");
									}}
								);	
							add(
									new HashMap<String, Object>(){{
										put("tagdata",listTagDataBFP);
										put("plant_type","BFP");
									}}
								);	
							}}
						);
				}}
				).toString();
//		"{
//		   ""tagdata"":[
//		      {
//		         ""tagdata"":[
//		            {
//		               ""data"":{
//		                  ""val"":""58.60"",
//		                  ""tagname"":""1MAV10CT025XE01"",
//		                  ""max_val"":58.7,
//		                  ""time"":""2023-01-03 14:37:04.585"",
//		                  ""seq"":1,
//		                  ""status"":""""
//		               },
//		               ""plant_part"":""MAV"",
//		               ""facil_code"":""MV1"",
//		               ""plant_no"":""M1""
//		            },
//		            {
//		               ""data"":{
//		                  ""val"":""58.70"",
//		                  ""tagname"":""1MAV30CT005XE01"",
//		                  ""max_val"":58.8,
//		                  ""time"":""2023-01-03 14:37:04.585"",
//		                  ""seq"":1,
//		                  ""status"":""""
//		               },
//		               ""plant_part"":""MAV"",
//		               ""facil_code"":""MV2"",
//		               ""plant_no"":""M1""
//		            },
//		            {
//		               ""data"":{
//		                  ""val"":""32.10"",
//		                  ""tagname"":""1MAV30CT010XE01"",
//		                  ""max_val"":32.1,
//		                  ""time"":""2023-01-03 14:37:04.585"",
//		                  ""seq"":2,
//		                  ""status"":""""
//		               },
//		               ""plant_part"":""MAV"",
//		               ""facil_code"":""MV2"",
//		               ""plant_no"":""M1""
//		            },
//		            {
//		               ""data"":{
//		                  ""val"":""45.25"",
//		                  ""tagname"":""1MAV40CT005XE02"",
//		                  ""max_val"":45.31,
//		                  ""time"":""2023-01-03 14:37:04.585"",
//		                  ""seq"":1,
//		                  ""status"":""""
//		               },
//		               ""plant_part"":""MAV"",
//		               ""facil_code"":""MV3"",
//		               ""plant_no"":""M1""
//		            },
//		            {
//		               ""data"":{
//		                  ""val"":""44.99"",
//		                  ""tagname"":""1MAV40CT015XE02"",
//		                  ""max_val"":45.05,
//		                  ""time"":""2023-01-03 14:37:04.585"",
//		                  ""seq"":1,
//		                  ""status"":""""
//		               },
//		               ""plant_part"":""MAV"",
//		               ""facil_code"":""MV3"",
//		               ""plant_no"":""M1""
//		            },
//		            {
//		               ""data"":{
//		                  ""val"":""44.82"",
//		                  ""tagname"":""1MAV40CT020XE02"",
//		                  ""max_val"":44.82,
//		                  ""time"":""2023-01-03 14:37:04.585"",
//		                  ""seq"":1,
//		                  ""status"":""""
//		               },
//		               ""plant_part"":""MAV"",
//		               ""facil_code"":""MV3"",
//		               ""plant_no"":""M1""
//		            }
//		         ],
//		         ""plant_type"":""TBN""
//		      }
//		   ]
//		}"	
	}
	public static String getMapTagData(String jsonStr) {
		return getMapTagData(JsonUtils.toObject(jsonStr, Map.class), "tagdata");
	}	
	public static String getMapTagData(String jsonStr, String tagName) {
		return getMapTagData(JsonUtils.toObject(jsonStr, Map.class), tagName);
	}	
	public static String getMapTagData(Map<String, Object> mapTagData) {
		return getMapTagData(mapTagData, "tagdata");
	}
	public static String getMapTagData(Map<String, Object> mapTagData, String tagName) {
		//Map<String, Object> mapTagData = JsonUtils.toObject(jsonStr, Map.class);

		Map<String, Object> mapTagPlantNo = new HashMap<String, Object>();
		Map<String, Object> mapTagPlantPart = new HashMap<String, Object>();
		Map<String, Object> mapTagFacilCode = new HashMap<String, Object>();
		
		String time = "";
		//System.out.println(((List<Map<String, Object>>)mapTagData.get("tagdata")).stream().filter(e -> e.get("tagdata")!=null).findAny().get().get("tagdata"));
		List<Map<String, Object>> listTagData = new ArrayList<Map<String, Object>>(); 
		((List<Map<String, Object>>)mapTagData.get(tagName)).forEach(e -> {
			if (e.get("tagdata") != null) {
				((List<Map<String, Object>>)e.get("tagdata")).forEach(ex -> {
					listTagData.add(ex);
				});
			}
		});
		
//		((List<Map<String, Object>>)((List<Map<String, Object>>)mapTagData.get("tagdata")).stream()
//				.filter(e -> e.get("tagdata")!=null).findAny().get()
//				.get("tagdata"))
		//System.out.println(JsonUtils.toJson(listTagData));
		listTagData
		.forEach(e -> {
			Map<String, Object> mapTagItem = new HashMap<String, Object>();
			String plantNo = (String)e.get("plant_no");
			String plantPart = (String)e.get("plant_part");
			String facilCode = (String)e.get("facil_code");

			Map<String, String> mapData = (Map<String, String>)e.get("data");
			mapTagItem.put(mapData.get("tagname"), mapData);
			if (time.equals("")) {
				mapTagPlantNo.put("time", mapData.get("time"));
			}
			
//			if (mapTagPlantPart.get((String)e.get("plant_part"))==null) {
//				mapTagFacilCode.clear();
//			}
			if (mapTagPlantNo.get(plantNo)!=null) {
				if (((Map<String, Object>)mapTagPlantNo.get(plantNo)).get(plantPart)!=null) {
					if (((Map<String, Object>)(((Map<String, Object>)mapTagPlantNo.get(plantNo)).get(plantPart))).get(facilCode)!=null) {
						((Map<String, Object>)((Map<String, Object>)(((Map<String, Object>)mapTagPlantNo.get(plantNo)).get(plantPart))).get(facilCode)).putAll(mapTagItem);
					} else {
						mapTagFacilCode.put(facilCode, new HashMap<String, Object>(){{
								putAll(mapTagItem);
							}});
						((Map<String, Object>)((Map<String, Object>)mapTagPlantNo.get(plantNo)).get(plantPart)).putAll(mapTagFacilCode);
					}
				} else {
					mapTagFacilCode.put(facilCode, new HashMap<String, Object>(){{
						putAll(mapTagItem);
					}});
					mapTagPlantPart.put(plantPart, new HashMap<String, Object>(){{
						putAll(mapTagFacilCode);
						}});
				}
				((Map<String, Object>)mapTagPlantNo.get(plantNo)).putAll(mapTagPlantPart);
			} else {
				mapTagFacilCode.put(facilCode, new HashMap<String, Object>(){{
					putAll(mapTagItem);
				}});
				mapTagPlantPart.put(plantPart, new HashMap<String, Object>(){{
					putAll(mapTagFacilCode);
					}});
				mapTagPlantNo.put(plantNo, new HashMap<String, Object>(){{
					putAll(mapTagPlantPart);
					}});
			}
			mapTagFacilCode.clear();
			mapTagPlantPart.clear();
			
//			mapTagFacilCode.put(facilCode, new HashMap<String, Object>(){{
//				putAll(mapTagItem);
//				}});
//			
//			mapTagPlantPart.put(plantPart, new HashMap<String, Object>(){{
//				putAll(mapTagFacilCode);
//				}});
//			mapTagPlantNo.put(plantNo, new HashMap<String, Object>(){{
//				putAll(mapTagPlantPart);
//				}});

		});
		//System.out.println(JsonUtils.toJson(mapTagPlantNo));
		return JsonUtils.toJson(mapTagPlantNo);
	}
	
	public static List<TagData> getListTagData(String jsonStr) {
		return getListTagData(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static List<TagData> getListTagData(Map<String, Object> mapTagData) {
		return getListTagData(mapTagData, "tagdata");
	}
	public static List<TagData> getListTagData(Map<String, Object> mapTagData, String tagName) {
		//Map<String, Object> mapTagData = JsonUtils.toObject(jsonStr, Map.class);

		List<TagData> result = new ArrayList<TagData>();
		
		List<Map<String, Object>> listTagData = new ArrayList<Map<String, Object>>(); 
		((List<Map<String, Object>>)mapTagData.get(tagName)).forEach(e -> {
			if (e.get("tagdata") != null) {
				((List<Map<String, Object>>)e.get("tagdata")).forEach(ex -> {
					listTagData.add(ex);
				});
			}
		});

		//((List<Map<String, Object>>)((List<Map<String, Object>>)mapTagData.get(tagName)).stream().filter(e -> e.get("tagdata")!=null).findAny().get().get("tagdata"))
		//((List<Map<String, Object>>)mapTagData.get("tagdata"))
		listTagData.forEach(e -> {
			//String plantNo = (String)e.get("plant_no");
			Map<String, Object> mapData = (Map<String, Object>)e.get("data");
			result.add(new TagData(
					(String)mapData.get("time"),
					(String)e.get("plant_no"),
					(String)e.get("plant_part"),
					(String)e.get("facil_code"),
					(String)mapData.get("tagname"),
					(((Double) mapData.get("seq")).intValue()),
					(int)(
							(Double.parseDouble((String)mapData.get("val"))
							)*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(int)(((Double) mapData.get("max_val"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(String)e.get("status")
				));
//			try {
//			} catch (Exception ex) {
//				System.out.println(ex);
//				//System.out.println(mapData.get("seq"));
//				System.out.println(mapData.toString());
//			}
		});
		return result;
	}
	
	public static String generateTagDataByTag() {
		String time =  Utils.yyyyMMddHHmmssSSSHypen();
		List<Map<String, Object>> arTagMainList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listTagData = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapTagDataItem = new HashMap<String, Object>();
		Map<String, Object> mapData = new HashMap<String, Object>();
		String[] tagMainFieldsPrev = {"","",""};
		int idx = Utils.getRandomNumber(0, tagMainList.length-1);
		int i = 0;
		for (String[] tagMainFields : tagMainList) {
			if (i < idx) continue;
			if (!tagMainFieldsPrev[0].equals(tagMainFields[0])
					||!tagMainFieldsPrev[1].equals(tagMainFields[1])
					||!tagMainFieldsPrev[2].equals(tagMainFields[2])) {
				if (mapTagDataItem.size()>0) {
					mapTagDataItem.put("data", new HashMap<String, Object>(){{
						putAll(mapData);
					}});
					listTagData.add(new HashMap<String, Object>(){{
						putAll(mapTagDataItem);
						}});
					mapTagDataItem.clear();
					mapData.clear();
				}				
			} 
			mapData.put("tagname", tagMainFields[3]);
			mapData.put("val", Double.toString(FakeDataUtil.tempMidVal()));
			mapData.put("seq", tagMainFields[4]);
			mapData.put("status", "");
			mapData.put("max_val", Double.toString(FakeDataUtil.tempMidVal()));
			mapData.put("time", time);
			mapTagDataItem.put("plant_no",tagMainFields[0]);
			mapTagDataItem.put("plant_part",tagMainFields[1]);
			mapTagDataItem.put("facil_code",tagMainFields[2]);
			tagMainFieldsPrev=tagMainFields;
			
			break;
		}
		if (mapTagDataItem.size()>0) {
			mapTagDataItem.put("data", new HashMap<String, Object>(){{
				putAll(mapData);
				}});
			listTagData.add(new HashMap<String, Object>(){{
				putAll(mapTagDataItem);
				}});
			mapTagDataItem.clear();
			mapData.clear();
		}
		
		
		//return "{\"tagdata\":"+JsonUtils.toJson(listTagData).toString()+"}";
		return JsonUtils.toJson(
				
				new HashMap<String, Object>(){{
					put("tagdata",
						new ArrayList<HashMap>() {{
							add(
									new HashMap<String, Object>(){{
										put("tagdata",listTagData);
										put("plant_type","TBN");
									}}
								);	
							}}
						);
				}}
				).toString();
//		"{
//		   ""databytag"":[
//		      {
//		         ""tagdata"":[
//		            {
//		               ""data"":{
//		                  ""val"":""58.60"",
//		                  ""tagname"":""1MAV10CT025XE01"",
//		                  ""max_val"":58.7,
//		                  ""time"":""2023-01-03 14:37:04.585"",
//		                  ""seq"":1,
//		                  ""status"":""""
//		               },
//		               ""plant_part"":""MAV"",
//		               ""facil_code"":""MV1"",
//		               ""plant_no"":""M1""
//		            },
//		         ],
//		         ""plant_type"":""TBN""
//		      }
//		   ]
//		}"	
	}
	
	public static String getMapTagDataByTag(String jsonStr) {
		return getMapTagData(JsonUtils.toObject(jsonStr, Map.class), "databytag");
	}
	
	public static String getMapTagDataByTag(Map<String, Object> mapTagData) {
		return getMapTagData(mapTagData, "databytag");
	}
	
	public static String generateTagFireIdx() {
		String time =  Utils.yyyyMMddHHmmssSSSHypen();
		List<Map<String, Object>> arTagFireIdxList = new ArrayList<Map<String, Object>>();
		for (String plant: plantList) {
			arTagFireIdxList.add(
					new HashMap<String, Object>(){{
						put("plant_no",plant);
						put("data",new ArrayList<Map<String, String>>(){{
							for (String plantPart : plantPartList) {
								add(new HashMap<String, String>(){{
									put("plant_part",plantPart);
									put("time", time);
									put("max_fire_idx", Double.toString(FakeDataUtil.fireIdxMaxVal()));
									put("fire_idx", Double.toString(FakeDataUtil.fireIdxVal()));
								}});
							}
						}});
			}});
		}
		return JsonUtils.toJson(
				new HashMap<String, ArrayList<HashMap<String, Object>>>(){{
					put("tagfireidx", new ArrayList<HashMap<String, Object>>() {{
						add(new HashMap<String, Object>(){{
							put("plant_type", "TBN");
							put("tagfireidx", arTagFireIdxList);
						}});
					}});
				}}).toString();
		//return "{\"tagfireidx\":[{\"tagfireidx\":[{\"data\":[{\"plant_part\":\"FP\",\"max_fire_idx\":\"9.4\",\"time\":\""+Utils.yyyyMMddHHmmssSSSHypen()+"\",\"fire_idx\":\"8.3\"},{\"plant_part\":\"MAA\",\"max_fire_idx\":\"8.9\",\"time\":\"2023-03-01 10:29:46.000\",\"fire_idx\":\"8.9\"},{\"plant_part\":\"MAB\",\"max_fire_idx\":\"8.9\",\"time\":\"2023-03-01 10:29:46.000\",\"fire_idx\":\"8.9\"},{\"plant_part\":\"MAK\",\"max_fire_idx\":\"9.0\",\"time\":\"2023-03-01 10:29:46.000\",\"fire_idx\":\"9.0\"},{\"plant_part\":\"MAV\",\"max_fire_idx\":\"8.4\",\"time\":\"2023-03-01 10:29:45.000\",\"fire_idx\":\"8.4\"},{\"plant_part\":\"MKD\",\"max_fire_idx\":\"8.6\",\"time\":\"2023-03-01 10:29:45.000\",\"fire_idx\":\"8.6\"}],\"plant_no\":\"M1\"},{\"data\":[{\"plant_part\":\"FP\",\"max_fire_idx\":\"9.7\",\"time\":\"2023-03-01 10:29:46.000\",\"fire_idx\":\"8.8\"},{\"plant_part\":\"MAA\",\"max_fire_idx\":\"10.2\",\"time\":\"2023-03-01 10:29:45.000\",\"fire_idx\":\"9.0\"},{\"plant_part\":\"MAB\",\"max_fire_idx\":\"9.9\",\"time\":\"2023-03-01 10:29:46.000\",\"fire_idx\":\"9.8\"},{\"plant_part\":\"MAK\",\"max_fire_idx\":\"10.5\",\"time\":\"2023-03-01 10:29:46.000\",\"fire_idx\":\"10.1\"},{\"plant_part\":\"MAV\",\"max_fire_idx\":\"9.1\",\"time\":\"2023-03-01 10:29:46.000\",\"fire_idx\":\"9.1\"},{\"plant_part\":\"MKD\",\"max_fire_idx\":\"9.7\",\"time\":\"2023-03-01 10:29:46.000\",\"fire_idx\":\"9.7\"}],\"plant_no\":\"M2\"}],\"plant_type\":\"TBN\"}]}";
//		"{
//		   ""tagfireidx"":[
//		      {
//		         ""plant_type"":""TBN"",
//		         ""tagfireidx"":[
//		            {
//		               ""plant_no"":""M1"",
//		               ""data"":[
//		                  {
//		                     ""facil_code"":""MAA"",
//		                     ""fire_idx"":""10"",
//		                     ""max_fire_idx"":""13""
//		                  },
	}
	public static String getMapTagFireIdx2(String jsonStr) {
		return getMapTagFireIdx2(JsonUtils.toObject(jsonStr, Map.class));
	}
	
	public static Map<String, Object> getMapObjectTagFireIdx2(String jsonStr) {
		return getMapObjectTagFireIdx2(JsonUtils.toObject(jsonStr, Map.class));
	}
	
	public static String getMapTagFireIdx2(Map<String, Object> mapTagFireIdx) {
		return JsonUtils.toJson(getMapObjectTagFireIdx2(mapTagFireIdx));
	}
	
	public static Map<String, Object> getMapObjectTagFireIdx2(Map<String, Object> mapTagFireIdx) {
		//Map<String, Object> mapTagFireIdx = JsonUtils.toObject(jsonStr, Map.class);

		Map<String, Object> mapTagPlantNo = new HashMap<String, Object>();
		Map<String, Object> mapTagPlantPart = new HashMap<String, Object>();
		AverageObj averageObj = new AverageObj();
		//List<Map<String, Object>> listTag = (List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx");
		((List<Map<String, Object>>)((List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx")).stream()
		.filter(e -> e.get("tagfireidx")!=null)
		//.peek(e -> System.out.println(e))
		.findFirst().get()
		.get("tagfireidx")
		)
		.stream()
		//.peek(e -> System.out.println(e))
		//((List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx"))
		.forEach(e -> {
			Map<String, Object> mapTagItem = new HashMap<String, Object>();
			String plantNo = (String)e.get("plant_no");
			if (averageObj.isNew(plantNo)) {
				mapTagPlantPart.put("AVERAGE", new HashMap<String, String>(){{
					put("time", averageObj.getTime());
					put("max_fire_idx", averageObj.getMaxFireIdxStr());
					put("fire_idx", averageObj.getFireIdxStr());
				}});
				mapTagPlantNo.put(averageObj.getFlag(), new HashMap<String, Object>(){{
					putAll(mapTagPlantPart);
					}}
				);
				mapTagPlantPart.clear();
			}
			((List<Map<String, Object>>)e.get("data"))
			.forEach(ex -> {
				String plantPart=(String)ex.get("plant_part");
				mapTagPlantPart.put(plantPart, new HashMap<String, String>(){{
					put("time", (String)ex.get("time"));
					put("max_fire_idx", (String)ex.get("max_fire_idx"));
					put("fire_idx", (String)ex.get("fire_idx"));
				}});
				averageObj.add(plantNo, (String)ex.get("time"), (String)ex.get("fire_idx"), (String)ex.get("max_fire_idx"));
			});
		});
		mapTagPlantPart.put("AVERAGE", new HashMap<String, String>(){{
			put("time", averageObj.getTime());
			put("max_fire_idx", averageObj.getMaxFireIdxStr());
			put("fire_idx", averageObj.getFireIdxStr());
		}});
		mapTagPlantNo.put("time",averageObj.getTime());
		mapTagPlantNo.put(averageObj.getFlag(), new HashMap<String, Object>(){{
			putAll(mapTagPlantPart);
			}});
		mapTagPlantPart.clear();
		return mapTagPlantNo;
		//return JsonUtils.toJson(mapTagPlantNo);
	}
	public static String getMapTagFireIdx(String jsonStr) {
		return getMapTagFireIdx(JsonUtils.toObject(jsonStr, Map.class));
	}
	
	public static Map<String, Object> getMapObjectTagFireIdx(String jsonStr) {
		return getMapObjectTagFireIdx(JsonUtils.toObject(jsonStr, Map.class));
	}
	
	public static String getMapTagFireIdx(Map<String, Object> mapTagFireIdx) {
		return JsonUtils.toJson(getMapObjectTagFireIdx(mapTagFireIdx));
	}
	public static Map<String, Object> getMapObjectTagFireIdx(Map<String, Object> mapTagFireIdx) {
		//Map<String, Object> mapTagFireIdx = JsonUtils.toObject(jsonStr, Map.class);

		Map<String, Object> mapTagPlantNo = new HashMap<String, Object>();
		Map<String, Object> mapTagPlantPart = new HashMap<String, Object>();
		AverageObj averageObj = new AverageObj();
		//List<Map<String, Object>> listTag = (List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx");
		((List<Map<String, Object>>)((List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx")).stream()
		.filter(e -> e.get("tagfireidx")!=null)
		//.peek(e -> System.out.println(e))
		.findFirst().get()
		.get("tagfireidx")
		)
		.stream().peek(e -> System.out.println(e))
		//((List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx"))
		.forEach(e -> {
			Map<String, Object> mapTagItem = new HashMap<String, Object>();
			String plantNo = (String)e.get("plant_no");
			if (averageObj.isNew(plantNo)) {
				mapTagPlantPart.put("AVERAGE", new HashMap<String, String>(){{
					put("time", averageObj.getTime());
					put("max_fire_idx", averageObj.getMaxFireIdxStr());
					put("fire_idx", averageObj.getFireIdxStr());
				}});
				mapTagPlantNo.put(averageObj.getFlag(), new HashMap<String, Object>(){{
					putAll(mapTagPlantPart);
					}}
				);
				mapTagPlantPart.clear();
			}
			Map<String, Object> mapData=((Map<String, Object>)e.get("data"));
			String plantPart=(String)mapData.get("plant_part");
			mapTagPlantPart.put(plantPart, new HashMap<String, String>(){{
				put("time", (String)mapData.get("time"));
				put("max_fire_idx", (String)mapData.get("max_fire_idx"));
				put("fire_idx", (String)mapData.get("fire_idx"));
			}});
			averageObj.add(plantNo, (String)mapData.get("time"), (String)mapData.get("fire_idx"), (String)mapData.get("max_fire_idx"));
		});
		mapTagPlantPart.put("AVERAGE", new HashMap<String, String>(){{
			put("time", averageObj.getTime());
			put("max_fire_idx", averageObj.getMaxFireIdxStr());
			put("fire_idx", averageObj.getFireIdxStr());
		}});
		mapTagPlantNo.put(averageObj.getFlag(), new HashMap<String, Object>(){{
			putAll(mapTagPlantPart);
			}});
		mapTagPlantPart.clear();
		return mapTagPlantNo;
		//return JsonUtils.toJson(mapTagPlantNo);
	}
	
	public static List<TagFireIdx> getListTagFireIdx2(String jsonStr) {
		return getListTagFireIdx2(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static List<TagFireIdx> getListTagFireIdx2(Map<String, Object> mapTagFireIdx) {
		//Map<String, Object> mapTagFireIdx = JsonUtils.toObject(jsonStr, Map.class);

		List<TagFireIdx> result = new ArrayList<TagFireIdx>();
		AverageObj averageObj = new AverageObj();
		((List<Map<String, Object>>)((List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx")).stream()
		.filter(e -> e.get("tagfireidx")!=null).findFirst().get()
		.get("tagfireidx"))
		//((List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx"))
		.forEach(e -> {
			String plantNo = (String)e.get("plant_no");
			//String plantNo = (String)e.get("plant_no");
			if (averageObj.isNew(plantNo)) {
				result.add(new TagFireIdx(
						averageObj.getTime(),
						averageObj.getFlag(),
						"AVERAGE",
						averageObj.getFireIdx(),
						averageObj.getMaxFireIdx()
						));
			}
			((List<Map<String, Object>>)e.get("data"))
			.forEach(ex -> {
				averageObj.add(plantNo,(String)ex.get("time"), (String)ex.get("fire_idx"), (String)ex.get("max_fire_idx"));
				result.add(new TagFireIdx(
						(String)ex.get("time"),
						plantNo,
						(String)ex.get("plant_part"),
						(long)(Float.parseFloat((String)ex.get("fire_idx"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
						(long)(Float.parseFloat((String)ex.get("max_fire_idx"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val())
						));
			});
		});
		result.add(new TagFireIdx(
				averageObj.getTime(),
				averageObj.getFlag(),
				"AVERAGE",
				averageObj.getFireIdx(),
				averageObj.getMaxFireIdx()
				));
		return result;
	}

	public static List<TagFireIdx> getListTagFireIdx(String jsonStr) {
		return getListTagFireIdx(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static List<TagFireIdx> getListTagFireIdx(Map<String, Object> mapTagFireIdx) {
		//Map<String, Object> mapTagFireIdx = JsonUtils.toObject(jsonStr, Map.class);

		List<TagFireIdx> result = new ArrayList<TagFireIdx>();
		AverageObj averageObj = new AverageObj();
		((List<Map<String, Object>>)((List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx")).stream()
		.filter(e -> e.get("tagfireidx")!=null).findFirst().get()
		.get("tagfireidx"))
		//((List<Map<String, Object>>)mapTagFireIdx.get("tagfireidx"))
		.forEach(e -> {
			String plantNo = (String)e.get("plant_no");
			//String plantNo = (String)e.get("plant_no");
			if (averageObj.isNew(plantNo)) {
				result.add(new TagFireIdx(
						averageObj.getTime(),
						averageObj.getFlag(),
						"AVERAGE",
						averageObj.getFireIdx(),
						averageObj.getMaxFireIdx()
						));
			}
			Map<String, Object> mapData=((Map<String, Object>)e.get("data"));
			averageObj.add(plantNo,(String)mapData.get("time"), (String)mapData.get("fire_idx"), (String)mapData.get("max_fire_idx"));
			result.add(new TagFireIdx(
					(String)mapData.get("time"),
					plantNo,
					(String)mapData.get("plant_part"),
					(long)(Float.parseFloat((String)mapData.get("fire_idx"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val()),
					(long)(Float.parseFloat((String)mapData.get("max_fire_idx"))*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val())
					));
		});
		result.add(new TagFireIdx(
				averageObj.getTime(),
				averageObj.getFlag(),
				"AVERAGE",
				averageObj.getFireIdx(),
				averageObj.getMaxFireIdx()
				));
		return result;
	}

	public static String generateTagDvcPushEvent(CacheMapManager cacheMapManager) {
		Map<String, String> arJson = new HashMap<String, String>();
		
		String type =  eventTypesRandom[randomVal(eventTypesRandom.length)];
		arJson.put("type",  type);
		arJson.put("time", Utils.yyyyMMddHHmmssHypen());
		if (type.equals("I")) {
			String deviceId=iotDeviceList[randomVal(iotDeviceList.length)];
			arJson.put("id", deviceId);
			arJson.put("desc", cacheMapManager.getMapIotDeviceInfoByCode().get(deviceId).getDeviceDesc());
			arJson.put("kind", valTypesIot[randomVal(valTypesIot.length)]);
			arJson.put("value", Double.toString(FakeDataUtil.tempLowVal()));
			arJson.put("level", eventLevels[randomVal(eventLevels.length)]); //A:알람, F:화재, C: critical
		} else if (type.equals("P")) {
			String tagName = tagMainList[randomVal(tagMainList.length)][3];
			arJson.put("id", tagName);
			arJson.put("desc", cacheMapManager.getMapFacilTagInfoByCode().get(tagName).getTagDesc2());
			arJson.put("kind", valTypesTag[randomVal(valTypesTag.length)]);
			arJson.put("value", Double.toString(FakeDataUtil.tempMidVal()));
			arJson.put("level", eventLevels[randomVal(eventLevels.length)]); //A:알람, F:화재, C: critical
		} else if (type.equals("C")) {
			String deviceId = cctvSampleList[randomVal(cctvSampleList.length)];
			arJson.put("id", deviceId);
			arJson.put("desc", cacheMapManager.getMapEtcDeviceInfoByCode().get(deviceId).getDeviceDesc());
			arJson.put("kind", "SMK");
			arJson.put("value", Double.toString(FakeDataUtil.tempVeryLowVal()));
			arJson.put("level", eventLevels[randomVal(eventLevels.length)]); //A:알람, F:화재, C: critical
		}
		return JsonUtils.toJson(arJson).toString();
	}

	public static TagDvcPushEvent getObjTabDvcPushEvent(CacheMapManager cacheMapManager, String jsonStr) {
		return getObjTabDvcPushEvent(cacheMapManager, JsonUtils.toObject(jsonStr, Map.class));
	}
	public static TagDvcPushEvent getObjTabDvcPushEvent(CacheMapManager cacheMapManager, Map<String, Object> mapJson) {
		//Map<String, Object> mapJson = JsonUtils.toObject(jsonStr, Map.class);
		String deviceType = (String)mapJson.get("type");
		String deviceId;
		String interiorCode=null; 
		String plantNo=null;
		String plantPartCode=null;
		String facilCode=null;
		if (deviceType.equals("I")) {
			IotDeviceInfo iotDeviceInfo = cacheMapManager.getMapIotDeviceInfoByCode().get((String)mapJson.get("id"));
			deviceId = iotDeviceInfo.getDeviceId();
			interiorCode=cacheMapManager.getMapInteriorInfo().get((Long)iotDeviceInfo.getInteriorId()).getInteriorCode();
		} else if (deviceType.equals("P")) {
			FacilTagInfo facilTagInfo =  cacheMapManager.getMapFacilTagInfoByCode().get((String)mapJson.get("id"));
			deviceId = facilTagInfo.getTagName();
			plantNo  = facilTagInfo.getPlantNo();
			plantPartCode = facilTagInfo.getPlantPartCode();
			facilCode = facilTagInfo.getFacilCode();
		} else {// C
			EtcDeviceInfo etcDeviceInfo = cacheMapManager.getMapEtcDeviceInfoByCode().get((String)mapJson.get("id"));
			deviceId = etcDeviceInfo.getDeviceId();
			interiorCode=cacheMapManager.getMapInteriorInfo().get((Long)etcDeviceInfo.getInteriorId()).getInteriorCode();
		}
		return new TagDvcPushEvent(
						(String)mapJson.get("time"), 
						deviceType, 
						deviceId, 
						(String)mapJson.get("kind"), 
						(String)mapJson.get("level"),
						(String)mapJson.get("value"), 
						(String)mapJson.get("desc"), 
						interiorCode, 
						plantNo, 
						plantPartCode,
						facilCode
				);
	}
	
	public 
	static String generateTagDvcEvent(String deviceType) {
		String time =  Utils.yyyyMMddHHmmssSSSHypen();
		List<Map<String, Object>> arTagDvcEventList = new ArrayList<Map<String, Object>>();

		for (int i=0;i<3;i++) {
			
			String type;
			if (deviceType.length()!=1) {
				if (i%3==0) {
					type = "I";
				} else if (i%3==1) {
					type = "P";
				} else {
					type = "C";
				}
			} else {
				type=deviceType;
			}
			
			arTagDvcEventList.add(
				new HashMap<String, Object>(){{
					put("dvc_type",type);
					List<Object> dataList = new ArrayList<Object>(); 
					for (int j=0;j<randomVal(20);j++) {
						dataList.add(
							new HashMap<String, String>() {{
								if (type.equals("I")) {
									String iotDevice =iotDeviceList[randomVal(iotDeviceList.length)];
									String interiorCode=iotDevice.substring(0,3);
									put("device_id", iotDevice);
									put("event_level", eventLevels[randomVal(eventLevels.length)]); //A:알람, F:화재, C: critical
									put("val_type", valTypesIot[randomVal(valTypesIot.length)]);
									put("value", Double.toString(FakeDataUtil.tempLowVal()));
									put("plant_type", "TBN");
									put("interior", interiorCode);
								} else if (type.equals("P")) {
									//{"M1","FP","FP1","1FP-TE01A","1"},
									String[] arTagMain = tagMainList[randomVal(tagMainList.length)];
									put("device_id", arTagMain[3]);
									put("event_level", eventLevels[randomVal(eventLevels.length)]); //A:알람, F:화재, C: critical
									put("val_type", valTypesTag[randomVal(valTypesTag.length)]);
									put("value", Double.toString(FakeDataUtil.tempMidVal()));
									put("plant_type", "TBN");
									put("plant_no", arTagMain[0]);
									put("plant_part", arTagMain[1]);
								} else if (type.equals("C")) {
									String etcDevice =cctvSampleList[randomVal(cctvSampleList.length)];
									String interiorCode=etcDevice.substring(0,3);
									put("device_id", etcDevice);
									put("event_level", eventLevels[randomVal(eventLevels.length)]); //A:알람, F:화재, C: critical
									put("val_type", "FLM");
									put("value", Double.toString(FakeDataUtil.randomVal(1)));
									put("plant_type", "TBN");
									put("interior", interiorCode);
								}
								put("time", time);
							}}
						);
					}
					put("data", dataList);
				}}
			);
		}
		return JsonUtils.toJson(
				new HashMap<String, List<Map<String,Object>>>(){{
					put("dvcevent", arTagDvcEventList);
		}}).toString();
	}
	
	public static List<TagDvcEventHist> getListTagDvcEvent(String jsonStr) {
		System.out.println(jsonStr);
		return getListTagDvcEvent(JsonUtils.toObject(jsonStr, Map.class));
	}
	public static List<TagDvcEventHist> getListTagDvcEvent(Map<String, Object> mapTagDvcData) {
		//Map<String, Object> mapTagDvcData = JsonUtils.toObject(jsonStr, Map.class);

		List<TagDvcEventHist> result = new ArrayList<TagDvcEventHist>();
		((List<Map<String, Object>>)mapTagDvcData.get("dvcevent")).forEach(e -> {
			//String plantNo = (String)e.get("plant_no");
			((List<Map<String, Object>>)e.get("data")).forEach(ex -> {
				//String createdDtm, String deviceType, String deviceId, String valType, String eventLevel,String eventVal, String interiorCode, String plantNo, String plantPartCode, String facilCode
				if ("P".equals((String)e.get("dvc_type"))) {
					result.add(new TagDvcEventHist(
							(String)ex.get("time"),
							"P",
							(String)ex.get("device_id"),
							(String)ex.get("val_type"),
							(String)ex.get("event_level"),
							(String)ex.get("value"),
							null,
							(String)ex.get("plant_no"),
							(String)ex.get("plant_part"),
							null
						));
				} else if ("I".equals((String)e.get("dvc_type"))) {
					result.add(new TagDvcEventHist(
							(String)ex.get("time"),
							"I",
							(String)ex.get("device_id"),
							(String)ex.get("val_type"),
							(String)ex.get("event_level"),
							(String)ex.get("value"),
							(String)ex.get("interior"),
							null,
							null,
							null
						));
					
				} else {
					result.add(new TagDvcEventHist(
							(String)ex.get("time"),
							"C",
							(String)ex.get("device_id"),
							(String)ex.get("val_type"),
							(String)ex.get("event_level"),
							(String)ex.get("value"),
							(String)ex.get("interior"),
							null,
							null,
							null
						));
					
				}
			});
		});
		return result;
	}
	
//
//	public 
//	static String generateKfslTotalResult(List<CommonCode> stepList, List<CommonCode> safetyGradeList) {
//		String time =  Utils.YYYYMMDD();
//		List<Map<String, Object>> arDataList = new ArrayList<Map<String, Object>>();
//
//		safetyGradeList.forEach(e-> {
//			String grade = e.getItemKey();
//			arDataList.add(
//					new HashMap<String, Object>(){{
//			            put("EVALUATION_DATE", time);
//			            put("EVALUATION", "FSA-T");
//			            put("SAFETY_GRADE", grade);
//			            put("SAFETY_SCORE", FakeDataUtil.tempValStr());
//			            put("SAFETY_TYPE1", FakeDataUtil.tempValStr());
//			            put("SAFETY_TYPE2", FakeDataUtil.tempValStr());
//			            put("REPORT_URL", "http://tool.kfsl.co.kr/report/writeReport/6/990/4");
//			            put("STEP1_RESULT", 
//			            	stepList.stream().map(e-> {
//			            		String stepName = e.getItemValue();
//			            		new HashMap<String, String>() {{
//			            			put("STEP1_NAME",stepName);
//			            			put("STEP1_JISU","0");
//			            		}}
//			            	}));
//			            }});
//					}});
//		});
//
//		return JsonUtils.toJson(
//				new HashMap<String, Object>(){{
//					put("result", Boolean.TRUE);
//					put("data", arDataList);
//		}}).toString();
//	}
//	

	
	static final public String[] eventLevels = {"alarm","fire","critical"}; //A:알람, F:화재, C: critical
	static final public String[] eventTypes = {"I","P","C"}; // I:센서, P:태그, C:CCTV
	static final public String[] eventTypesRandom = {"I","P","P","I","C","P","I","I","I","P","I","P","I","I",}; // I:센서, P:태그, C:CCTV
	static final public String[] tagSampleList = {"1FP-VE-11B","1FP-VE-05B","1FP-VE-06B","1MAV10CT025","1MAV30CT005","1MAV30CT010","1MAV40CT005","1MAV40CT015"};
	static final public String[] cctvSampleList = {"ST40000C01","ST40000C02","SC10000C01","SC20000C01","SC20000C02","SU10000C01"};
	static final public String[] eventKinds = {"SMK", "TMP", "VIB", "POS", "HUM", "CO", "SPD"};// – 연기, 온도, 진동, 위치, 습도, CO
	static final public String[] valTypesTag= {"POS", "VIB","TMP","XY", "SPEED", "EXPS"};// – 연기, 온도, 진동, 위치, 습도, CO
	static final public String[] valTypesIot = {"SMK", "TEMP", "CO", "HUM", "FLM"};// – 연기, 온도, 진동, 위치, 습도, CO

	static final public String[] plantList = {"M1","M2"}; 
	static final public String[] plantList2 = {"M1","M2","MC"}; 
	static final public String[] plantPartList = {"FP","MAA","MAV","MAB","MAK","MKD"};
	
	static final public String[] areaCodeList = {"TURBIN","MCTRL","UNDERCBL","INVERTER"};
	static final public String[] interiorList = {"ST3","SC3","SC2","SC1","SU1","SI2","SI1","SI0"};
	
	static final public String[] iotDeviceList = {"ST30101N01","ST30101N02","ST30101N03","ST30102N01","ST30102N02","ST30103N01","ST30103N02","ST30104N01","ST30104N02","ST30104N03","SC10101N01","SC10101N02","SC10102N01","SC10102N02","SC10103N01","SC10103N02","SC10103N03","SC10104N01","SC10104N02","SC10104N03","SC20101N01","SC20101N02","SC20101N03","SC20201N01","SC20201N02","SC20201N03","SC20202N01","SC20202N02","SC20203N01","SC20203N02","SC30101N01","SC30102N01","SC30102N02","SC30102N03","SC30102N04","SU10101N01","SU10101N02","SU10102N01","SU10102N02","SU10103N01","SU10103N02","SU10104N01","SU10104N02","SI00103N01","SI00103N02","SI00103N03","SI00103N04","SI10101N01","SI10101N02","SI10101N03","SI10101N04","SI20101N01","SI20101N02","SI20101N03","SI20102N01","SI20102N02","SI20102N03","SI20102N04","SI20102N05"};
	static final public String[] iotDeviceFields = {"time","device_id","humid","smoke","temp","co","flame"};
	static final public String[] iotMaFields = {"time","max_humid","max_smoke","max_temp","max_co","max_flame","avg_humid","avg_smoke","avg_temp","avg_co","avg_flame"};
	static final public String[] iotFireIdxFields = {"time","fire_idx","max_fire_idx"};
	static final public String[] tagDataFields1 = {"plant_no","plant_part","facil_code","data"};
	static final public String[] tagDataFields2 = {"time","tagname","val","seq","status","max_val"};
	static final public String[] tagFireIdxFields = {"time","fire_idx","max_fire_idx","facil_code"};
	static final public String[] dvcEventFields = {"time","device_id","evt_level","val_type","value","plant_type","plant_no","plant_part","interior"};
	
	static final public String[] eventFields = {"type","level","id","kind","value","desc","time"};
	static final public String[][] facilList ={{"M1","FP","FP1"},
			{"M1","FP","FP2"},
			{"M1","FP","FP3"},
			{"M1","FP","FP4"},
			{"M1","FP","FP5"},
			{"M1","FP","FP6"},
			{"M1","FP","FP7"},
			{"M1","MAA","MA1"},
			{"M1","MAA","MA2"},
			{"M1","MAA","MA3"},
			{"M1","MAA","MA4"},
			{"M1","MAA","MA5"},
			{"M1","MAA","MA6"},
			{"M1","MAK","MK1"},
			{"M1","MAK","MK2"},
			{"M1","MAK","MK3"},
			{"M1","MAK","MK4"},
			{"M1","MAK","MK5"},
			{"M1","MAK","MK6"},
			{"M1","MKD","MD1"},
			{"M1","MKD","MD2"},
			{"M1","MKD","MD3"},
			{"M1","MKD","MD4"},
			{"M1","MAB","MB1"},
			{"M1","MAB","MB2"},
			{"M1","MAB","MB3"},
			{"M1","MAB","MB4"},
			{"M1","MAB","MB5"},
			{"M1","MAB","MB6"},
			{"M1","MAV","MV1"},
			{"M1","MAV","MV2"},
			{"M1","MAV","MV3"},
			{"M2","FP","FP1"},
			{"M2","FP","FP2"},
			{"M2","FP","FP3"},
			{"M2","FP","FP4"},
			{"M2","FP","FP5"},
			{"M2","FP","FP6"},
			{"M2","FP","FP7"},
			{"M2","MAA","MA1"},
			{"M2","MAA","MA2"},
			{"M2","MAA","MA3"},
			{"M2","MAA","MA4"},
			{"M2","MAA","MA5"},
			{"M2","MAA","MA6"},
			{"M2","MAK","MK1"},
			{"M2","MAK","MK2"},
			{"M2","MAK","MK3"},
			{"M2","MAK","MK4"},
			{"M2","MAK","MK5"},
			{"M2","MAK","MK6"},
			{"M2","MKD","MD1"},
			{"M2","MKD","MD2"},
			{"M2","MKD","MD3"},
			{"M2","MKD","MD4"},
			{"M2","MAB","MB1"},
			{"M2","MAB","MB2"},
			{"M2","MAB","MB3"},
			{"M2","MAB","MB4"},
			{"M2","MAB","MB5"},
			{"M2","MAB","MB6"},
			{"M2","MAV","MV1"},
			{"M2","MAV","MV2"},
			{"M2","MAV","MV3"},
};
	static final public String[][] tagMainList = {
			{"M1","FP","FP1","1FP-TE01A","1"},
			{"M1","FP","FP1","1FP-TE01B","2"},
			{"M1","FP","FP1","1FP-TE02A","3"},
			{"M1","FP","FP1","1FP-TE02B","4"},
			{"M1","FP","FP2","1FP-TE03A","1"},
			{"M1","FP","FP2","1FP-TE03B","2"},
			{"M1","FP","FP2","1FP-TE04A","3"},
			{"M1","FP","FP2","1FP-TE04B","4"},
			{"M1","FP","FP3","1FP-TE09A","1"},
			{"M1","FP","FP3","1FP-TE09B","2"},
			{"M1","FP","FP3","1FP-TE10A","3"},
			{"M1","FP","FP3","1FP-TE10B","4"},
			{"M1","FP","FP4","A1FP-VE-01A","1"},
			{"M1","FP","FP4","A1FP-VE-02A","2"},
			{"M1","FP","FP4","A1FP-VE-01B","3"},
			{"M1","FP","FP4","A1FP-VE-02B","4"},
			{"M1","FP","FP4","A1FP-VE-03A","5"},
			{"M1","FP","FP4","A1FP-VE-04A","6"},
			{"M1","FP","FP4","A1FP-VE-03B","7"},
			{"M1","FP","FP4","A1FP-VE-04B","8"},
			{"M1","FP","FP5","A1FP-VE-08A","1"},
			{"M1","FP","FP5","A1FP-VE-09A","2"},
			{"M1","FP","FP5","A1FP-VE-08B","3"},
			{"M1","FP","FP5","A1FP-VE-09B","4"},
			{"M1","FP","FP5","A1FP-VE-10A","5"},
			{"M1","FP","FP5","A1FP-VE-11A","6"},
			{"M1","FP","FP5","A1FP-VE-10B","7"},
			{"M1","FP","FP5","A1FP-VE-11B","8"},
			{"M1","FP","FP6","A1FP-VE-05A","1"},
			{"M1","FP","FP6","A1FP-VE-06A","1"},
			{"M1","FP","FP6","A1FP-VE-05B","2"},
			{"M1","FP","FP6","A1FP-VE-06B","2"},
			{"M1","FP","FP7","1FP-VF-IT02","1"},
			{"M1","FP","FP7","1FP-VF-SPD-RPM","2"},
			{"M1","MAA","MA1","1MAA10CT010XE01","1"},
			{"M1","MAA","MA1","1MAA10CT020XE01","2"},
			{"M1","MAA","MA1","1MAA10CT005XE01","3"},
			{"M1","MAA","MA1","1MAA10CT015XE01","4"},
			{"M1","MAA","MA2","1MAA10CT025XE01","1"},
			{"M1","MAA","MA2","1MAA10CT030XE01","2"},
			{"M1","MAA","MA3","1MAA10CG005XE01","1"},
			{"M1","MAA","MA3","1MAA10CG010XE01","1"},
			{"M1","MAA","MA3","1MAA10CG011XE01","1"},
			{"M1","MAA","MA4","1MAA10CY005XE01","1"},
			{"M1","MAA","MA4","1MAA10CY010XE01","2"},
			{"M1","MAA","MA4","1MAA10CY105XE01","3"},
			{"M1","MAA","MA4","1MAA10CY106XE01","3"},
			{"M1","MAA","MA4","1MAA10CY107XE01","3"},
			{"M1","MAA","MA5","1MAA10CY015XE01","1"},
			{"M1","MAA","MA5","1MAA10CY020XE01","2"},
			{"M1","MAA","MA5","1MAA10CY110XE01","3"},
			{"M1","MAA","MA5","1MAA10CY111XE01","3"},
			{"M1","MAA","MA5","1MAA10CY112XE01","3"},
			{"M1","MAA","MA6","1MAA10CS905ZE01","1"},
			{"M1","MAB","MB1","1MAB10CT010XE01","1"},
			{"M1","MAB","MB1","1MAB10CT020XE01","2"},
			{"M1","MAB","MB1","1MAB10CT005XE01","3"},
			{"M1","MAB","MB1","1MAB10CT015XE01","4"},
			{"M1","MAB","MB2","1MAB10CT025XE01","1"},
			{"M1","MAB","MB2","1MAB10CT030XE01","2"},
			{"M1","MAB","MB3","1MAB10CG005XE01","1"},
			{"M1","MAB","MB3","1MAB10CG010XE01","1"},
			{"M1","MAB","MB3","1MAB10CG011XE01","1"},
			{"M1","MAB","MB4","1MAB10CG020XE02","1"},
			{"M1","MAB","MB5","1MAB10CY005XE01","1"},
			{"M1","MAB","MB5","1MAB10CY010XE01","2"},
			{"M1","MAB","MB5","1MAB10CY105XE01","3"},
			{"M1","MAB","MB5","1MAB10CY106XE01","3"},
			{"M1","MAB","MB5","1MAB10CY107XE01","3"},
			{"M1","MAB","MB6","1MAB10CY015XE01","1"},
			{"M1","MAB","MB6","1MAB10CY020XE01","1"},
			{"M1","MAB","MB6","1MAB10CY110XE01","3"},
			{"M1","MAB","MB6","1MAB10CY111XE01","3"},
			{"M1","MAB","MB6","1MAB10CY112XE01","3"},
			{"M1","MAK","MK1","1MAK10CT005XE01","1"},
			{"M1","MAK","MK1","1MAK10CT010XE01","2"},
			{"M1","MAK","MK2","1MAK10CT025XE01","1"},
			{"M1","MAK","MK2","1MAK10CT030XE01","2"},
			{"M1","MAK","MK3","1MAK10CY008XE01","1"},
			{"M1","MAK","MK3","1MAK10CY009XE01","2"},
			{"M1","MAK","MK4","1MAK10CY028XE01","1"},
			{"M1","MAK","MK4","1MAK10CY029XE01","2"},
			{"M1","MAK","MK5","1MAK10CY005XE01","1"},
			{"M1","MAK","MK5","1MAK10CY006XE01","1"},
			{"M1","MAK","MK5","1MAK10CY007XE01","1"},
			{"M1","MAK","MK6","1MAK10CY025XE01","1"},
			{"M1","MAV","MV1","1MAV10CT025XE01","1"},
			{"M1","MAV","MV2","1MAV30CT005XE01","1"},
			{"M1","MAV","MV2","1MAV30CT010XE01","2"},
			{"M1","MAV","MV3","1MAV40CT005XE02","1"},
			{"M1","MAV","MV3","1MAV40CT015XE02","1"},
			{"M1","MAV","MV3","1MAV40CT020XE02","1"},
			{"M1","MKD","MD1","1MKD10CY030XE02","1"},
			{"M1","MKD","MD1","1MKD10CY040XE02","2"},
			{"M1","MKD","MD2","1MKD20CY030XE02","1"},
			{"M1","MKD","MD2","1MKD20CY040XE02","2"},
			{"M1","MKD","MD3","1MKD10CY105XE01","1"},
			{"M1","MKD","MD3","1MKD10CY106XE01","1"},
			{"M1","MKD","MD3","1MKD10CY107XE01","1"},
			{"M1","MKD","MD3","1MKD20CY105XE01","2"},
			{"M1","MKD","MD3","1MKD20CY106XE01","2"},
			{"M1","MKD","MD3","1MKD20CY107XE01","2"},
			{"M1","MKD","MD4","1MKD20CT010XE01","1"},
			{"M1","MKD","MD4","1MKD10CT010XE01","2"},
			{"M2","FP","FP1","2FP-TE01A","1"},
			{"M2","FP","FP1","2FP-TE01B","2"},
			{"M2","FP","FP1","2FP-TE02A","3"},
			{"M2","FP","FP1","2FP-TE02B","4"},
			{"M2","FP","FP2","2FP-TE03A","1"},
			{"M2","FP","FP2","2FP-TE03B","2"},
			{"M2","FP","FP2","2FP-TE04A","3"},
			{"M2","FP","FP2","2FP-TE04B","4"},
			{"M2","FP","FP3","2FP-TE09A","1"},
			{"M2","FP","FP3","2FP-TE09B","2"},
			{"M2","FP","FP3","2FP-TE10A","3"},
			{"M2","FP","FP3","2FP-TE10B","4"},
			{"M2","FP","FP4","A2FP-VE-01A","1"},
			{"M2","FP","FP4","A2FP-VE-02A","2"},
			{"M2","FP","FP4","A2FP-VE-01B","3"},
			{"M2","FP","FP4","A2FP-VE-02B","4"},
			{"M2","FP","FP4","A2FP-VE-03A","5"},
			{"M2","FP","FP4","A2FP-VE-04A","6"},
			{"M2","FP","FP4","A2FP-VE-03B","7"},
			{"M2","FP","FP4","A2FP-VE-04B","8"},
			{"M2","FP","FP5","A2FP-VE-08A","1"},
			{"M2","FP","FP5","A2FP-VE-09A","2"},
			{"M2","FP","FP5","A2FP-VE-08B","3"},
			{"M2","FP","FP5","A2FP-VE-09B","4"},
			{"M2","FP","FP5","A2FP-VE-10A","5"},
			{"M2","FP","FP5","A2FP-VE-11A","6"},
			{"M2","FP","FP5","A2FP-VE-10B","7"},
			{"M2","FP","FP5","A2FP-VE-11B","8"},
			{"M2","FP","FP6","A2FP-VE-05A","1"},
			{"M2","FP","FP6","A2FP-VE-06A","1"},
			{"M2","FP","FP6","A2FP-VE-05B","2"},
			{"M2","FP","FP6","A2FP-VE-06B","2"},
			{"M2","FP","FP7","2FP-VF-IT02","1"},
			{"M2","FP","FP7","2FP-VF-SPD-RPM","2"},
			{"M2","MAA","MA1","2MAA10CT010XE01","1"},
			{"M2","MAA","MA1","2MAA10CT020XE01","2"},
			{"M2","MAA","MA1","2MAA10CT005XE01","3"},
			{"M2","MAA","MA1","2MAA10CT015XE01","4"},
			{"M2","MAA","MA2","2MAA10CT025XE01","1"},
			{"M2","MAA","MA2","2MAA10CT030XE01","2"},
			{"M2","MAA","MA3","2MAA10CG005XE01","1"},
			{"M2","MAA","MA3","2MAA10CG010XE01","1"},
			{"M2","MAA","MA3","2MAA10CG011XE01","1"},
			{"M2","MAA","MA4","2MAA10CY005XE01","1"},
			{"M2","MAA","MA4","2MAA10CY010XE01","2"},
			{"M2","MAA","MA4","2MAA10CY105XE01","3"},
			{"M2","MAA","MA4","2MAA10CY106XE01","3"},
			{"M2","MAA","MA4","2MAA10CY107XE01","3"},
			{"M2","MAA","MA5","2MAA10CY015XE01","1"},
			{"M2","MAA","MA5","2MAA10CY020XE01","2"},
			{"M2","MAA","MA5","2MAA10CY110XE01","3"},
			{"M2","MAA","MA5","2MAA10CY111XE01","3"},
			{"M2","MAA","MA5","2MAA10CY112XE01","3"},
			{"M2","MAA","MA6","2MAA10CS905ZE01","1"},
			{"M2","MAB","MB1","2MAB10CT010XE01","1"},
			{"M2","MAB","MB1","2MAB10CT020XE01","2"},
			{"M2","MAB","MB1","2MAB10CT005XE01","3"},
			{"M2","MAB","MB1","2MAB10CT015XE01","4"},
			{"M2","MAB","MB2","2MAB10CT025XE01","1"},
			{"M2","MAB","MB2","2MAB10CT030XE01","2"},
			{"M2","MAB","MB3","2MAB10CG005XE01","1"},
			{"M2","MAB","MB3","2MAB10CG010XE01","1"},
			{"M2","MAB","MB3","2MAB10CG011XE01","1"},
			{"M2","MAB","MB4","2MAB10CG020XE02","1"},
			{"M2","MAB","MB5","2MAB10CY005XE01","1"},
			{"M2","MAB","MB5","2MAB10CY010XE01","2"},
			{"M2","MAB","MB5","2MAB10CY105XE01","3"},
			{"M2","MAB","MB5","2MAB10CY106XE01","3"},
			{"M2","MAB","MB5","2MAB10CY107XE01","3"},
			{"M2","MAB","MB6","2MAB10CY015XE01","1"},
			{"M2","MAB","MB6","2MAB10CY020XE01","2"},
			{"M2","MAB","MB6","2MAB10CY110XE01","3"},
			{"M2","MAB","MB6","2MAB10CY111XE01","3"},
			{"M2","MAB","MB6","2MAB10CY112XE01","3"},
			{"M2","MAK","MK1","2MAK10CT005XE01","1"},
			{"M2","MAK","MK1","2MAK10CT010XE01","2"},
			{"M2","MAK","MK2","2MAK10CT025XE01","1"},
			{"M2","MAK","MK2","2MAK10CT030XE01","2"},
			{"M2","MAK","MK3","2MAK10CY008XE01","1"},
			{"M2","MAK","MK3","2MAK10CY009XE01","2"},
			{"M2","MAK","MK4","2MAK10CY028XE01","1"},
			{"M2","MAK","MK4","2MAK10CY029XE01","2"},
			{"M2","MAK","MK5","2MAK10CY005XE01","1"},
			{"M2","MAK","MK5","2MAK10CY006XE01","1"},
			{"M2","MAK","MK5","2MAK10CY007XE01","1"},
			{"M2","MAK","MK6","2MAK10CY025XE01","1"},
			{"M2","MAV","MV1","2MAV10CT025XE01","1"},
			{"M2","MAV","MV2","2MAV30CT005XE01","1"},
			{"M2","MAV","MV2","2MAV30CT010XE01","2"},
			{"M2","MAV","MV3","2MAV40CT005XE02","1"},
			{"M2","MAV","MV3","2MAV40CT015XE02","1"},
			{"M2","MAV","MV3","2MAV40CT020XE02","1"},
			{"M2","MKD","MD1","2MKD10CY030XE02","1"},
			{"M2","MKD","MD1","2MKD10CY040XE02","2"},
			{"M2","MKD","MD2","2MKD20CY030XE02","1"},
			{"M2","MKD","MD2","2MKD20CY040XE02","2"},
			{"M2","MKD","MD3","2MKD10CY105XE01","1"},
			{"M2","MKD","MD3","2MKD10CY106XE01","1"},
			{"M2","MKD","MD3","2MKD10CY107XE01","1"},
			{"M2","MKD","MD3","2MKD20CY105XE01","2"},
			{"M2","MKD","MD3","2MKD20CY106XE01","2"},
			{"M2","MKD","MD3","2MKD20CY107XE01","2"},
			{"M2","MKD","MD4","2MKD20CT010XE01","1"},
			{"M2","MKD","MD4","2MKD10CT010XE01","2"},
};

}
