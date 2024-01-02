package com.rune.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class TimeDataResponse {
	
	private String message;
	private String error;
}
