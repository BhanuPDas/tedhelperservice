package com.rune.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.rune.request.TimeUserRequest;
import com.rune.request.TimesheetActivityRequest;
import com.rune.request.TimesheetProjectRequest;
import com.rune.request.TimesheetRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rune.dto.Activity;
import com.rune.dto.Project;
import com.rune.dto.ProjectActivity;
import com.rune.dto.UserDetails;
import com.rune.repository.TEDHelperRepoBean;
import com.rune.repository.TEDHelperRepository;
import com.rune.request.StatusRequest;
import com.rune.request.TEDActivityRequest;
import com.rune.request.TEDProjectRequest;
import com.rune.request.TEDUpdateProjectRequest;
import com.rune.request.TimeActivityRequest;
import com.rune.request.TimeDataRequest;
import com.rune.request.TimeProjectRequest;
import com.rune.response.TEDActivityResponse;
import com.rune.response.TEDProjectResponse;
import com.rune.response.TEDUserResponse;
import com.rune.response.TimeDataResponse;
import com.rune.response.TimesheetResponse;

/*
 * Steps for creating a timesheet
 * 1)Check for uuid, if the user exists on TED.
 * 2)If yes, use the existing data else create a new user record
 * 3)Check if project exist else create new project
 * 4)Check if the activity exist else create new activity
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
	@Autowired
	private UserDetails user;
	@Value("${ted.url}")
	private String tedUrl;

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
			ex.printStackTrace();
			return (TimeDataResponse.builder().message(null)
					.error("Error occured while creating timesheet. Please try again.").build());
		}
	}

	private void createTimesheet(TimeDataRequest request) throws Exception {
		try {
			TEDUserResponse user = checkUserExist(request);
			ProjectActivity prActivity = checkProjectExist(request.getProject(), request.getActivity());
			StatusRequest status = StatusRequest.builder().name("pending").build();
			TimesheetActivityRequest act = TimesheetActivityRequest.builder().id(prActivity.getActivity().getId())
					.name(prActivity.getActivity().getName()).billable(prActivity.getActivity().isBillable())
					.plannedQuota(prActivity.getActivity().getPlannedQuota()).tags(prActivity.getActivity().getTags())
					.build();
			TimesheetProjectRequest pro = TimesheetProjectRequest.builder().id(prActivity.getProject().getId())
					.name(prActivity.getProject().getName()).color(prActivity.getProject().getColor())
					.tags(prActivity.getProject().getTags()).build();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
			String startDate = request.getStartDate().format(formatter);
			String endDate = request.getEndDate().format(formatter);
			TimesheetRequest timesheetRequest = TimesheetRequest.builder().owner(user.getUserId())
					.ownerFullName(user.getFirstName() + " " + user.getLastName()).startDate(startDate).endDate(endDate)
					.timeZone(user.getTimeZone()).status(status).expenseIds(request.getExpenseIds())
					.createdDate(Instant.now().toEpochMilli()).activity(act).project(pro).build();
			String timesheetUrl = tedUrl.concat("/api/timesheets");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<TimesheetRequest> timesheetEntity = new HttpEntity<TimesheetRequest>(timesheetRequest, headers);
			try {
				ResponseEntity<TimesheetResponse> timesheetResponse = template.exchange(timesheetUrl, HttpMethod.POST,
						timesheetEntity, TimesheetResponse.class);
				if (timesheetResponse != null) {
					logger.info("Request to create timesheet \n {} \n and response received \n {}",
							obj.writeValueAsString(timesheetEntity.getBody()),
							obj.writeValueAsString(timesheetResponse.getBody()));
					TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
							.error(null).createdOn(LocalDateTime.now())
							.request(obj.writeValueAsString(timesheetEntity.getBody()))
							.response(obj.writeValueAsString(timesheetResponse.getBody())).type("timesheet").build();
					repository.save(bean);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
						.error("Exception while creating timesheet information").createdOn(LocalDateTime.now())
						.request(obj.writeValueAsString(timesheetEntity.getBody())).response(null).type("timesheet")
						.build();
				repository.save(bean);
				throw new Exception("Exception while creating timesheet information::: " + ex.getMessage());
			}

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	// Checks User exists else create user
	@SuppressWarnings("all")
	private TEDUserResponse checkUserExist(TimeDataRequest request) throws Exception {
		TEDUserResponse response = null;
		if (user.getUserId() != null && !user.getUserId().isBlank()) {
			String userUrl = tedUrl.concat("/api/user/{uuId}");
			Map<String, String> param = new HashMap<String, String>();
			param.put("uuId", user.getUserId());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity userEntity = new HttpEntity(headers);
			try {
				ResponseEntity<TEDUserResponse[]> userResponse = template.exchange(userUrl, HttpMethod.GET, userEntity,
						TEDUserResponse[].class, param);
				if (userResponse.getBody() != null && userResponse.getBody().length != 0) {
					logger.info("Request to check if user exists \n {} \n and response received \n {}",
							obj.writeValueAsString("{}"), obj.writeValueAsString(userResponse.getBody()[0]));
					TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
							.error(null).createdOn(LocalDateTime.now()).request(obj.writeValueAsString("{}"))
							.response(obj.writeValueAsString(userResponse.getBody()[0])).type("user").build();
					repository.save(bean);
					return (userResponse.getBody()[0]);
				} else {
					response = createUserInTed(request);
					return response;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
						.error("Exception while getting user information").createdOn(LocalDateTime.now())
						.request(obj.writeValueAsString("{}")).response(null).type("user").build();
				repository.save(bean);
				throw new Exception("Exception while getting user information::: " + ex.getMessage());
			}
		}
		return response;
	}

	// Checks Project exists else create project
	@SuppressWarnings("all")
	private ProjectActivity checkProjectExist(TimeProjectRequest projectName, TimeActivityRequest activityName)
			throws Exception {
		Project prj = new Project();
		Activity act = new Activity();
		boolean projectFound = false;
		boolean activityFound = false;
		String projectUrl = tedUrl.concat("/api/projects");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity userEntity = new HttpEntity(headers);
		try {
			ResponseEntity<TEDProjectResponse[]> projectResponse = template.exchange(projectUrl, HttpMethod.GET,
					userEntity, TEDProjectResponse[].class);
			if (projectResponse.getBody() != null && projectResponse.getBody().length != 0) {
				for (TEDProjectResponse prjRes : projectResponse.getBody()) {
					if (projectName.getName().equals(prjRes.getName()) && !projectFound) {
						prj = Project.builder().id(prjRes.getId()).name(prjRes.getName()).color(prjRes.getColor())
								.tags(prjRes.getTags()).build();
						projectFound = true;
					}
					if (prjRes.getActivities() != null) {
						for (TEDActivityResponse activity : prjRes.getActivities()) {
							if (activityName.getName().equals(activity.getName()) && !activityFound) {
								if (projectFound) {
									act = Activity.builder().id(activity.getId()).name(activity.getName())
											.tags(activity.getTags()).billable(activity.isBillable())
											.plannedQuota(activity.getPlannedQuota()).build();
									activityFound = true;
									break;
								}
							}
						}
					}
				}
				logger.info("Request to check if project exists \n {} \n and response received \n {}",
						obj.writeValueAsString("{}"), obj.writeValueAsString(projectResponse.getBody()));
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString("{}"))
						.response(obj.writeValueAsString(projectResponse.getBody())).type("project").build();
				repository.save(bean);
				ProjectActivity practivity = new ProjectActivity();
				if (!projectFound || !activityFound) {
					Project pro = createProjectInTed(projectName);
					practivity = ProjectActivity.builder().activity(createActivityInTed(pro, activityName)).project(pro)
							.build();
				} else {
					practivity = ProjectActivity.builder().activity(act).project(prj).build();
				}
				return (practivity);
			} else {
				Project pro = createProjectInTed(projectName);
				Activity activity = createActivityInTed(pro, activityName);
				ProjectActivity practivity = ProjectActivity.builder().activity(activity).project(pro).build();
				return practivity;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
					.error("Exception while getting project information").createdOn(LocalDateTime.now())
					.request(obj.writeValueAsString("{}")).response(null).type("project").build();
			repository.save(bean);
			throw new Exception("Exception while getting project information::: " + ex.getMessage());
		}

	}

	private TEDUserResponse createUserInTed(TimeDataRequest request) throws Exception {
		TimeUserRequest userRequest = TimeUserRequest.builder().firstName(user.getFirstName())
				.lastName(user.getLastName()).activated(user.isActivated())
				.active(user.isActive()).companyOwner(user.isCompanyOwner())
				.deactivationDate(user.getDeactivationDate()).email(user.getEmail())
				.role(user.getRole()).timeZone(user.getTimeZone())
				.userId(user.getUserId()).language(user.getLanguage()).build();
		String userUrl = tedUrl.concat("/api/user/create").toString();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TimeUserRequest> userEntity = new HttpEntity<TimeUserRequest>(userRequest, headers);
		try {
			ResponseEntity<TEDUserResponse> userResponse = template.exchange(userUrl, HttpMethod.POST, userEntity,
					TEDUserResponse.class);
			if (userResponse != null) {
				logger.info("Request to create user \n {} \n and response received \n {}",
						obj.writeValueAsString(userEntity.getBody()), obj.writeValueAsString(userResponse.getBody()));
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString("{}"))
						.response(obj.writeValueAsString(userResponse.getBody())).type("user").build();
				repository.save(bean);

			}
			return (userResponse.getBody());
		} catch (Exception ex) {
			ex.printStackTrace();
			TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
					.error("Exception while creating user information").createdOn(LocalDateTime.now())
					.request(obj.writeValueAsString(userEntity.getBody())).response(null).type("user").build();
			repository.save(bean);
			throw new Exception("Exception while creating user information::: " + ex.getMessage());
		}

	}

	private Project createProjectInTed(TimeProjectRequest project) throws Exception {
		Project pro = new Project();
		String projectUrl = tedUrl.concat("/api/projects").toString();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TimeProjectRequest> projectEntity = new HttpEntity<TimeProjectRequest>(project, headers);
		try {
			ResponseEntity<TEDProjectResponse> projectResponse = template.exchange(projectUrl, HttpMethod.POST,
					projectEntity, TEDProjectResponse.class);
			if (projectResponse != null) {
				logger.info("Request to create project \n {} \n and response received \n {}",
						obj.writeValueAsString(projectEntity.getBody()),
						obj.writeValueAsString(projectResponse.getBody()));
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString(projectEntity.getBody()))
						.response(obj.writeValueAsString(projectResponse.getBody())).type("project").build();
				repository.save(bean);
				pro = Project.builder().id(projectResponse.getBody().getId()).name(projectResponse.getBody().getName())
						.color(projectResponse.getBody().getColor()).tags(projectResponse.getBody().getTags()).build();
			}
			return (pro);
		} catch (Exception ex) {
			ex.printStackTrace();
			TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
					.error("Exception while creating project information").createdOn(LocalDateTime.now())
					.request(obj.writeValueAsString(projectEntity.getBody())).response(null).type("project").build();
			repository.save(bean);
			throw new Exception("Exception while creating project information::: " + ex.getMessage());
		}
	}

	private Activity createActivityInTed(Project pro, TimeActivityRequest activity) throws Exception {
		Activity act = new Activity();
		TEDProjectRequest proID = TEDProjectRequest.builder().id(pro.getId()).build();
		String activityUrl = tedUrl.concat("/api/activity");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		TEDActivityRequest activityRequest = TEDActivityRequest.builder().project(proID).name(activity.getName())
				.plannedQuota(activity.getPlannedQuota()).billable(activity.isBillable()).tags(activity.getTags())
				.build();
		HttpEntity<TEDActivityRequest> activityEntity = new HttpEntity<TEDActivityRequest>(activityRequest, headers);
		try {
			ResponseEntity<TEDActivityResponse> activityResponse = template.exchange(activityUrl, HttpMethod.POST,
					activityEntity, TEDActivityResponse.class);
			if (activityResponse != null) {
				logger.info("Request to create activity \n {} \n and response received \n {}",
						obj.writeValueAsString(activityEntity.getBody()),
						obj.writeValueAsString(activityResponse.getBody()));
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString(activityEntity.getBody()))
						.response(obj.writeValueAsString(activityResponse.getBody())).type("activity").build();
				repository.save(bean);
				act = Activity.builder().id(activityResponse.getBody().getId())
						.name(activityResponse.getBody().getName())
						.plannedQuota(activityResponse.getBody().getPlannedQuota())
						.tags(activityResponse.getBody().getTags()).billable(activityResponse.getBody().isBillable())
						.build();
			}
			updateProject(pro, act);
			return (act);
		} catch (Exception ex) {
			ex.printStackTrace();
			TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
					.error("Exception while creating activity information").createdOn(LocalDateTime.now())
					.request(obj.writeValueAsString(activityEntity.getBody())).response(null).type("activity").build();
			repository.save(bean);
			throw new Exception("Exception while creating project information::: " + ex.getMessage());
		}
	}

	@SuppressWarnings("all")
	private void updateProject(Project pro, Activity activity) throws Exception {
		String projectUrl = tedUrl.concat("/api/projects/{id}");
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("id", pro.getId());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity projectEntity = new HttpEntity(headers);
		try {
			ResponseEntity<TEDProjectResponse[]> projectResponse = template.exchange(projectUrl, HttpMethod.GET,
					projectEntity, TEDProjectResponse[].class, param);
			if (projectResponse.getBody() != null && projectResponse.getBody().length != 0) {
				logger.info("Request to fetch project \n {} \n and response received \n {}",
						obj.writeValueAsString("{}"), obj.writeValueAsString(projectResponse.getBody()[0]));
				TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2).error(null)
						.createdOn(LocalDateTime.now()).request(obj.writeValueAsString("{}"))
						.response(obj.writeValueAsString(projectResponse.getBody()[0])).type("project").build();
				repository.save(bean);
				List<TEDActivityResponse> activityList;
				if (projectResponse.getBody()[0].getActivities() != null) {
					activityList = Arrays.asList(projectResponse.getBody()[0].getActivities());
				} else {
					activityList = new ArrayList<TEDActivityResponse>();
				}
				TEDActivityResponse tedActivity = TEDActivityResponse.builder().id(activity.getId())
						.name(activity.getName()).billable(activity.isBillable())
						.plannedQuota(activity.getPlannedQuota()).tags(activity.getTags()).build();
				activityList.add(tedActivity);
				TEDActivityResponse[] activityArray = activityList.toArray(TEDActivityResponse[]::new);
				TEDUpdateProjectRequest updateRequest = TEDUpdateProjectRequest.builder().id(pro.getId())
						.color(pro.getColor()).name(pro.getName()).tags(projectResponse.getBody()[0].getTags())
						.activities(activityArray).build();
				String updateUrl = tedUrl.concat("/api/projects/update/{id}");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<TEDUpdateProjectRequest> updateEntity = new HttpEntity<TEDUpdateProjectRequest>(
						updateRequest, headers);
				ResponseEntity<TEDProjectResponse> updateResponse = template.exchange(updateUrl, HttpMethod.PUT,
						updateEntity, TEDProjectResponse.class, param);
				if (updateResponse != null) {
					logger.info("Request to update project \n {} \n and response received \n {}",
							obj.writeValueAsString(updateEntity.getBody()),
							obj.writeValueAsString(updateResponse.getBody()));
					bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2).error(null)
							.createdOn(LocalDateTime.now()).request(obj.writeValueAsString(updateEntity.getBody()))
							.response(obj.writeValueAsString(updateResponse.getBody())).type("project").build();
					repository.save(bean);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			TEDHelperRepoBean bean = TEDHelperRepoBean.builder().appName1(APP_NAME1).appName2(APP_NAME2)
					.error("Exception while updating project information").createdOn(LocalDateTime.now())
					.request(obj.writeValueAsString(projectEntity.getBody())).response(null).type("project").build();
			repository.save(bean);
			throw new Exception("Exception while updating project information::: " + ex.getMessage());
		}
	}

}
