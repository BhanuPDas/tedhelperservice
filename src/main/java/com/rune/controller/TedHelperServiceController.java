package com.rune.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ths")
public class TedHelperServiceController {

	@GetMapping("/workTime")
	public String getWorkTime() {
		return "its time to work";
	}

}
