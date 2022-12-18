/**=========================================================================================
<overview>Springboot entry 클래스
  </overview>
==========================================================================================*/
package com.gencode.issuetool;

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
		SpringApplication.run(IssuetoolChatApp.class, args);
	}
}