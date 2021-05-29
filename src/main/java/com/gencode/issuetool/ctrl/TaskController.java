package com.gencode.issuetool.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.User;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "${cors_url}")
public class TaskController {

//	private final static Logger logger = LoggerFactory.getLogger(TaskController.class);
//	@Autowired
//	private TaskService taskService;
//		
//	@RequestMapping("/add/test")
//	String addTask() {
//		Task task = new Task("task name", "R",-1, "jinwony","members","watchers", "N",null,null,"comments");
//		
//		//	public Task(String taskName, String taskType, long parentTask, String createId, String taskMembers,
//		//String taskWatchers, String status, String startDate, String endDate, String detail) {
//
//		try {
//			taskService.add(task);
//			return "success";
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return "error";
//		}
//	}
//	
//	
//	@RequestMapping(method=RequestMethod.POST, value="/add")//,consumes = MediaType.APPLICATION_JSON_VALUE)
//	ResultObj<String> addTask(@RequestBody Task task) {
//		try {
//			logger.info(task.toString());
//			taskService.add(task);
//			return ResultObj.<String>success();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.<String>errorUnknown();
//		}
//	}
//
//	@RequestMapping(method=RequestMethod.POST, value="/update")//,consumes = MediaType.APPLICATION_JSON_VALUE)
//	ResultObj<String> updateTask(@RequestBody Task task) {
//		try {
//			taskService.update(task);
//			return ResultObj.<String>success();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.<String>errorUnknown();
//		}
//	}
//	
//	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")//,consumes = MediaType.APPLICATION_JSON_VALUE)
//	ResultObj<String> deleteTask(@PathVariable(name="id") long id) {
//		try {
//			taskService.delete(id);
//			return ResultObj.<String>success();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.<String>errorUnknown();
//		}
//	}
//	
//	@RequestMapping("/{id}") 
//	ResultObj<Task> getTask(@PathVariable(name="id") long id) {
//		try {
//			Optional<Task> task = taskService.load(id);
//			if (task.isPresent()) 
//				return new ResultObj<Task>(ReturnCode.SUCCESS, task.get());
//			else 
//				return ResultObj.<Task>dataNotFound();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.<Task>errorUnknown();
//		}
//	}
//
//	@RequestMapping("/list") 
//	ResultObj<List<Task>> loadAll() {
//		try {
//			return taskService.loadAll().map(t->t.size()>0?ResultObj.<List<Task>>success(t):ResultObj.<List<Task>>dataNotFound()).get();
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return ResultObj.<List<Task>>errorUnknown();
//		}
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="/search")
//	public ResultObj<List<Task>> searchTask(@RequestBody HashMap<String, String> map) {
//		try {
//			System.out.println(map.toString());
//			Optional<List<Task>> list = taskService.search(map);
//			if (list.isPresent()) {
//				return new ResultObj<List<Task>>(ReturnCode.SUCCESS, list.get());
//			} else {
//				return ResultObj.<List<Task>>dataNotFound();
//			}
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return PageResultObj.<List<Task>>errorUnknown();
//		}
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="/page")//,consumes = MediaType.APPLICATION_JSON_VALUE)
//	public PageResultObj<List<Task>> searchTask(@RequestBody PageRequest pageRequest) {
//		try {
//			logger.info(pageRequest.toString());
//			return taskService.search(pageRequest);
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return PageResultObj.<List<Task>>errorUnknown();
//		}
//	}
//	
}
