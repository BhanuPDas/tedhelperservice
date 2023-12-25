package com.rune.request;

import java.io.Serializable;
import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiTimeDataRequest implements Serializable{

	private static final long serialVersionUID = 5592996058911704307L;
	
	@NotBlank
	@NotNull
	private String uuid;
	@NotNull
	private ZonedDateTime startDate;
	@NotNull
	private ZonedDateTime endDate;
	@NotBlank
	@NotNull
	private String type;
	private int[] expenseIds;
	@NotNull
	private Project project;
	@NotNull
	private Activity activity;
	private int[] expenses;
}
