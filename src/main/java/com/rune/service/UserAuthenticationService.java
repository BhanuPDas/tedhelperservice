package com.rune.service;

import java.time.LocalDateTime;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rune.dto.UserDetails;
import com.rune.repository.TEDHelperRepoBean;
import com.rune.repository.TEDHelperRepository;
import com.rune.response.UserAuthenticationResponse;

@Service
public class UserAuthenticationService {

	Logger logger = LogManager.getLogger(UserAuthenticationService.class);

	private static final String APP_NAME1 = "TEDHELPER";
	private static final String APP_NAME2 = "PROVIDER";
	@Autowired
	private ObjectMapper obj;
	@Autowired
	private RestTemplate template;
	@Autowired
	private TEDHelperRepository repository;
	@Autowired
	private UserDetails user;
	@Value("${providerservice.url}")
	private String providerUrl;

	@SuppressWarnings("all")
	public boolean getUser(String token) {
		String userUrl = providerUrl.concat("/api/v1/user");
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity userEntity = new HttpEntity(headers);
		try {
			ResponseEntity<UserAuthenticationResponse> userResponse = template.exchange(userUrl, HttpMethod.GET,
					userEntity, UserAuthenticationResponse.class);
			if (userResponse.getBody() != null && userResponse.getBody().getStatus().equals("success")
					&& userResponse.getBody().getContent().length != 0) {
				logger.info("Request to authenticate user \n {} \n and response received \n {}",
						obj.writeValueAsString("{}"), obj.writeValueAsString(userResponse.getBody()));
				if (user != null) {
					user.setFirstName(userResponse.getBody().getContent()[0].getFirstName());
					user.setLastName(userResponse.getBody().getContent()[0].getLastName());
					user.setUserId(userResponse.getBody().getContent()[0].getId());
					user.setEmail(userResponse.getBody().getContent()[0].getEmail());
					user.setRole(userResponse.getBody().getContent()[0].getRole());
					user.setTimeZone(userResponse.getBody().getContent()[0].getTimeZone());
					user.setActivated(userResponse.getBody().getContent()[0].isActivated());
					user.setActive(userResponse.getBody().getContent()[0].isActive());
					user.setCompanyOwner(userResponse.getBody().getContent()[0].isCompanyOwner());
					user.setDeactivationDate(userResponse.getBody().getContent()[0].getDeactivationDate());
					user.setLanguage(userResponse.getBody().getContent()[0].getLanguage());
				}
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString("{}"))
						.response(obj.writeValueAsString(userResponse.getBody())).type("authenticate").build();
				repository.save(bean);
				return true;
			}
		} catch (Exception ex) {
			try {
				ex.printStackTrace();
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
						.error("Exception while authentication user").createdOn(LocalDateTime.now())
						.request(obj.writeValueAsString("{}")).response(null).type("authenticate").build();
				repository.save(bean);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return false;

	}
}
