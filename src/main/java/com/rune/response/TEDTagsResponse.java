package com.rune.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TEDTagsResponse {

	private int id;
	private String name;

}
