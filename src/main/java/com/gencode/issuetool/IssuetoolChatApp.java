/**=========================================================================================
<overview>Springboot entry 클래스
  </overview>
==========================================================================================*/
package com.gencode.issuetool;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class IssuetoolChatApp {
	@Value( "${cors_url}" )
    private String corsUrl;

	public static void main(String[] args) {
		 System.out.println(fullVMArguments());
		SpringApplication.run(IssuetoolChatApp.class, args);
	}
	
	  static String fullVMArguments() {
	    String name = javaVmName();
	    return (contains(name, "Server") ? "-server "
	      : contains(name, "Client") ? "-client " : "")
	      + joinWithSpace(vmArguments());
	  }

	  static List<String> vmArguments() {
	    return ManagementFactory.getRuntimeMXBean().getInputArguments();
	  }

	  static boolean contains(String s, String b) {
	    return s != null && s.indexOf(b) >= 0;
	  }

	  static String javaVmName() {
	    return System.getProperty("java.vm.name");
	  }

	  static String joinWithSpace(Collection<String> c) {
	    return join(" ", c);
	  }

	  public static String join(String glue, Iterable<String> strings) {
	    if (strings == null) return "";
	    StringBuilder buf = new StringBuilder();
	    Iterator<String> i = strings.iterator();
	    if (i.hasNext()) {
	      buf.append(i.next());
	      while (i.hasNext())
	        buf.append(glue).append(i.next());
	    }
	    return buf.toString();
	  }
}
