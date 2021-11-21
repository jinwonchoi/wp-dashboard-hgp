package com.gencode.issuetool.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.exception.NotFoundException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.IotDeviceInfo;
import com.gencode.issuetool.service.IotDeviceInfoService;

@RestController
@RequestMapping("/plantinfo/iotdevice")
@CrossOrigin(origins = "${cors_url}")
public class IotDeviceInfoController {

	private final static Logger logger = LoggerFactory.getLogger(IotDeviceInfoController.class);
	@Autowired
	private IotDeviceInfoService iotDeviceInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<IotDeviceInfo> add(@RequestBody IotDeviceInfo iotDeviceInfo) {
		try {
			logger.info(iotDeviceInfo.toString());
			IotDeviceInfo resultIotDeviceInfo = iotDeviceInfoService.register(iotDeviceInfo).get();
			return ResultObj.<IotDeviceInfo>success(resultIotDeviceInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<IotDeviceInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<IotDeviceInfo> update(@RequestBody IotDeviceInfo iotDeviceInfo) {
		try {
			logger.info(iotDeviceInfo.toString());
			IotDeviceInfo resultIotDeviceInfo = iotDeviceInfoService.update(iotDeviceInfo).get();
			return ResultObj.<IotDeviceInfo>success(resultIotDeviceInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<IotDeviceInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<IotDeviceInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			iotDeviceInfoService.delete(id);
			return ResultObj.<String>success();
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<String>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<String>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public PageResultObj<List<IotDeviceInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<IotDeviceInfo>>> list = iotDeviceInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<IotDeviceInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<IotDeviceInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<IotDeviceInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<IotDeviceInfo>> loadAll() {
		try {
			Optional<List<IotDeviceInfo>> list = iotDeviceInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<IotDeviceInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<IotDeviceInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<IotDeviceInfo>>errorUnknown();
		}
	}

}
