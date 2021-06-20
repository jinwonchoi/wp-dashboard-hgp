package com.gencode.issuetool.unittest;

import java.io.IOException;

import org.junit.Test;

import com.logpresso.client.Cursor;
import com.logpresso.client.Logpresso;

public class LogpressoRunner {

	@Test
	public void doRunTableList() throws IOException {
		 Logpresso client = null;
		 Cursor cursor = null;
		 
		 try {
		        client = new Logpresso();
		        client.connect("file.rozetatech.com", 8888, "root", "rozeta5308!");
		        cursor = client.query("system tables | fields table");
		 
		        while (cursor.hasNext()) {
		                System.out.println(cursor.next());
		        }
		 } finally {
		        if (cursor != null)
		                cursor.close();
		 
		        if (client != null)
		                client.close();
		 }
	}
}


//java -jar C:\Users\jinno\.m2\repository\logpresso\logpresso-sdk-java\1.0.1\logpresso-sdk-java-1.0.1.jar -e "system tables | fields table" -h "file.rozetatech.com" -P "8888" -u "root" -p "rozeta5308!"

//java -jar C:\Users\jinno\Downloads\logpresso-sdk-java-1.0.0-package.jar -e "system tables | fields table" -h "file.rozetatech.com" -P "8888" -u "root" -p "rozeta5308!"