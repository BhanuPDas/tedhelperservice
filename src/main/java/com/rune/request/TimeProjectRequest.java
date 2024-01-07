package com.rune.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeProjectRequest {

	@NotNull
	@NotBlank
	private String name;
	private String color;
	private TimeTagsRequest[] tags;

}
