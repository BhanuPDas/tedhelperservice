package com.rune.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TEDUserRequest {
	
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
