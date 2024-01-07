package com.rune.request;

import com.rune.response.TEDTagsResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimesheetActivityRequest {

	private int id;
	private String name;
	private boolean billable;
	private int plannedQuota;
	private TEDTagsResponse[] tags;
}
