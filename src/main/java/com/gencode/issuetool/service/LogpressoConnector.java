/**=========================================================================================
<overview>로그프레소연동관리자
  </overview>
==========================================================================================*/
package com.gencode.issuetool.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gencode.issuetool.exception.StorageException;

@Deprecated
@Component
public class LogpressoConnector {
	private final static Logger logger = LoggerFactory.getLogger(LogpressoConnector.class);
	@Value("${logpresso.host}") String host;
	@Value("${logpresso.port}") String port;
	@Value("${logpresso.userid}") String userid;
	@Value("${logpresso.password}") String password;
	//@Autowired
	//Logpresso client;
//	 Cursor cursor = null;
//	public Logpresso getConnection() throws IOException {
//		Logpresso client = new Logpresso();
//        client.connect(host, Integer.parseInt(port), userid, password);
//        return client;
//	}
//	public Cursor executeQuery(Logpresso client, String strQuery) throws IOException {
//        logger.info(String.format("Logpresso connection created: %s:%s ", host, port));
//        return client.query(strQuery);
//	}
//
//	public Cursor executeProc(Logpresso client, String strQuery) throws IOException {
//        logger.info(String.format("Logpresso connection created: %s:%s ", host, port));
//        List<Procedure> list  = client.listProcedures();
//        Iterator<Procedure> it = list.iterator();
//        while (it.hasNext()) {
//        	System.out.println(it.next().toString());
//        }
//        return null;
//	}
//
//	public void close(Logpresso client) throws IOException {
//		client.close();
//		client = null;
//	}
}
