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
import com.gencode.issuetool.obj.PlantInfo;
import com.gencode.issuetool.service.PlantInfoService;

@RestController
@RequestMapping("/plantinfo/plant")
@CrossOrigin(origins = "${cors_url}")
public class PlantInfoController {

	private final static Logger logger = LoggerFactory.getLogger(PlantInfoController.class);
	@Autowired
	private PlantInfoService plantInfoService;
	

	@RequestMapping(method=RequestMethod.POST, value="/add")
	ResultObj<PlantInfo> add(@RequestBody PlantInfo plantInfo) {
		try {
			logger.info(plantInfo.toString());
			PlantInfo resultPlantInfo = plantInfoService.register(plantInfo).get();
			return ResultObj.<PlantInfo>success(resultPlantInfo);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<PlantInfo>errorUnknown();
		}
	}

	@RequestMapping(method=RequestMethod.POST, value="/update")
	ResultObj<PlantInfo> update(@RequestBody PlantInfo plantInfo) {
		try {
			logger.info(plantInfo.toString());
			PlantInfo resultPlantInfo = plantInfoService.update(plantInfo).get();
			return ResultObj.<PlantInfo>success(resultPlantInfo);
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<PlantInfo>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<PlantInfo>errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	ResultObj<String> delete(@PathVariable(name="id") long id) {
		try {
			plantInfoService.delete(id);
			return ResultObj.<String>success();
		} catch (NotFoundException nfe) {
			logger.error("not found error", nfe);
			return ResultObj.<String>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<String>errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/search")
	public PageResultObj<List<PlantInfo>> search(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<PlantInfo>>> list = plantInfoService.search(req);
			if (list.isPresent()) {
				return new PageResultObj<List<PlantInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<PlantInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<PlantInfo>>errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/list")
	public ResultObj<List<PlantInfo>> loadAll() {
		try {
			Optional<List<PlantInfo>> list = plantInfoService.loadAll();
			if (list.isPresent()) {
				return new ResultObj<List<PlantInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return ResultObj.<List<PlantInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<List<PlantInfo>>errorUnknown();
		}
	}
		
}
