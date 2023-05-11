/**=========================================================================================
<overview>요청 경로 유효성체크 및 경로별 사용자접근권한 처리 
  </overview>
==========================================================================================*/
package com.gencode.issuetool.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.gencode.issuetool.etc.UserRole;
import com.gencode.issuetool.service.MyUserDetailsService;

/**
 * @EnableGlobalAuthentication annotates:
 * @EnableWebSecurity
 * @EnableWebMvcSecurity
 * @EnableGlobalMethodSecurity Passing in 'prePostEnabled = true' allows:
 * <p>
 * Pre/Post annotations such as:
 * @PreAuthorize("hasRole('ROLE_USER')")
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired(required=true)
    private MyUserDetailsService myUserDetailsService;
    
    @Autowired
    FilterChainExceptionHandler filterChainExceptionHandler;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

    /**
     * Authentication beans
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider bean = new CustomDaoAuthenticationProvider();
		bean.setUserDetailsService(myUserDetailsService);
        bean.setPasswordEncoder(passwordEncoder());
        return bean;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Order of precedence is very important.
     * <p>
     * Matching occurs from top to bottom - so, the topmost match succeeds first.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	log.info("configure(HttpSecurity http)");
        http.cors().and()
        .httpBasic().disable().csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .antMatchers("/", "/index").permitAll()
        .antMatchers("/auth/login").permitAll()
        .antMatchers("/auth/register").permitAll()
        .antMatchers("/auth/user/**").permitAll()
        .antMatchers("/auth/refreshToken").permitAll()
        .antMatchers("/user/profile/register").permitAll()
        .antMatchers("/error/**").permitAll()
        .antMatchers("/fmon/**").permitAll() 
        .antMatchers("/powerplant/**").permitAll() 
        .antMatchers("/prototype/**").permitAll()
        
        .antMatchers("/user/**").hasAnyAuthority(UserRole.USER.get(), 
        		UserRole.MANAGER.get(),
        		UserRole.ADMIN.get()).anyRequest().authenticated()
        .antMatchers("/common/**").hasAnyAuthority(UserRole.USER.get(),UserRole.MANAGER.get(),UserRole.ADMIN.get()).anyRequest().authenticated()
        .antMatchers("/dashboard/**").hasAnyAuthority(UserRole.USER.get(),UserRole.MANAGER.get(),UserRole.ADMIN.get()).anyRequest().authenticated()
        .antMatchers("/kfsl/**").hasAnyAuthority(UserRole.USER.get(),UserRole.MANAGER.get(),UserRole.ADMIN.get()).anyRequest().authenticated()
        .antMatchers("/plantinfo/**").hasAnyAuthority(UserRole.USER.get(),UserRole.MANAGER.get(),UserRole.ADMIN.get()).anyRequest().authenticated()
        .antMatchers("/chat/**").hasAnyAuthority(UserRole.USER.get(), 
        		UserRole.MANAGER.get(),
        		UserRole.ADMIN.get()).anyRequest().authenticated()
        .antMatchers("/admin/**").hasAuthority(UserRole.ADMIN.get()).anyRequest().authenticated()
        .antMatchers("/task/**").hasAuthority(UserRole.ADMIN.get()).anyRequest().authenticated()

        /** Disabled for local testing */
        .and().csrf().disable()
        //.addFilterBefore(filterChainExceptionHandler, JwtTokenFilter.class)
        /** jwt 적용시 */
        .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint()).and()
        .apply(new JwtConfigurer(jwtTokenProvider))
        ;         
    }
    
    @Override
    protected void configure(final AuthenticationManagerBuilder builder) throws Exception {
    	builder.authenticationProvider(authenticationProvider());
	}
    

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()//.antMatchers("/resources/**");
    	//webSecurity.ignoring()
        .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**"
		,"/files/**"
		//,"/files/chatsimul/**"
		//,"/files/chatapp/**"
		,"/files/upload/**"
		,"/static/profile/**"
		);        
        
    }
    //https://stackoverflow.com/questions/36968963/how-to-configure-cors-in-a-spring-boot-spring-security-application
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers"
//        		,"Access-Control-Allow-Origin"
//        		,"Access-Control-Request-Method"
//        		,"Access-Control-Request-Headers"
//        		,"Origin"
//        		,"Cache-Control"
//        		,"Content-Type"
//        		,"Authorization"
//        		,"Accept"
//        		,"X-Auth-Token"// websocket 
//        		));
        //configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT","OPTIONS"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("X-Auth-Token", "Authorization"));//websocket
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    //https://stackoverflow.com/questions/40286549/spring-boot-security-cors
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(corsUrl.split(",")));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","OPTIONS"));
//        configuration.addAllowedHeader("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized");
    }

}
