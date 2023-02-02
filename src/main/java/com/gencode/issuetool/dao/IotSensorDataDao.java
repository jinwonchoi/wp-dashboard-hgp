package com.gencode.issuetool.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.obj.IotSensorData;
import com.gencode.issuetool.obj.UserInfo;

public interface IotSensorDataDao extends Dao<IotSensorData> {
 	Optional<List<IotSensorData>> searchExtrict(Map<String, String> map);
 	//Optional<PageResultObj<List<IotSensorData>>> listByCategory(PageRequest req) throws IOException;
}
