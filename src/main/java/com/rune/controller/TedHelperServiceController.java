package com.rune.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rune.request.ApiTimeDataRequest;
import com.rune.response.ApiTimeDataResponse;
import com.rune.service.TEDHelperService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/tedHelper")
public class TedHelperServiceController {

	Logger logger = LogManager.getLogger(TedHelperServiceController.class);

	@Autowired
	private ObjectMapper obj;
	@Autowired
	private TEDHelperService service;
	private ApiTimeDataResponse response;

	@PostMapping("/create-work-Time")
	public ResponseEntity<ApiTimeDataResponse> addWorkTime(
			@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody ApiTimeDataRequest timeDataRequest) {

		try {
			logger.info("Request received to create work time on TED \n {}", obj.writeValueAsString(timeDataRequest));
			response = service.createTimesheet(appName, timeDataRequest);
		} catch (Exception e) {
			logger.error("Error while parsing json.", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/create-travel-Time")
	public ResponseEntity<ApiTimeDataResponse> addTravelTime(
			@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody ApiTimeDataRequest timeDataRequest) {

		try {
			logger.info("Request received to create travel time on TED \n {}", obj.writeValueAsString(timeDataRequest));
			response = service.createTimesheet(appName, timeDataRequest);
		} catch (Exception e) {
			logger.error("Error while parsing json.", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/create-break-Time")
	public ResponseEntity<ApiTimeDataResponse> addBreakTime(
			@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody ApiTimeDataRequest timeDataRequest) {

		try {
			logger.info("Request received to create break time on TED \n {}", obj.writeValueAsString(timeDataRequest));
			response = service.createTimesheet(appName, timeDataRequest);
		} catch (Exception e) {
			logger.error("Error while parsing json.", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

}
