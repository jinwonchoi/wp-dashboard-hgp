/**=========================================================================================
<overview>기타장비기준정보관련 controller
  </overview>
==========================================================================================*/
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
import com.gencode.issuetool.obj.EtcDeviceInfo;
import com.gencode.issuetool.service.EtcDeviceInfoService;

@RestController
@RequestMapping("/plantinfo/etcdevice")
@CrossOrigin(origins = "${cors_url}")
public class EtcDeviceInfoController {

	private final static Logger logger = LoggerFactory.getLogger(EtcDeviceInfoController.class);
	@Autowired
	private EtcDeviceInfoService etcDeviceInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<EtcDeviceInfo> add(@RequestBody EtcDeviceInfo etcDeviceInfo) {
		try {
			logger.info(etcDeviceInfo.toString());
			EtcDeviceInfo resultEtcDeviceInfo = etcDeviceInfoService.register(etcDeviceInfo).get();
			return ResultObj.<EtcDeviceInfo>success(resultEtcDeviceInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<EtcDeviceInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<EtcDeviceInfo> update(@RequestBody EtcDeviceInfo etcDeviceInfo) {
		try {
			logger.info(etcDeviceInfo.toString());
			EtcDeviceInfo resultEtcDeviceInfo = etcDeviceInfoService.update(etcDeviceInfo).get();
			return ResultObj.<EtcDeviceInfo>success(resultEtcDeviceInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<EtcDeviceInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<EtcDeviceInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			etcDeviceInfoService.delete(id);
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
	public PageResultObj<List<EtcDeviceInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<EtcDeviceInfo>>> list = etcDeviceInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<EtcDeviceInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<EtcDeviceInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<EtcDeviceInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<EtcDeviceInfo>> loadAll() {
		try {
			Optional<List<EtcDeviceInfo>> list = etcDeviceInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<EtcDeviceInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<EtcDeviceInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<EtcDeviceInfo>>errorUnknown();
		}
	}

}
