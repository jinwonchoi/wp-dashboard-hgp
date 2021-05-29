package com.gencode.issuetool.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories
public class AppConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/denied").setViewName("denied");
      registry.addViewController("/powerplant").setViewName("forward:/powerplant/index.html");
    }

//    @Bean
//    public UrlBasedViewResolver viewResolver() {
//        final UrlBasedViewResolver bean = new UrlBasedViewResolver();
//        bean.setPrefix("/WEB-INF/jsp/");
//        bean.setSuffix(".jsp");
//        bean.setViewClass(JstlView.class);
//        return bean;
//    }

    /**
     * 외부파일경로 설정
     * vue js 경로 설정
     * https://www.baeldung.com/spring-mvc-static-resources
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/", "/resources/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
        registry.addResourceHandler("/files/profile/**").addResourceLocations("file:files/profile/");
        registry.addResourceHandler("/files/upload/**").addResourceLocations("file:files/upload/");
        registry.addResourceHandler("/powerplant/**").addResourceLocations("file:files/powerplant/");
    }
    

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedOrigins("http://localhost:8082")
                .allowCredentials(true).allowedHeaders("*");
    }
}