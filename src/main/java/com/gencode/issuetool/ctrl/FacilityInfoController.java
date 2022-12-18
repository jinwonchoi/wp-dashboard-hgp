/**=========================================================================================
<overview>설비정보기준정보관련 controller
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
import com.gencode.issuetool.obj.FacilityInfo;
import com.gencode.issuetool.service.FacilityInfoService;

@RestController
@RequestMapping("/plantinfo/facility")
@CrossOrigin(origins = "${cors_url}")
public class FacilityInfoController {

	private final static Logger logger = LoggerFactory.getLogger(FacilityInfoController.class);
	@Autowired
	private FacilityInfoService facilityInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<FacilityInfo> add(@RequestBody FacilityInfo facilityInfo) {
		try {
			logger.info(facilityInfo.toString());
			FacilityInfo resultFacilityInfo = facilityInfoService.register(facilityInfo).get();
			return ResultObj.<FacilityInfo>success(resultFacilityInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<FacilityInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<FacilityInfo> update(@RequestBody FacilityInfo facilityInfo) {
		try {
			logger.info(facilityInfo.toString());
			FacilityInfo resultFacilityInfo = facilityInfoService.update(facilityInfo).get();
			return ResultObj.<FacilityInfo>success(resultFacilityInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<FacilityInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<FacilityInfo>errorUnknown();
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			facilityInfoService.delete(id);
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
	public PageResultObj<List<FacilityInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<FacilityInfo>>> list = facilityInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<FacilityInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<FacilityInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<FacilityInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<FacilityInfo>> loadAll() {
		try {
			Optional<List<FacilityInfo>> list = facilityInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<FacilityInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<FacilityInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<FacilityInfo>>errorUnknown();
		}
	}

}
