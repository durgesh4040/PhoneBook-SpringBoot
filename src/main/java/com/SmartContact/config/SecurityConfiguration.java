package com.SmartContact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//import com.example.demo.config.UserDetailServiceImp;


import com.SmartContact.config.UserDetailServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

 @Bean 
 public UserDetailsService getUserDetailService() {
	 return new UserDetailServiceImp();
 }
 @Bean 
 public BCryptPasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder();
 }
 @Bean 
 public DaoAuthenticationProvider authenticationProvider() {
	 DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
	
	 daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
	 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	 return  daoAuthenticationProvider;
 }
	
	
	
	@Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeRequests()
            .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/**").permitAll()
                
            .and().formLogin().loginPage("/signin")
            .loginProcessingUrl("/dologin")
            .defaultSuccessUrl("/user/index")
            .failureUrl("/login-fail")
            .and()
            .csrf().disable() // Disable CSRF for simplicity in this example
            .build();
    }
}
