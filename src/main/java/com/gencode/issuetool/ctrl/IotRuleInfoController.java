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
import com.gencode.issuetool.obj.IotRuleInfo;
import com.gencode.issuetool.service.IotRuleInfoService;

@RestController
@RequestMapping("/plantinfo/iotrule")
@CrossOrigin(origins = "${cors_url}")
public class IotRuleInfoController {

	private final static Logger logger = LoggerFactory.getLogger(IotRuleInfoController.class);
	@Autowired
	private IotRuleInfoService iotRuleInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<IotRuleInfo> add(@RequestBody IotRuleInfo iotRuleInfo) {
		try {
			logger.info(iotRuleInfo.toString());
			IotRuleInfo resultIotRuleInfo = iotRuleInfoService.register(iotRuleInfo).get();
			return ResultObj.<IotRuleInfo>success(resultIotRuleInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<IotRuleInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<IotRuleInfo> update(@RequestBody IotRuleInfo iotRuleInfo) {
		try {
			logger.info(iotRuleInfo.toString());
			IotRuleInfo resultIotRuleInfo = iotRuleInfoService.update(iotRuleInfo).get();
			return ResultObj.<IotRuleInfo>success(resultIotRuleInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<IotRuleInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<IotRuleInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			iotRuleInfoService.delete(id);
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
	public PageResultObj<List<IotRuleInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<IotRuleInfo>>> list = iotRuleInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<IotRuleInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<IotRuleInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<IotRuleInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<IotRuleInfo>> loadAll() {
		try {
			Optional<List<IotRuleInfo>> list = iotRuleInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<IotRuleInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<IotRuleInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<IotRuleInfo>>errorUnknown();
		}
	}

}
