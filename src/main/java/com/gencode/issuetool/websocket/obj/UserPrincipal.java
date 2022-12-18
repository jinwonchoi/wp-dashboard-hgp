/**=========================================================================================
<overview>웹소켓관련 사용자인증정보 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.websocket.obj;

import java.security.Principal;

import com.gencode.issuetool.obj.UserInfo;

public class UserPrincipal implements Principal{
    private UserInfo user;
    public UserPrincipal(UserInfo user){
        this.user = user;
    }
    public UserInfo getUserInfo(){
        return user;
    }
    @Override
    public String getName() {
        return user.getLoginId();
    }
    
}
