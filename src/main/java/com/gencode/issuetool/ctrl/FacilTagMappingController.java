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
import com.gencode.issuetool.obj.FacilTagMapping;
import com.gencode.issuetool.service.FacilTagMappingService;

@RestController
@RequestMapping("/plantinfo/tagmapping")
@CrossOrigin(origins = "${cors_url}")
public class FacilTagMappingController {

	private final static Logger logger = LoggerFactory.getLogger(FacilTagMappingController.class);
	@Autowired
	private FacilTagMappingService facilTagMappingService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<FacilTagMapping> add(@RequestBody FacilTagMapping facilTagMapping) {
		try {
			logger.info(facilTagMapping.toString());
			FacilTagMapping resultFacilTagMapping = facilTagMappingService.register(facilTagMapping).get();
			return ResultObj.<FacilTagMapping>success(resultFacilTagMapping);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<FacilTagMapping>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<FacilTagMapping> update(@RequestBody FacilTagMapping facilTagMapping) {
		try {
			logger.info(facilTagMapping.toString());
			FacilTagMapping resultFacilTagMapping = facilTagMappingService.update(facilTagMapping).get();
			return ResultObj.<FacilTagMapping>success(resultFacilTagMapping);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<FacilTagMapping>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<FacilTagMapping>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	ResultObj<String> delete(@RequestBody FacilTagMapping facilTagMapping) {
		try {
			logger.info(facilTagMapping.toString());
			facilTagMappingService.delete(facilTagMapping);
			return ResultObj.<String>success();
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<String>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<String>errorUnknown();
		}
	}

	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> deleteByRuleId(@PathVariable(name = "id") long id) {
		try {
			facilTagMappingService.deleteByRuleId(id);
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
	public PageResultObj<List<FacilTagMapping>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<FacilTagMapping>>> list = facilTagMappingService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<FacilTagMapping>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<FacilTagMapping>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<FacilTagMapping>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<FacilTagMapping>> loadAll() {
		try {
			Optional<List<FacilTagMapping>> list = facilTagMappingService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<FacilTagMapping>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<FacilTagMapping>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<FacilTagMapping>>errorUnknown();
		}
	}

}
