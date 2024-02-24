package com.rune.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.rune.service.UserAuthenticationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
public class TedHelperServiceController {

	Logger logger = LogManager.getLogger(TedHelperServiceController.class);

	@Autowired
	private ObjectMapper obj;
	@Autowired
	private TEDHelperService service;
	@Autowired
	private UserAuthenticationService userService;
	private TimeDataResponse response;

	@PostMapping("/create-work-time")
	public ResponseEntity<TimeDataResponse> addWorkTime(
			@Valid @NotNull @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @NotNull @NotBlank @RequestHeader(name = "Authorization") String token,
			@Valid @RequestBody TimeDataRequest timeDataRequest) {

		if (isAuthenticUser(token)) {
			try {
				response = service.processTimesheetRequest(appName, timeDataRequest);
				logger.info("Request received to create work time on TED \n {} \n and response received \n {}",
						obj.writeValueAsString(timeDataRequest), obj.writeValueAsString(response));
			} catch (Exception e) {
				logger.error("Error while parsing json.", e.getMessage());
			}
			return ResponseEntity.ok(response);
		} else {
			response = TimeDataResponse.builder().message(null).error("User Authentication Failed").build();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

	@PostMapping("/create-travel-time")
	public ResponseEntity<TimeDataResponse> addTravelTime(
			@Valid @NotNull @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @NotNull @NotBlank @RequestHeader(name = "Authorization") String token,
			@Valid @RequestBody TimeDataRequest timeDataRequest) {

		if (isAuthenticUser(token)) {
			try {
				response = service.processTimesheetRequest(appName, timeDataRequest);
				logger.info("Request received to create travel time on TED \n {} \n and response received \n {}",
						obj.writeValueAsString(timeDataRequest), obj.writeValueAsString(response));
			} catch (Exception e) {
				logger.error("Error while parsing json.", e.getMessage());
			}
			return ResponseEntity.ok(response);
		} else {
			response = TimeDataResponse.builder().message(null).error("User Authentication Failed").build();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

	@PostMapping("/create-break-time")
	public ResponseEntity<TimeDataResponse> addBreakTime(
			@Valid @NotNull @NotBlank @RequestHeader(name = "appName") String appName,
			@Valid @NotNull @NotBlank @RequestHeader(name = "Authorization") String token,
			@Valid @RequestBody TimeDataRequest timeDataRequest) {

		if (isAuthenticUser(token)) {
			try {
				response = service.processTimesheetRequest(appName, timeDataRequest);
				logger.info("Request received to create break time on TED \n {} \n and response received \n {}",
						obj.writeValueAsString(timeDataRequest), obj.writeValueAsString(response));
			} catch (Exception e) {
				logger.error("Error while parsing json.", e.getMessage());
			}
			return ResponseEntity.ok(response);
		} else {
			response = TimeDataResponse.builder().message(null).error("User Authentication Failed").build();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

	private boolean isAuthenticUser(String token) {
		logger.info("Validating user with the token");
		String[] tokenArr = token.split(" ");
		boolean isAuthenticated = userService.getUser(tokenArr[1]);
		return isAuthenticated;
	}

}
