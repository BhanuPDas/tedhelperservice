package com.rune.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Activity {

	private int id;
	private String name;
	private boolean billable;
	private Tags[] tags;
	private int plannedQuota;
}
