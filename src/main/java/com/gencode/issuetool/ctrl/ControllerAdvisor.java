package com.gencode.issuetool.ctrl;


import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.io.ResultObj;

//@RestControllerAdvice(annotations = RestController.class)
@ControllerAdvice
public class ControllerAdvisor {
	private final static Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);
	
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ModelAndView handle(Exception ex) {
//
//    	ModelAndView mv = new ModelAndView();
//        mv.addObject("message", ex.getMessage());
//        mv.setViewName("error/404");
//
//        return mv;
//    }
    
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ModelAndView handleMaxSizeException(
//      MaxUploadSizeExceededException exc, 
//      HttpServletRequest request,
//      HttpServletResponse response) {
// 
//		try {
//			logger.error("Max file size error", exc);
//	    	ModelAndView mv = new ModelAndView();
//	        mv.addObject("message", exc.getMessage());
//	        mv.setViewName("error/maxfilesize");
//	        
//	        return mv;
//		} catch (Exception e) {
//			logger.error("normal error", e);
//			return null;
//		}
//    }
    
//    @ExceptionHandler(MultipartException.class)
//    public String handleMaxUploadSizeExceededException(MultipartException ex, RedirectAttributes ra) {
//      String maxFileSize = getMaxUploadFileSize(ex);
//      if (maxFileSize != null) {
//        ra.addFlashAttribute("errors", "Uploaded file is too large.  File size cannot exceed " + maxFileSize + ".");
//      }
//      else {
//        ra.addFlashAttribute("errors", ex.getMessage());
//      }
//      //return "redirect:/";
//      return "error/maxfilesize";
//    }
//
//    private String getMaxUploadFileSize(MultipartException ex) {
//      if (ex instanceof MaxUploadSizeExceededException) {
//        return asReadableFileSize(((MaxUploadSizeExceededException)ex).getMaxUploadSize());
//      }
//      String msg = ex.getMessage();
//      if (msg.contains("SizeLimitExceededException")) {
//        String maxFileSize = msg.substring(msg.indexOf("maximum")).replaceAll("\\D+", "");
//        if (StringUtils.isNumeric(maxFileSize)) {
//          return asReadableFileSize(Long.valueOf(maxFileSize));
//        }
//      }
//
//      return null;
//    }
//
//    // http://stackoverflow.com/a/5599842/225217
//    private static String asReadableFileSize(long size) {
//      if(size <= 0) return "0";
//      final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
//      int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
//      return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
//    }
	@ExceptionHandler(Exception.class)
	public @ResponseBody ResultObj<String> onException(Exception e, HttpServletResponse response) {
	    response.setStatus(HttpServletResponse.SC_OK);

	    //BaseResponse resp = new BaseResponse();
	    if(e instanceof MaxUploadSizeExceededException){
			ResultObj<String> result = new ResultObj<String>();
			result.setResultCode("1111");
			result.setResultMsg("1111Message");
			return result;
	    }
	    if(e instanceof Exception){
			return ResultObj.errorUnknown();
	    }
	    return ResultObj.success();
	}
}