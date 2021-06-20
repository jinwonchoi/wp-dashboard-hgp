package com.gencode.issuetool.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * vue.js통해서 다른 경로로 url이 들어왔을때 404 아닌 index.html로 포워딩
 * https://stackoverflow.com/questions/44692781/configure-spring-boot-to-redirect-404-to-a-single-page-app
 * @author jinno
 *
 */
@Controller 
public class Html5PathsController { 
//
//    @RequestMapping( method = {RequestMethod.OPTIONS, RequestMethod.GET}, 
//    		path = {"/chatsimul/pages/**",
//    				"/chatsimul/apps/**",
//    		} )
//    public String forwardChatSimulPaths() { 
//        return "forward:/chatsimul/index.html"; 
//    } 
//
    @RequestMapping( method = {RequestMethod.OPTIONS, RequestMethod.GET}, 
    		path = {
    				"/fmon/login"
    				,"/fmon/apps"
    				,"/fmon/apps/**"
    				,"/fmon/default"
    				,"/fmon/dashboard"
    				,"/fmon/dashboard/**"
    				,"/fmon/notice-board"
    				,"/fmon/notice-board/**"
    				,"/fmon/account"
    				,"/fmon/account/**"
    				,"/fmon/admin"
    				,"/fmon/admin/**"
    				,"/fmon/main"
    				,"/fmon/main/**"
    				,"/fmon/stats"
    				,"/fmon/stats/**"
    				,"/fmon/etc"
    				,"/fmon/etc/**"
    		})
    public String forwardChatAppPaths() { 
        return "forward:/fmon"; 
    }

}