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
import com.gencode.issuetool.obj.FacilRuleInfo;
import com.gencode.issuetool.service.FacilRuleInfoService;

@RestController
@RequestMapping("/plantinfo/facilrule")
@CrossOrigin(origins = "${cors_url}")
public class FacilRuleInfoController {

	private final static Logger logger = LoggerFactory.getLogger(FacilRuleInfoController.class);
	@Autowired
	private FacilRuleInfoService facilRuleInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<FacilRuleInfo> add(@RequestBody FacilRuleInfo facilRuleInfo) {
		try {
			logger.info(facilRuleInfo.toString());
			FacilRuleInfo resultFacilRuleInfo = facilRuleInfoService.register(facilRuleInfo).get();
			return ResultObj.<FacilRuleInfo>success(resultFacilRuleInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<FacilRuleInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<FacilRuleInfo> update(@RequestBody FacilRuleInfo facilRuleInfo) {
		try {
			logger.info(facilRuleInfo.toString());
			FacilRuleInfo resultFacilRuleInfo = facilRuleInfoService.update(facilRuleInfo).get();
			return ResultObj.<FacilRuleInfo>success(resultFacilRuleInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<FacilRuleInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<FacilRuleInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			facilRuleInfoService.delete(id);
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
	public PageResultObj<List<FacilRuleInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<FacilRuleInfo>>> list = facilRuleInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<FacilRuleInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<FacilRuleInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<FacilRuleInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<FacilRuleInfo>> loadAll() {
		try {
			Optional<List<FacilRuleInfo>> list = facilRuleInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<FacilRuleInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<FacilRuleInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<FacilRuleInfo>>errorUnknown();
		}
	}

}
