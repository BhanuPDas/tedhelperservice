package com.rune.response;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimesheetResponse {

	private int id;
	private String owner;
	private String ownerFullName;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private String timeZone;
	private long createdDate;
	private int[] expenses;
	private TEDStatusResponse status;
	private TEDActivityResponse activity;
	private TimesheetProjectResponse project;

}
