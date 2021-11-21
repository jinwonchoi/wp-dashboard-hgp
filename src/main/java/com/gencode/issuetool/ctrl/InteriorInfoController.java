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
import com.gencode.issuetool.obj.InteriorInfo;
import com.gencode.issuetool.service.InteriorInfoService;

@RestController
@RequestMapping("/plantinfo/interior")
@CrossOrigin(origins = "${cors_url}")
public class InteriorInfoController {

	private final static Logger logger = LoggerFactory.getLogger(InteriorInfoController.class);
	@Autowired
	private InteriorInfoService interiorInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<InteriorInfo> add(@RequestBody InteriorInfo interiorInfo) {
		try {
			logger.info(interiorInfo.toString());
			InteriorInfo resultInteriorInfo = interiorInfoService.register(interiorInfo).get();
			return ResultObj.<InteriorInfo>success(resultInteriorInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<InteriorInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<InteriorInfo> update(@RequestBody InteriorInfo interiorInfo) {
		try {
			logger.info(interiorInfo.toString());
			InteriorInfo resultInteriorInfo = interiorInfoService.update(interiorInfo).get();
			return ResultObj.<InteriorInfo>success(resultInteriorInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<InteriorInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<InteriorInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			interiorInfoService.delete(id);
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
	public PageResultObj<List<InteriorInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<InteriorInfo>>> list = interiorInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<InteriorInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<InteriorInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<InteriorInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<InteriorInfo>> loadAll() {
		try {
			Optional<List<InteriorInfo>> list = interiorInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<InteriorInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<InteriorInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<InteriorInfo>>errorUnknown();
		}
	}

}
