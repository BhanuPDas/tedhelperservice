package com.rune.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TEDActivityResponse {

	private int id;
	private String name;
	private boolean billable;
	private int plannedQuota;
	private TEDTagsResponse[] tags;

}
