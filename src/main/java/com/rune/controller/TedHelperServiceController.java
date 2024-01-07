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
import com.rune.request.TimeDataRequest;
import com.rune.response.TimeDataResponse;
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
	private TimeDataResponse response;

	@PostMapping("/create-work-time")
	public ResponseEntity<TimeDataResponse> addWorkTime(
			@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody TimeDataRequest timeDataRequest) {

		try {
			response = service.processTimesheetRequest(appName, timeDataRequest);
			logger.info("Request received to create work time on TED \n {} \n and response received \n {}",
					obj.writeValueAsString(timeDataRequest), obj.writeValueAsString(response));
		} catch (Exception e) {
			logger.error("Error while parsing json.", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/create-travel-time")
	public ResponseEntity<TimeDataResponse> addTravelTime(
			@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody TimeDataRequest timeDataRequest) {

		try {
			response = service.processTimesheetRequest(appName, timeDataRequest);
			logger.info("Request received to create travel time on TED \n {} \n and response received \n {}",
					obj.writeValueAsString(timeDataRequest), obj.writeValueAsString(response));
		} catch (Exception e) {
			logger.error("Error while parsing json.", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/create-break-time")
	public ResponseEntity<TimeDataResponse> addBreakTime(
			@Valid @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @RequestBody TimeDataRequest timeDataRequest) {

		try {
			response = service.processTimesheetRequest(appName, timeDataRequest);
			logger.info("Request received to create break time on TED \n {} \n and response received \n {}",
					obj.writeValueAsString(timeDataRequest), obj.writeValueAsString(response));
		} catch (Exception e) {
			logger.error("Error while parsing json.", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

}
