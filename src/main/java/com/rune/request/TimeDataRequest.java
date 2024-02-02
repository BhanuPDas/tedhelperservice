package com.rune.request;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeDataRequest implements Serializable{

	private static final long serialVersionUID = 5592996058911704307L;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private String type;
	private int[] expenseIds;
	private TimeProjectRequest project;
	private TimeActivityRequest activity;
}
