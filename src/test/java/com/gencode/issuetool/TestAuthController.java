package com.gencode.issuetool;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gencode.issuetool.obj.UserInfo;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class TestAuthController {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
	String token = null;

	public TestAuthController() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * 로그인후 정상권한 조회
	 */
    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
    	mockMvc.perform( post("/auth/login")
    				.content(om.writeValueAsString(new UserInfo("admin","passwd")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
    				.andDo(mvcResult -> { System.out.println("RETURN BY LOGIN: "+mvcResult.toString()); })
    				.andDo(mvcResult -> { token = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.item.token"); })
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode", is("0001")));
    	//로그인후 admin권한으로 user패스 조회
    	mockMvc.perform( get("/user/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(mvcResult -> { System.out.println("RETURN BY USER ID: "+mvcResult.getResponse().getContentAsString()); })
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode", is("0001")));
	}
    
    /*
     * 로그인후 부적합한 권한으로 조회=> 실패
     */
    @Test 
    public void loginValidAndAuthorizationFailed() throws Exception {
    	mockMvc.perform( post("/auth/login")
				.content(om.writeValueAsString(new UserInfo("manager","passwd")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(mvcResult -> { System.out.println("RETURN BY LOGIN: "+mvcResult.toString()); })
				.andDo(mvcResult -> { token = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.item.token"); })
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode", is("0001")));
		//로그인후 admin권한으로 user패스 조회
		mockMvc.perform( get("/user/1")
	            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(mvcResult -> { System.out.println("RETURN BY USER ID: "+mvcResult.getResponse().getContentAsString()); })
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.resultCode", is("302")));
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
