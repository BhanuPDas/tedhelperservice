package com.rune.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan("com.rune")
public class TedHelperServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TedHelperServiceApplication.class, args);
	}

	@Bean
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

}
