package com.rune.response;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContentResponse {

	private String id;
	private String email;
	private String firstName;
	private String lastName;
	private String timeZone;
	private String role;
	private boolean activated;
	private LocalDateTime deactivationDate;
	private boolean active;
	private String language;
	private boolean companyOwner;

}
