package com.gencode.issuetool.etc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gencode.issuetool.io.ResultObj;
import com.gencode.issuetool.obj.UserInfo;

public class UtilsTest extends Utils {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		long a = 3;
		long b = 7;
		double c= BigDecimal.valueOf((float)a/b).round(new MathContext(2)).doubleValue();
		System.out.println("a="+a);
		System.out.println("b="+b);
		System.out.println("c="+c);
		fail("Not yet implemented");
	}

	@Test
	public void testStrToObj() {
		ResultObj<UserInfo> resultObj = new ResultObj<UserInfo>();
		UserInfo userInfo  = new UserInfo("loginid", "passwd1");
		resultObj.setResultCode(ReturnCode.ALREADY_EXISTS_SCHEDULE.get());
		resultObj.setItem(userInfo);
		try {
			System.out.println(Utils.objToStr(resultObj));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testWeekList() {
		Map<String, Object> workDays = new HashMap<String, Object>();
		Date date = new Date();
		for (int i=0;i<7;i++) {
			System.out.println(Utils.YYYYMMDD(date)+":"+Utils.YYYYMMDD(DateUtils.addDays(date, -7+i)));
			workDays.put(Utils.YYYYMMDD(DateUtils.addDays(date, -7+i)), new Integer(0));
		}
		workDays.forEach((k,v) -> System.out.println(k+":"+workDays.get(k)));
	}
	
}
