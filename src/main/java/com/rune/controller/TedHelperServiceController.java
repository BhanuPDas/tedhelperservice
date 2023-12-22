package com.rune.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TedHelperServiceController {

	@GetMapping("/workTime")
	public String getWorkTime() {
		return "its time to work";
	}

}
