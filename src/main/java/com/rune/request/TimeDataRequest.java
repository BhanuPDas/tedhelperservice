package com.rune.request;

import java.io.Serializable;
import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeDataRequest implements Serializable{

	private static final long serialVersionUID = 5592996058911704307L;
	@NotNull
	private TimeUserRequest user;
	@NotNull
	private ZonedDateTime startDate;
	@NotNull
	private ZonedDateTime endDate;
	@NotBlank
	@NotNull
	private String type;
	private int[] expenseIds;
	@NotNull
	private TimeProjectRequest project;
	@NotNull
	private TimeActivityRequest activity;
}
