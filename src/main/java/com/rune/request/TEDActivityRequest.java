package com.rune.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TEDActivityRequest {

	private TEDProjectRequest project;
	private String name;
	private int plannedQuota;
	private boolean billable;
	private TimeTagsRequest[] tags;
}
