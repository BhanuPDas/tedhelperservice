package com.rune.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TEDProjectResponse {

	private int id;
	private String name;
	private String color;
	private TEDActivityResponse[] activities;
	private TEDTagsResponse[] tags;

}
