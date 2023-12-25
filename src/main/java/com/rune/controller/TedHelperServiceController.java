package com.rune.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rune.request.ApiTimeDataRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/tedHelper")
public class TedHelperServiceController {

	Logger logger = LogManager.getLogger(TedHelperServiceController.class);

	@PostMapping("/create-work-Time")
	public ResponseEntity<String> addWorkTime(@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody ApiTimeDataRequest timeDataRequest) {

		logger.info("Request received to create work time on TED");
		return ResponseEntity.ok("Work Data has been successfully updated on TED");
	}

	@PostMapping("/create-travel-Time")
	public ResponseEntity<String> addTravelTime(@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody ApiTimeDataRequest timeDataRequest) {

		logger.info("Request received to create travel time on TED");
		return ResponseEntity.ok("Travel Data has been successfully updated on TED");
	}

	@PostMapping("/create-break-Time")
	public ResponseEntity<String> addBreakTime(@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody ApiTimeDataRequest timeDataRequest) {

		logger.info("Request received to create break time on TED");
		return ResponseEntity.ok("Break Data has been successfully updated on TED");
	}

}
