package com.rune.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TEDUserResponse {

	private int id;
	private String firstName;
	private String lastName;
	private String userId;
	private String timeZone;
	private String email;
	private String role;
	private boolean activated;
	private boolean active;
	private LocalDateTime deactivationDate;
	private String language;
	private boolean companyOwner;

}
