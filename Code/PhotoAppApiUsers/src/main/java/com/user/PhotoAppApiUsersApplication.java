package com.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.user.dto.FeignErrorDecoder;

import feign.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PhotoAppApiUsersApplication {
	
	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bcryptpassencoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	
	@Bean
	
	Logger.Level feignLoggerLevel()
	{
		return Logger.Level.NONE;
	}
	
	//productionbean
	@Bean
	@Profile("prod")
	public String createProductionBean() {
		System.out.println("productionbean created myapplication.environment =" + env.getProperty("myapplication.environment"));
		return  "Production bean";
	
	}
	
	//default env
	@Bean
	@Profile("default")
	public String createDevelopmentBean() {
		System.out.println("default bean created myapplication.environment =" + env.getProperty("myapplication.environment"));
		return  "Development bean";
	
	}
	
	//not for production
	@Profile("!prod")
	public String createNotForProductionBean() {
		System.out.println("not productionbean created myapplication.environment =" + env.getProperty("myapplication.environment"));
		return  "Production bean";
	
	}
	
	
	
	
	/*
	 * @Bean public FeignErrorDecoder getFeignErrorDecoder() { return new
	 * FeignErrorDecoder(); }
	 */

}
