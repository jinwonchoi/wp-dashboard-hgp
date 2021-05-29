package com.gencode.issuetool.client;

import static org.junit.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWrapper {

	private static Logger logger = null;

	public LogWrapper(Class clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}
	
	public void info(String msg) {
		logger.info(msg);
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void error(String msg) {
		logger.error(msg);
	}
	
	public void error(String msg, Throwable t) {
		logger.error(msg,t);
	}
	
	public void describe(String stepId, String testDesc) {
		logger.info("--------------------------------------------------------------");
		logger.info(String.format("[%s] %s", stepId, testDesc));
		logger.info("--------------------------------------------------------------");
	}
	
	public void describe(String stepId, String testDesc, boolean result) {
		logger.info(String.format("[%s] %s ==> %b", stepId, testDesc, result));
	}
	
	public void assertResult(String stepId, String testDesc, boolean result) {
		describe(stepId, testDesc, result);
		assertTrue(String.format("[%s] %s", stepId, testDesc), result);
	}
}
