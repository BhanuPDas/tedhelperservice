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
public class TimeActivityRequest {

	@NotBlank
	@NotNull
	private String name;
	private boolean billable;
	private TimeTagsRequest[] tags;
	private int plannedQuota;
}
