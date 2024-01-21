package com.rune.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
	
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
