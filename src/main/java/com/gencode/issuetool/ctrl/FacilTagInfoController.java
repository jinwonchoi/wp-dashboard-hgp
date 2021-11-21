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
import com.gencode.issuetool.obj.FacilTagInfo;
import com.gencode.issuetool.service.FacilTagInfoService;

@RestController
@RequestMapping("/plantinfo/faciltag")
@CrossOrigin(origins = "${cors_url}")
public class FacilTagInfoController {

	private final static Logger logger = LoggerFactory.getLogger(FacilTagInfoController.class);
	@Autowired
	private FacilTagInfoService facilTagInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<FacilTagInfo> add(@RequestBody FacilTagInfo facilTagInfo) {
		try {
			logger.info(facilTagInfo.toString());
			FacilTagInfo resultFacilTagInfo = facilTagInfoService.register(facilTagInfo).get();
			return ResultObj.<FacilTagInfo>success(resultFacilTagInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<FacilTagInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<FacilTagInfo> update(@RequestBody FacilTagInfo facilTagInfo) {
		try {
			logger.info(facilTagInfo.toString());
			FacilTagInfo resultFacilTagInfo = facilTagInfoService.update(facilTagInfo).get();
			return ResultObj.<FacilTagInfo>success(resultFacilTagInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<FacilTagInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<FacilTagInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			facilTagInfoService.delete(id);
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
	public PageResultObj<List<FacilTagInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<FacilTagInfo>>> list = facilTagInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<FacilTagInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<FacilTagInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<FacilTagInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<FacilTagInfo>> loadAll() {
		try {
			Optional<List<FacilTagInfo>> list = facilTagInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<FacilTagInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<FacilTagInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<FacilTagInfo>>errorUnknown();
		}
	}

}
