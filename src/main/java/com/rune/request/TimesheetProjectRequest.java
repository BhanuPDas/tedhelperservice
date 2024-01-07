package com.rune.request;

import com.rune.response.TEDTagsResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimesheetProjectRequest {

	private int id;
	private String name;
	private String color;
	private TEDTagsResponse[] tags;

}
