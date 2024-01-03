package com.rune.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rune.repository.TEDHelperRepoBean;
import com.rune.repository.TEDHelperRepository;
import com.rune.request.TimeDataRequest;
import com.rune.response.TEDUserResponse;
import com.rune.response.TimeDataResponse;

/*
 * Steps for creating a timesheet
 * 1)Check for uuid, if the user exists on TED.
 * 2)If yes, use the existing data else create a new user record
 * 3)Check if the activity exist else create new activity
 * 4)Check if project exist else create new project
 * 5)Create timesheet with the details received
 * */
@Service
public class TEDHelperService {

	Logger logger = LogManager.getLogger(TEDHelperService.class);
	private static final String APP_NAME1 = "TEDHELPER";
	private static final String APP_NAME2 = "TED";

	@Autowired
	private ObjectMapper obj;
	@Autowired
	private RestTemplate template;
	@Autowired
	private TEDHelperRepository repository;
	@Value("${ted.url}")
	private StringBuilder tedUrl;

	public TimeDataResponse processTimesheetRequest(String appName, TimeDataRequest request) {

		try {
			if (request.getType().equalsIgnoreCase("work")) {
				createTimesheet(request);
				TimeDataResponse response = TimeDataResponse.builder()
						.message("Work Data has been successfully updated on TED").error(null).build();
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(appName).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString(request))
						.response(obj.writeValueAsString(response)).type("work").build();
				repository.save(bean);
				return (response);
			} else if (request.getType().equalsIgnoreCase("break")) {
				createTimesheet(request);
				TimeDataResponse response = TimeDataResponse.builder()
						.message("Break Data has been successfully updated on TED").error(null).build();
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(appName).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString(request))
						.response(obj.writeValueAsString(response)).type("break").build();
				repository.save(bean);
				return (response);
			} else {
				createTimesheet(request);
				TimeDataResponse response = TimeDataResponse.builder()
						.message("Travel Data has been successfully updated on TED").error(null).build();
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(appName).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString(request))
						.response(obj.writeValueAsString(response)).type("travel").build();
				repository.save(bean);
				return (response);
			}
		} catch (Exception ex) {
			logger.error("Error occured while creating timesheet", ex.getMessage());
			return (TimeDataResponse.builder().message(null)
					.error("Error occured while creating timesheet. Please try again.").build());
		}
	}

	private void createTimesheet(TimeDataRequest request) throws Exception {
		try {
			TEDUserResponse user = checkUserExist(request.getUuid());
			checkProjectExist();
			checkActivityExist();

			// Frame request to call Timesheet

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	// Checks User exists else create user
	private TEDUserResponse checkUserExist(String uuid) throws Exception {
		String userUrl = tedUrl.append("/api/user/{uuId}").toString();
		Map<String, String> param = new HashMap<String, String>();
		param.put("uuId", uuid);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity userEntity = new HttpEntity(headers);
		try {
			ResponseEntity<TEDUserResponse[]> userResponse = template.exchange(userUrl, HttpMethod.GET, userEntity,
					TEDUserResponse[].class, param);
			if (userResponse != null) {
				logger.info("Request to check if user exists \n {} \n and response received \n {}",
						obj.writeValueAsString("{}"), obj.writeValueAsString(userResponse.getBody()[0]));
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString("{}"))
						.response(obj.writeValueAsString(userResponse.getBody()[0])).type("user").build();
				repository.save(bean);
				return (userResponse.getBody()[0]);
			} else {
				// Create User;
				return null;
			}
		} catch (Exception ex) {
			TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
					.error("Exception while getting user information").createdOn(LocalDateTime.now())
					.request(obj.writeValueAsString(userEntity.getBody())).response(null).type("user").build();
			repository.save(bean);
			throw new Exception("Exception while getting user information::: " + ex.getMessage());
		}
	}

	// Checks Activity exists else create activity
	private void checkActivityExist() {

	}

	// Checks Project exists else create project
	private void checkProjectExist() {

	}

}
