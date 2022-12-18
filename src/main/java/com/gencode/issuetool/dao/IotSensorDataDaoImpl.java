/**=========================================================================================
<overview>구역기준정보관련 DAO inteface정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.IotSensorData;
import com.gencode.issuetool.obj.User;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.service.LogpressoConnector;
import com.gencode.issuetool.util.JsonUtils;
import com.logpresso.client.Cursor;
import com.logpresso.client.Logpresso;
import com.logpresso.client.Tuple;

@Component("IoTSensorDataDao")
public class IotSensorDataDaoImpl extends AbstractLogpressoDaoImpl implements IotSensorDataDao {

	@Autowired
	LogpressoConnector conn;
	public IotSensorDataDaoImpl() {
		super();
	}
	
	@Override
	public Optional<IotSensorData> load(long id) {
		return null;
	}

	@Override
	public Optional<List<IotSensorData>> loadAll() {
		return null;
	}
		
	private IotSensorData toIoTSensorData(String logpressoStr) {
		List<Map<String, Object>> arJsonObjList = new ArrayList<Map<String, Object>>();
		arJsonObjList = JsonUtils.toObject(logpressoStr, List.class);
		System.out.println(arJsonObjList.get(0));
		
		Map<String, Object> arJsonObj = arJsonObjList.get(0);
		Map<String, String> valMap=(Map<String, String>)arJsonObj.get(Constant.IOT_DEVICE_VALUES.get());
		
		IotSensorData ioTSensorData = new IotSensorData();
		ioTSensorData.setDeviceId((String)arJsonObj.get(Constant.IOT_DEVICE_INDEX.get()));
		ioTSensorData.setCreatedDate((String)arJsonObj.get(Constant.IOT_DEVICE_TIME.get()));
		ioTSensorData.setHumid(Double.parseDouble(valMap.get(Constant.IOT_DEVICE_HUMID.get())));
		ioTSensorData.setSmoke(Double.parseDouble(valMap.get(Constant.IOT_DEVICE_SMOKE.get())));
		ioTSensorData.setTemperature(Double.parseDouble(valMap.get(Constant.IOT_DEVICE_TEMPERATURE.get())));
		ioTSensorData.setCoGas(Double.parseDouble(valMap.get(Constant.IOT_DEVICE_COGAS.get())));
		ioTSensorData.setFlare(valMap.get(Constant.IOT_DEVICE_HUMID.get()));
		return ioTSensorData;
	}

	@Override
	public long register(IotSensorData t) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public long delete(long id) {
		// TODO Auto-generated method stub
		return -1;
	}


	@Override
	public long update(IotSensorData t) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Optional<List<IotSensorData>> search(Map<String, String> map) {
		return null;
	}
	
	@Override
	public Optional<PageResultObj<List<IotSensorData>>> listByCategory(PageRequest req) throws IOException {
		Cursor cursor = null;
		Logpresso logpresso = null;

		try {
			List<IotSensorData> arResult  = new ArrayList<IotSensorData>();
			
			long offset = req.getOffset();
			long pageSize = req.getPageSize();
			
			String categoryId = req.getParamMap().get("categoryId");
			String deviceId = req.getSearchByOrMap().get("deviceId");
			//conn = new LogpressoConnector();
			//cursor = conn.executeQuery("proc sp_dsmain()");
			logpresso = conn.getConnection();
			cursor = conn.executeQuery(logpresso, String.format("proc sp_dsSubIoTList(\"%s\")", categoryId));
			//cursor = conn.executeQuery("table if_wth_fcst");
			int i = 0;
	        while (cursor.hasNext()) {
	        	Tuple tuple = cursor.next();
	        	//logger.info(tuple.get("_return").toString());
	        	if ( i >= offset && i < (offset+pageSize) ) {
	        		IotSensorData ioTSensorData = toIoTSensorData(tuple.get("_return").toString());
	        		if ( deviceId!=null&&!ioTSensorData.getDeviceId().contains(deviceId)) {
	        			continue;
	        		}
	        		arResult.add(ioTSensorData);		        	
	        	}
	        	i++;
	        }
			
	        PageResultObj<List<IotSensorData>> pageResultObj = new PageResultObj<List<IotSensorData>>();
			pageResultObj.setSuccess();
			pageResultObj.setItem(arResult);
			pageResultObj.setTotalCnt(i);
			pageResultObj.setPageNo(req.getPageNo());
			pageResultObj.setPageSize(req.getPageSize());

			return Optional.of(pageResultObj); 
		} finally {
	        if (cursor != null)
			cursor.close();
		    if (conn != null)
		    	conn.close(logpresso);
		}
	}


	@Override
	public Optional<PageResultObj<List<IotSensorData>>> search(PageRequest req) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getTotalCount(String query, MapSqlParameterSource namedParameters) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Optional<List<IotSensorData>> searchExtrict(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
