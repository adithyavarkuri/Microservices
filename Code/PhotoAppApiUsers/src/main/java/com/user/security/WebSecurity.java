package com.user.security;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import com.user.service.UsersService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class WebSecurity {
	
	public Environment env;
	private UsersService usersService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public WebSecurity(Environment env,  UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.env = env;
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		
	}
	
	@Bean
	 protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		// Configure AuthenticationManagerBuilder
    	AuthenticationManagerBuilder authenticationManagerBuilder = 
    			http.getSharedObject(AuthenticationManagerBuilder.class);
    	authenticationManagerBuilder.userDetailsService(usersService)
    	.passwordEncoder(bCryptPasswordEncoder);
		
    	AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
    	
    	// Create AuthenticationFilter
    	AuthenticationFilter authenticationFilter = 
    			new AuthenticationFilter(usersService, env, authenticationManager);
    	authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
    	
    	
		http.csrf().disable();
		/*
		 * http.authorizeHttpRequests().requestMatchers(HttpMethod.POST,
		 * "/users").permitAll(). requestMatchers(new
		 * AntPathRequestMatcher("/h2-console/**")).permitAll()
		 * .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
		 * STATELESS);
		 */
		
		IpAddressMatcher hasIpAddress = new IpAddressMatcher(Objects.requireNonNull(env.getProperty("gateway.ip")));
		IpAddressMatcher hasIpv4Address = new IpAddressMatcher(Objects.requireNonNull(env.getProperty("gateway.ipv4")));
		
		
		 http.authorizeHttpRequests((authz) -> authz
			        .requestMatchers(new AntPathRequestMatcher("/users/**")).
			        access((authentication, context) -> {
	                    if(hasIpAddress.matches(context.getRequest()))
	                        return new AuthorizationDecision(
	                                hasIpAddress.matches(context.getRequest()));
	                    return new AuthorizationDecision(
	                            hasIpv4Address.matches(context.getRequest()));
	                }).requestMatchers(new AntPathRequestMatcher( "/actuator/**", HttpMethod.GET.toString())).
			        access((authentication, context) -> {
	                    if(hasIpAddress.matches(context.getRequest()))
	                        return new AuthorizationDecision(
	                                hasIpAddress.matches(context.getRequest()));
	                    return new AuthorizationDecision(
	                            hasIpv4Address.matches(context.getRequest()));
	                })
			        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll())
		 .addFilter(new AuthorizationFilter(authenticationManager, env )).
		 addFilter(authenticationFilter)
		 .authenticationManager(authenticationManager).
		 sessionManagement((session) -> session
					        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));;
		
	
		http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()));
		
		return http.build();
		 
	 }

}
