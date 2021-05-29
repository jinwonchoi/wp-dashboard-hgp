package com.gencode.issuetool.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.io.ResultObj;

/**
 * 참고
 * https://stackoverflow.com/questions/34595605/how-to-manage-exceptions-thrown-in-filters-in-spring
 * @author jinno
 *
 */
@Component
public class FilterChainExceptionHandler extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    final List<MultipartFile> files = new ArrayList<>();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
        	logger.info("filterChain.doFilter");
//        	if (request.getContentType()!=null && request.getContentType().startsWith("multipart")) {
//        		MultipartHttpServletRequest multiPartRequest = new DefaultMultipartHttpServletRequest(request);
//    			files.addAll(multiPartRequest.getFileMap().values());
//	            request.getParts().forEach(part -> {
//	            	logger.info(String.format("Part [%s, %d]", part.getName(), part.getSize()));
//	            });
//        	}
            
            filterChain.doFilter(request, response);
        } catch (NestedServletException e) {
            if(e.getRootCause() instanceof MaxUploadSizeExceededException) {
            	logger.error("Spring Security Filter Chain NestedServletException MaxUploadSizeExceededException:", e);
                response.setStatus(HttpStatus.ACCEPTED.value());
                response.setContentType("application/json");
//                ObjectMapper mapper = new ObjectMapper();
//                PrintWriter out = response.getWriter(); 
//                out.print(mapper.writeValueAsString(new ResultObj<String>(ReturnCode.FILE_MAX_SIZE_OVER)));
//                out.flush();

                response.getWriter().write("a different response... e.g in HTML");
            	//resolver.resolveException(request, response, null, (MaxUploadSizeExceededException) e.getRootCause());
            } else {
                throw e;
            }
        } catch (Exception e) {
        	logger.error("Spring Security Filter Chain Exception:", e);
            resolver.resolveException(request, response, null, e);
        }
    }
}
