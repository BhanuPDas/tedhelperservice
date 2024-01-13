package com.rune.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimesheetResponse {

	private int id;
	private String owner;
	private String ownerFullName;
	private String startDate;
	private String endDate;
	private String timeZone;
	private long createdDate;
	private int[] expenses;
	private TEDStatusResponse status;
	private TEDActivityResponse activity;
	private TimesheetProjectResponse project;

}
