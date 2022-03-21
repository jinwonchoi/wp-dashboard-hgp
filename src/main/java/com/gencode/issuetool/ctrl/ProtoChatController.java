package com.gencode.issuetool.ctrl;

import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.prototype.obj.ChatInfo;
import com.gencode.issuetool.prototype.obj.MessageLog;
import com.gencode.issuetool.prototype.obj.NoticeBoardEx;
import com.gencode.issuetool.prototype.obj.UserInfo;
import com.gencode.issuetool.prototype.service.PrtChatService;
import com.gencode.issuetool.util.JsonUtils;
import com.gencode.issuetool.websocket.obj.StompMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/prototype/chat")
@RestController
@CrossOrigin(origins = "${cors_url}")
public class ProtoChatController {
	private static final Logger logger = LoggerFactory.getLogger(ProtoChatController.class);

    @Autowired
    private PrtChatService chatService;

    /**
     * 채팅메시지 전송
     * @param 
     * @return
     */
	@RequestMapping(method=RequestMethod.POST, value="/message/send")
    public ResultObj<String> sendMessage(@RequestBody MessageLog t){
        try {
        	chatService.sendMsgToChat(t);
			return ResultObj.success();
        } catch (Exception e) {
        	logger.error("normal error", e);
			return ResultObj.errorUnknown();
        }
	}
    /** 
     * 채팅 목록
	 * public Optional<List<ChatInfo>> searchChatList(Map<String, String> map) {
     */
	
	@RequestMapping(method=RequestMethod.POST, value="/list")
	public PageResultObj<List<ChatInfo>> getChatList(@RequestBody PageRequest req) {
        try {
        	System.out.println(req.toString());
			Optional<PageResultObj<List<ChatInfo>>> list = chatService.searchChatList(req);
			if (list.isPresent()) {
				return new PageResultObj<List<ChatInfo>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<ChatInfo>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<ChatInfo>>errorUnknown();
		}
	}
    
    /**
     * 메시지목록
	 * public Optional<List<MessageLog>> searchMessageList(Map<String, String> map) {
     */
	@RequestMapping(method=RequestMethod.POST, value="/message/list")
	public PageResultObj<List<MessageLog>> getChatMsgList(@RequestBody PageRequest req) {
        try {
        	System.out.println(req.toString());
			Optional<PageResultObj<List<MessageLog>>> list = chatService.searchMessageList(req);
			if (list.isPresent()) {
				return new PageResultObj<List<MessageLog>>(ReturnCode.SUCCESS, list.get());
			} else {
				return PageResultObj.<List<MessageLog>>dataNotFound();
			}
		} catch (Exception e) {
			logger.error("normal error", e);
			return PageResultObj.<List<MessageLog>>errorUnknown();
		}
	}
    
    /**
     * 메시지목록/페이지
     */
//    
//	@RequestMapping(method=RequestMethod.POST, value="/message/send")
//    public ResultObj<String> sendMessage(@RequestBody MessageLog t){
//        try {
//        	chatService.sendMsgToChat(t);
//			return ResultObj.success();
//        } catch (Exception e) {
//        	logger.error("normal error", e);
//			return ResultObj.errorUnknown();
//        }
//	}
    /**
     * 일기완료표시
	 * public void resetUnReadCnt(long chatId) {
	 * 
	@RequestMapping(value="/upload/delete/{refId}/{fileId}", method=RequestMethod.POST)
	ResultObj<String> deleteFileUpload(@PathVariable(name="refId") long refId,@PathVariable(name="fileId") long fileId) {
     */
    
	@RequestMapping(method=RequestMethod.POST, value="/message/done/{id}")
    public ResultObj<String> setMessageRead(@PathVariable(name="id") long id){
        try {
        	chatService.resetUnReadCnt(id);
			return ResultObj.success();
        } catch (Exception e) {
        	logger.error("normal error", e);
			return ResultObj.errorUnknown();
        }
	}
    
    /**
     * 채팅종료
	 * public void closeChat(long chatId) {
     */    
	@RequestMapping(method=RequestMethod.POST, value="/chat/done/{id}")
    public ResultObj<String> closeChat(@PathVariable(name="id") long id){
        try {
        	chatService.closeChat(id);
			return ResultObj.success();
        } catch (Exception e) {
        	logger.error("normal error", e);
			return ResultObj.errorUnknown();
        }
	}

}
