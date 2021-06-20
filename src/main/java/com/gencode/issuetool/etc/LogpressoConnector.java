package com.gencode.issuetool.etc;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.logpresso.client.Cursor;
import com.logpresso.client.Logpresso;
import com.logpresso.client.Procedure;

public class LogpressoConnector {

	
	@Value("${logpresso.host}") String host;
	@Value("${logpresso.port}") String port;
	@Value("${logpresso.userid}") String userid;
	@Value("${logpresso.password}") String password;
	
	 Logpresso client = null;
//	 Cursor cursor = null;
	public Cursor executeQuery(String strQuery) throws IOException {
        client = new Logpresso();
        client.connect("file.rozetatech.com", 8888, "root", "rozeta5308!");
        return client.query(strQuery);
	}

	public Cursor executeProc(String strQuery) throws IOException {
        client = new Logpresso();
        client.connect("file.rozetatech.com", 8888, "root", "rozeta5308!");
        List<Procedure> list  = client.listProcedures();
        Iterator<Procedure> it = list.iterator();
        while (it.hasNext()) {
        	System.out.println(it.next().toString());
        }
        return null;
	}

	public void close() throws IOException {
		client.close();
	}
	
//	 Logpresso client = null;
//	 Cursor cursor = null;
//	 
//	 try {
//	        client = new Logpresso();
//	        client.connect("file.rozetatech.com", 8888, "root", "rozeta5308!");
//	        cursor = client.query("table weather_if");
//	 
//	        while (cursor.hasNext()) {
//	                System.out.println(cursor.next());
//	        }
//	 } finally {
//	        if (cursor != null)
//	                cursor.close();
//	 
//	        if (client != null)
//	                client.close();
//	 }
}
