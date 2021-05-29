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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.BoardComment;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.NoticeBoardWithFileList;
import com.gencode.issuetool.obj.User;
import com.gencode.issuetool.service.NoticeBoardService;

@RestController
@RequestMapping("/notice-board")
@CrossOrigin(origins = "${cors_url}")
public class NoticeBoardController {

	private final static Logger logger = LoggerFactory.getLogger(NoticeBoardController.class);
	@Autowired
	private NoticeBoardService noticeBoardService;
	

	@RequestMapping(method=RequestMethod.POST, value="/add")
	ResultObj<NoticeBoard> addPost(@RequestBody NoticeBoardWithFileList notice) {
		try {
			logger.info(notice.toString());
			NoticeBoard resultNotice = noticeBoardService.addPostEx(notice).get();
			return ResultObj.<NoticeBoard>success(resultNotice);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<NoticeBoard>errorUnknown();
		}
	}

	@RequestMapping(method=RequestMethod.POST, value="/update")
	ResultObj<String> updatePost(@RequestBody NoticeBoardWithFileList notice) {
		try {
			noticeBoardService.updatePost(notice);
			return ResultObj.<String>success();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<String>errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/delete/{id}")
	ResultObj<String> deletePost(@PathVariable(name="id") long id) {
		try {
			noticeBoardService.deletePost(id);
			return ResultObj.<String>success();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<String>errorUnknown();
		}
	}
	
	@RequestMapping("/{id}") 
	ResultObj<NoticeBoardWithFileList> getPost(@PathVariable(name="id") long id) {
		try {
			Optional<NoticeBoardWithFileList> notice = noticeBoardService.loadPostEx(id);
			if (notice.isPresent()) 
				return new ResultObj<NoticeBoardWithFileList>(ReturnCode.SUCCESS, notice.get());
			else 
				return ResultObj.<NoticeBoardWithFileList>dataNotFound();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<NoticeBoardWithFileList>errorUnknown();
		}
	}

	@RequestMapping(method=RequestMethod.POST, value="/search")
	public PageResultObj<List<NoticeBoardEx>> searchNotice(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<NoticeBoardEx>>> list = noticeBoardService.searchPostEx(req);
			if (list.isPresent()) {
				return new PageResultObj<List<NoticeBoardEx>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<NoticeBoardEx>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<NoticeBoardEx>>errorUnknown();
		}
	}
	
	@RequestMapping(value="/upload/embed/{id}", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	ResultObj<FileInfo> uploadEmbedFile(@PathVariable(name="id") long id,
			@RequestPart("json") FileInfo fileInfo, @RequestPart("upfile") MultipartFile multipartFile) {
	try {
			logger.debug(fileInfo.toString());
			logger.debug("ContentType:"+multipartFile.getContentType());
			logger.debug("Name: "+multipartFile.getName());
			logger.debug("Resource : "+multipartFile.getResource().toString());
			logger.debug("Resource Desc:"+multipartFile.getResource().getDescription());
			logger.debug("OriginalName:"+multipartFile.getOriginalFilename());
			logger.debug("Size: "+multipartFile.getSize()+"");
			Optional<FileInfo> resultFileInfo = noticeBoardService.uploadEmbedFile(multipartFile, fileInfo);
			return new ResultObj<FileInfo>(ReturnCode.SUCCESS, resultFileInfo.get());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	@RequestMapping(value="/upload/attach/{id}", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	ResultObj<FileInfo> uploadAttachFile(@PathVariable(name="id") long id, 
			@RequestPart("json") FileInfo fileInfo, 
			@RequestPart("upfile") MultipartFile multipartFile) throws MaxUploadSizeExceededException {
		try {
			logger.debug(fileInfo.toString());
			logger.debug(multipartFile.getOriginalFilename());
			logger.debug(Long.toString(multipartFile.getSize()));
			Optional<FileInfo> resultFileInfo = noticeBoardService.uploadAttachFile(multipartFile, fileInfo, id);
			return new ResultObj<FileInfo>(ReturnCode.SUCCESS, resultFileInfo.get());
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	@RequestMapping(value="/upload/cancel", method=RequestMethod.POST)
	ResultObj<String> cancelFileUploadOnContentEdit(@RequestBody List<FileInfo> fileList) {
		try {
			logger.debug(fileList.toString());
			noticeBoardService.cancelFileUploadOnContentEdit(fileList);
			return ResultObj.success();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}
	
	@RequestMapping(value="/upload/delete/{refId}/{fileId}", method=RequestMethod.POST)
	ResultObj<String> deleteFileUpload(@PathVariable(name="refId") long refId,@PathVariable(name="fileId") long fileId) {
		try {
			noticeBoardService.deleteFile(refId, fileId);
			return ResultObj.success();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.errorUnknown();
		}
	}

	//comment add
	@RequestMapping(method=RequestMethod.POST, value="/comment/add")
	ResultObj<BoardComment> addComment(@RequestBody BoardComment comment) {
		try {
			logger.info(comment.toString());
			BoardComment resultComment = noticeBoardService.addComment(comment).get();
			return ResultObj.<BoardComment>success(resultComment);
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<BoardComment>errorUnknown();
		}
	}

	@RequestMapping(method=RequestMethod.POST, value="/comment/update")
	ResultObj<String> updateComment(@RequestBody BoardComment comment) {
		try {
			noticeBoardService.updateComment(comment);
			return ResultObj.<String>success();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<String>errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/comment/delete/{id}")
	ResultObj<String> deleteComment(@PathVariable(name="id") long id) {
		try {
			noticeBoardService.deleteComment(id);
			return ResultObj.<String>success();
		} catch (Exception e) {
			logger.error("normal error", e);
			return ResultObj.<String>errorUnknown();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/comment/search")
	public PageResultObj<List<BoardComment>> searchComment(@RequestBody PageRequest req) {
		try {
			System.out.println(req.toString());
			Optional<PageResultObj<List<BoardComment>>> list = noticeBoardService.searchComment(req);
			if (list.isPresent()) {
				return new PageResultObj<List<BoardComment>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<BoardComment>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<BoardComment>>errorUnknown();
		}
	}

//	@ResponseStatus(HttpStatus.ACCEPTED)
//	@ExceptionHandler(MaxUploadSizeExceededException.class)
//	public ResultObj<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
//        logger.error("errors", e);
//        return new ResultObj<String>(ReturnCode.FILE_MAX_SIZE_OVER, "");
//	}
//    public ResultObj<String> resolveException(HttpServletRequest request,
//            HttpServletResponse response, Object handler, Exception exception)
//    {        
//        Map<String, Object> model = new HashMap<String, Object>();
//        if (exception instanceof MaxUploadSizeExceededException)
//        {
//            logger.error("errors", exception);
//            return new ResultObj<String>(ReturnCode.FILE_MAX_SIZE_OVER, "");
//        } else
//        {
//        	logger.error("errors", exception);
//        	return ResultObj.errorUnknown();
//        }
//    }	
}
