package com.rune.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.rune")
public class TedHelperServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TedHelperServiceApplication.class, args);
	}

}
