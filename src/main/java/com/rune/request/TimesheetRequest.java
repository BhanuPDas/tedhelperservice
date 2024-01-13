package com.rune.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimesheetRequest {

	private String owner;
	private String ownerFullName;
	private String startDate;
	private String endDate;
	private StatusRequest status;
	private String timeZone;
	private TimesheetProjectRequest project;
	private TimesheetActivityRequest activity;
	private long createdDate;
	private int[] expenseIds;

}
