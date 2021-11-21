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
import com.gencode.issuetool.obj.AreaInfo;
import com.gencode.issuetool.service.AreaInfoService;

@RestController
@RequestMapping("/plantinfo/area")
@CrossOrigin(origins = "${cors_url}")
public class AreaInfoController {

	private final static Logger logger = LoggerFactory.getLogger(AreaInfoController.class);
	@Autowired
	private AreaInfoService areaInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<AreaInfo> add(@RequestBody AreaInfo areaInfo) {
		try {
			logger.info(areaInfo.toString());
			AreaInfo resultAreaInfo = areaInfoService.register(areaInfo).get();
			return ResultObj.<AreaInfo>success(resultAreaInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<AreaInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<AreaInfo> update(@RequestBody AreaInfo areaInfo) {
		try {
			logger.info(areaInfo.toString());
			AreaInfo resultAreaInfo = areaInfoService.update(areaInfo).get();
			return ResultObj.<AreaInfo>success(resultAreaInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<AreaInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<AreaInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			areaInfoService.delete(id);
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
	public PageResultObj<List<AreaInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<AreaInfo>>> list = areaInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<AreaInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<AreaInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<AreaInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<AreaInfo>> loadAll() {
		try {
			Optional<List<AreaInfo>> list = areaInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<AreaInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<AreaInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<AreaInfo>>errorUnknown();
		}
	}

}
