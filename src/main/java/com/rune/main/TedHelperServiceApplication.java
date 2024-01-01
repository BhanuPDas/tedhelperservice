package com.rune.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
@ComponentScan("com.rune")
public class TedHelperServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TedHelperServiceApplication.class, args);
	}

	@Bean(autowireCandidate = true)
	@Scope("singleton")
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

	@Bean(autowireCandidate = true)
	@Scope("singleton")
	public ObjectMapper createObjectMapper() {
		ObjectMapper obj = new ObjectMapper();
		obj.registerModule(new JavaTimeModule());
		return obj;
	}

}
