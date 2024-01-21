package com.rune.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rune.request.TimeDataRequest;
import com.rune.response.TimeDataResponse;
import com.rune.service.TEDHelperService;
import com.rune.service.UserAuthenticationService;

@ExtendWith(MockitoExtension.class)
public class TedHelperServiceControllerTest {

	@Mock
	private TEDHelperService service;
	@Mock
	private UserAuthenticationService userService;
	@Mock
	private ObjectMapper obj;
	@InjectMocks
	private TedHelperServiceController controller;

	@Test
	public void addWorkItemTest() throws Exception {
		lenient().when(userService.getUser(anyString())).thenReturn(true);
		lenient().when(service.processTimesheetRequest(anyString(), any()))
				.thenReturn(new TimeDataResponse("New Record", null));
		lenient().when(obj.writeValueAsString(any())).thenReturn("");
		ResponseEntity<TimeDataResponse> response = controller.addWorkTime("app1", "Token Test", new TimeDataRequest());
		assertNotNull(response);
		assertEquals("New Record", response.getBody().getMessage());
	}

	@Test
	public void addBreakItemTest() throws Exception {
		lenient().when(userService.getUser(anyString())).thenReturn(true);
		lenient().when(service.processTimesheetRequest(anyString(), any()))
				.thenReturn(new TimeDataResponse("New Record", null));
		lenient().when(obj.writeValueAsString(any())).thenReturn("");
		ResponseEntity<TimeDataResponse> response = controller.addBreakTime("app1", "Token Test",
				new TimeDataRequest());
		assertNotNull(response);
		assertEquals("New Record", response.getBody().getMessage());
	}

	@Test
	public void addTravelItemTest() throws Exception {
		lenient().when(userService.getUser(anyString())).thenReturn(true);
		lenient().when(service.processTimesheetRequest(anyString(), any()))
				.thenReturn(new TimeDataResponse("New Record", null));
		lenient().when(obj.writeValueAsString(any())).thenReturn("");
		ResponseEntity<TimeDataResponse> response = controller.addTravelTime("app1", "Token Test",
				new TimeDataRequest());
		assertNotNull(response);
		assertEquals("New Record", response.getBody().getMessage());
	}
}
