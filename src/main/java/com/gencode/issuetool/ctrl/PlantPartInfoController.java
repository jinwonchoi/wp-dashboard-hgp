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
import com.gencode.issuetool.obj.PlantPartInfo;
import com.gencode.issuetool.service.PlantPartInfoService;

@RestController
@RequestMapping("/plantinfo/plantPart")
@CrossOrigin(origins = "${cors_url}")
public class PlantPartInfoController {

	private final static Logger logger = LoggerFactory.getLogger(PlantPartInfoController.class);
	@Autowired
	private PlantPartInfoService plantPartInfoService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	ResultObj<PlantPartInfo> add(@RequestBody PlantPartInfo plantPartInfo) {
		try {
			logger.info(plantPartInfo.toString());
			PlantPartInfo resultPlantPartInfo = plantPartInfoService.register(plantPartInfo).get();
			return ResultObj.<PlantPartInfo>success(resultPlantPartInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<PlantPartInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResultObj<PlantPartInfo> update(@RequestBody PlantPartInfo plantPartInfo) {
		try {
			logger.info(plantPartInfo.toString());
			PlantPartInfo resultPlantPartInfo = plantPartInfoService.update(plantPartInfo).get();
			return ResultObj.<PlantPartInfo>success(resultPlantPartInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<PlantPartInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<PlantPartInfo>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResultObj<String> delete(@PathVariable(name = "id") long id) {
		try {
			plantPartInfoService.delete(id);
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
	public PageResultObj<List<PlantPartInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<PlantPartInfo>>> list = plantPartInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<PlantPartInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<PlantPartInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<PlantPartInfo>>errorUnknown();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/list")
	public ResultObj<List<PlantPartInfo>> loadAll() {
		try {
			Optional<List<PlantPartInfo>> list = plantPartInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<PlantPartInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<PlantPartInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<PlantPartInfo>>errorUnknown();
		}
	}

}
