package com.rune.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {

	private int id;
	private String name;
	private String color;
	private Tags[] tags;

}
