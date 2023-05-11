package com.gencode.issuetool.unittest;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gencode.issuetool.client.LogWrapper;
import com.gencode.issuetool.client.RestClient;
import com.gencode.issuetool.util.JsonUtils;

public class ScenarioTestRunner {
	LogWrapper logger = new LogWrapper(ScenarioTestRunner.class);
	//@DisplayName("TEST event push generation")
	@Test
	void testEventPushGeneration() throws Exception {
		RestClient<String> restClient = new RestClient<String>();
		String result = restClient.post("http://dt.rozetatech.com:3000/hg/api/event/ws/test",
				JsonUtils.toJson(
				new HashMap<String, String>(){{
					put("type","I");
					put("level","A");
					put("id","ST30101N02");
					put("kind","TMP");
					put("value","70");
					put("apikey","K8XWymm1dfTP3mh5KKheQ5acptMnOHt8LwYqRgTPMg66/MJTevbYlSwSnC/mOfa6");
				}}));
		logger.info("result:"+result);
	}
}
