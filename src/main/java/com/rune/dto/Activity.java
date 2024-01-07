package com.rune.dto;

import com.rune.response.TEDTagsResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

	private int id;
	private String name;
	private boolean billable;
	private int plannedQuota;
	private TEDTagsResponse[] tags;
}
