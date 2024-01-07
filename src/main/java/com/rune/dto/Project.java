package com.rune.dto;

import com.rune.response.TEDTagsResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

	private int id;
	private String name;
	private String color;
	private TEDTagsResponse[] tags;
}
