package com.rune.request;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimesheetRequest {

	private String owner;
	private String ownerFullName;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private StatusRequest status;
	private String timeZone;
	private TimesheetProjectRequest project;
	private TimesheetActivityRequest activity;
	private long createdDate;
	private int[] expenseIds;

}
