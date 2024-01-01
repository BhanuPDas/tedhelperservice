package com.rune.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rune.request.ApiTimeDataRequest;
import com.rune.response.ApiTimeDataResponse;

@Service
public class TEDHelperService {

	Logger logger = LogManager.getLogger(TEDHelperService.class);

	@Autowired
	private ObjectMapper obj;
	@Autowired
	private RestTemplate template;

	public ApiTimeDataResponse createTimesheet(String appName, ApiTimeDataRequest request) {

		if (request.getType().equalsIgnoreCase("work")) {
			return (ApiTimeDataResponse.builder().message("Work Data has been successfully updated on TED").error(null)
					.build());
		} else if (request.getType().equalsIgnoreCase("break")) {
			return (ApiTimeDataResponse.builder().message("Break Data has been successfully updated on TED").error(null)
					.build());
		} else {
			return (ApiTimeDataResponse.builder().message("Travel Data has been successfully updated on TED")
					.error(null).build());
		}
	}

}
