package com.gencode.issuetool.unittest;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;

import com.gencode.issuetool.client.LogWrapper;
import com.gencode.issuetool.client.RestClient;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.io.SortDirection;
import com.gencode.issuetool.obj.AuthUserInfo;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.UserInfo;
import com.gencode.issuetool.websocket.obj.StompMessage;
import com.google.gson.reflect.TypeToken;

public class UTWPDashBoardRunnerForRest {
	LogWrapper logger = new LogWrapper(UTWPDashBoardRunnerForRest.class);
	
    TestData testData; 
    String token;
    
	public UTWPDashBoardRunnerForRest() {
	}

	@Before 
	public void setUp() throws Exception {
		testData = new TestData();
	}
	
    /**
     * test title:로그인한 후 정상인증처리하고 token가져오기
     * description:
     * 
     * test id:
     * test step:
     * 1. admin계정으로 로그인
     * 2. token으로 사용자목록 가져오기
     * 
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    @Test
    public void runLoginAndGetUserList() {
    	try {
        	RestClient<AuthUserInfo> restClient = new RestClient<AuthUserInfo>();
        	Type type = new TypeToken<ResultObj<AuthUserInfo>>() {}.getType();
        	ResultObj<AuthUserInfo> result = restClient.callJsonHttp(testData.URL + "/auth/login", testData.getUserInfoAdmin(), type);
        	
        	logger.info(result.getResultCode());
        	logger.info("[["+result.getItem().toString());
        	logger.assertResult("Login", "admin계정으로 로그인", !result.getItem().getToken().isEmpty());
        	AuthUserInfo authUserInfo = (AuthUserInfo)result.getItem();
        	token =  authUserInfo.getToken();
        	
        	RestClient<List<UserInfo>> restClient2 = new RestClient<List<UserInfo>>(token);
        	ResultObj<List<UserInfo>> list = restClient2.callJsonHttp(testData.URL + "/user/list", "");
        	logger.assertResult("GetUserList", "token으로 사용자목록 가져오기", list.getItem().toArray().length>0);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * test title:Admin이 사용자 등록
     * description:
     * 
     * test id:
     * test step:
     * 1. admin계정으로 로그인
     * 2. 사번/팀/법인으로 사용자등록
     * 
     */
    public void runLoginAsAdminAndRegisterAgentUser() {
    	logger.describe("runLoginAsAdminAndRegisterAgentUser", "Admin이 사용자 등록");
    	//1. admin계정으로 로그인
    	AuthUserInfo authUserInfo = _callLogin("1. admin계정으로 로그인", testData.getUserInfoAdmin());
    	//2. 사번/팀/법인으로 사용자등록
    	//테스트용 사용자정보 클랜징 agent04
    	//테스트용 사용자정보 등록
    }


    /**
     * test title:비번초기화상태로 사용자로그인
     * description: 사용자가 비번초기화관련 인증 및 비번변경처리
     * 
     * test id:
     * test step:
     * 1. agent04로 로그인 ==> 비번변경오류코드 리턴
     * 2. 비번변경 등록 => 인증번호리턴
     * 3. 인증번호 등록 => 로그인 성공
     */

    /**
     * test title:비번변경상태로 사용자로그인
     * description: 사용자가 비번초기화관련 인증 및 비번변경처리
     * 
     * test id:
     * test step:
     * 1. agent04로 로그인 ==> 인증번호리턴
     * 3. 인증번호 등록 => 로그인 성공
     */

    /**
     * test title:loginId찾기
     * description: 이메일/폰번호로 loginId요청
     * 
     * test id:
     * test step:
     * 1. 이메일로 요청
     * 2. 문자로 요청 
     */

    /**
     * test title:사용자 프로파일 변경
     * description: 
     * 
     * test id:
     * test step:
     * 1. 이메일로 요청
     * 2. 문자로 요청 
     */

    
    /**
     * test title:로그인한 후 공지사항 등록 수정 조회 삭제
     * description:
     * 
     * test id:
     * test step:
     * 1. admin계정으로 로그인
     * 2. 등록 1
     * 3. Get
     * 4. 수정
     * 5. Get
     * 6. 등록 2
     * 7. 목록 조회
     * 8. 삭제 2
     * 9. 목록 조회
     * 
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    @Test
    public void runLoginAndDoCrudNoticeBoard() {
    	try {
        	RestClient<AuthUserInfo> restClient = new RestClient<AuthUserInfo>();
        	Type type = new TypeToken<ResultObj<AuthUserInfo>>() {}.getType();
        	ResultObj<AuthUserInfo> result = restClient.callJsonHttp(testData.URL + "/auth/login", testData.getUserInfoAdmin(), type);
        	
        	logger.info(result.getResultCode());
        	logger.info("[["+result.getItem().toString());
        	logger.assertResult("Login", "admin계정으로 로그인", !result.getItem().getToken().isEmpty());
        	AuthUserInfo authUserInfo = (AuthUserInfo)result.getItem();
        	token =  authUserInfo.getToken();
        	
			//* 2. 등록 1
    		NoticeBoard notice1 = new NoticeBoard("테스트 공지사항 1", "test notice content", 1/*admin*/,"N", 0, "P", 0);
    		NoticeBoard resultNotice1 = _callRegisterNotice("2. 등록 1", true, notice1);
        	//* 3. Get
        	NoticeBoard getNotice1 = _callGetNotice("3. Get", true,resultNotice1.getId()); 
			//* 4. 수정
        	getNotice1.setTitle("test notice title updated");
        	getNotice1.setContent("test notice content updated");
        	
        	String resultUpdate = _callUpdateNotice("4. 수정", true, getNotice1);
			//* 5. Get
        	NoticeBoard getNoticeUpdate = _callGetNotice("5. Get", true,getNotice1.getId()); 
			//* 6. 등록 2
    		NoticeBoard notice2 = new NoticeBoard("테스트 공지사항 2", "test notice content2", 1/*admin*/,"N", 0, "P", 0);
    		NoticeBoard resultNotice2 = _callRegisterNotice("6. 등록 2", true, notice2);
			//* 7. 목록 조회
    		PageResultObj<List<NoticeBoardEx>> page = _callListPageNotice("7. 목록 조회", true);
			//* 8. 삭제 2
    		//String resultDelete = _callDeleteNotice("8. 삭제 2", true,resultNotice2.getId());
			//* 9. 목록 조회
    		PageResultObj<List<NoticeBoardEx>> page2 = _callListPageNotice("9. 목록 조회", true);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

    /**
	 * 로그인처리
	 * @param loginUser
	 * @return
	 */
	AuthUserInfo _callLogin(String testTitle, UserInfo loginUser) {
		RestClient<AuthUserInfo> restClient = new RestClient<AuthUserInfo>();
		Type type = new TypeToken<ResultObj<AuthUserInfo>>() {}.getType();
		ResultObj<AuthUserInfo> result = restClient.callJsonHttp(testData.URL + "/auth/login", loginUser, type);
		
		logger.info(result.getResultCode());
		logger.info("[["+result.getItem().toString());
		AuthUserInfo authUserInfo = (AuthUserInfo)result.getItem();
		logger.assertResult("Login", testTitle, ResultObj.isSuccess(result.getResultCode()));
	
		return authUserInfo;
	}

	List<UserInfo> _callAgentList(String testTitle, boolean flag) {
		String url = testData.URL + "/user/search";
		Type type = new TypeToken<ResultObj<List<UserInfo>>>() {}.getType();
		Map<String, String> map = new HashMap<String, String>();
		map.put("role", "O");
		ResultObj<List<UserInfo>> result = (ResultObj<List<UserInfo>>) (new RestClient<List<UserInfo>>(token)).callJsonHttp(url, map, type);
		logger.debug("_callAgentList result["+result+"]");
		logger.assertResult("_callAgentList", testTitle, flag && ResultObj.isSuccess(result.getResultCode()) );
		return result.getItem();
	}
    
	NoticeBoard _callRegisterNotice(String testTitle, boolean flag, NoticeBoard notice) {
		String url = testData.URL + "/notice-board/add";
		Type type = new TypeToken<ResultObj<NoticeBoard>>() {}.getType();
		ResultObj<NoticeBoard> result = (ResultObj<NoticeBoard>) (new RestClient<NoticeBoard>(token)).callJsonHttp(url, notice, type);
		logger.debug("_callRegisterNotice result["+result+"]");
		logger.assertResult("_callRegisterNotice", testTitle, flag && ResultObj.isSuccess(result.getResultCode()) );
		return result.getItem();
	}
    
	NoticeBoard _callGetNotice(String testTitle, boolean flag, long noticeId) {
		String url = testData.URL + "/notice-board/"+Long.toString(noticeId);
		Type type = new TypeToken<ResultObj<NoticeBoard>>() {}.getType();
				
		ResultObj<NoticeBoard> result = (ResultObj<NoticeBoard>) (new RestClient<NoticeBoard>(token)).callJsonHttp(url,"", type);
		logger.debug("_callGetNotice result["+result+"]");
		logger.assertResult("_callGetNotice", testTitle, flag && ResultObj.isSuccess(result.getResultCode()) );
		return result.getItem();
	}
    
	String _callUpdateNotice(String testTitle, boolean flag, NoticeBoard notice) {
		String url = testData.URL + "/notice-board/update";
		Type type = new TypeToken<ResultObj<String>>() {}.getType();
				
		ResultObj<String> result = (ResultObj<String>) (new RestClient<String>(token)).callJsonHttp(url,notice, type);
		logger.debug("_callUpdateNotice result["+result+"]");
		logger.assertResult("_callUpdateNotice", testTitle, flag && ResultObj.isSuccess(result.getResultCode()) );
		return result.getItem();
	}
    
	String _callDeleteNotice(String testTitle, boolean flag, long noticeId) {
		String url = testData.URL + "/notice-board/delete/"+Long.toString(noticeId);
		Type type = new TypeToken<ResultObj<String>>() {}.getType();
				
		ResultObj<String> result = (ResultObj<String>) (new RestClient<String>(token)).callJsonHttp(url,"", type);
		logger.debug("_callDeleteNotice result["+result+"]");
		logger.assertResult("_callDeleteNotice", testTitle, flag && ResultObj.isSuccess(result.getResultCode()) );
		return result.getResultCode();
	}
    
	
	PageResultObj<List<NoticeBoardEx>> _callListPageNotice(String testTitle, boolean flag) {
		String url = testData.URL + "/notice-board/search";
		Type type = new TypeToken<PageResultObj<List<NoticeBoardEx>>>() {}.getType();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> paramMap = new HashMap<String, String>();
		PageRequest req = new PageRequest(1, 10, 0, SortDirection.ASC, "id", map, map,paramMap);
		PageResultObj<List<NoticeBoardEx>> result = (PageResultObj<List<NoticeBoardEx>>) (new RestClient<List<NoticeBoardEx>>(token)).callJsonHttp(url, req, type);
		logger.debug("_callListPageNotice result["+result+"]");
		logger.assertResult("_callListPageNotice", testTitle, flag && ResultObj.isSuccess(result.getResultCode()) );
		return result;
	}
    
}


