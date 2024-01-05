package com.rune.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rune.request.TEDUserRequest;

/*
 * This class generates random user details from the static list.
 * This class will only be used to simulate the user flow
 */
@Service
public class TEDHelperUserService {

	public TEDUserRequest createUser() {

		String[] firstNames = { "Andrew", "Bastian", "Teddy", "Dave", "Nady", "Daniel", "Harsha", "Nisha", "Robin",
				"Neel" };
		String[] lastNames = { "Jonson", "Jaafer", "Soro", "Mid", "West", "Summer", "Greens", "Bing", "Buffaay",
				"Ben" };
		Random random1 = new Random();
		String firstName = firstNames[random1.nextInt(0, firstNames.length)];
		String lastName = lastNames[random1.nextInt(0, lastNames.length)];
		String userId = UUID.randomUUID().toString();
		String timeZone = "Europe/Berlin";
		String email = firstName + "." + lastName + "@moots.io";
		String role = "interviewer";
		boolean activated = false;
		boolean active = true;
		LocalDateTime deactivationDate = null;
		String language = "en-US";
		boolean companyOwner = false;
		TEDUserRequest userRequest = TEDUserRequest.builder().activated(activated).active(active)
				.companyOwner(companyOwner).deactivationDate(deactivationDate).email(email).firstName(firstName)
				.lastName(lastName).language(language).role(role).timeZone(timeZone).userId(userId).build();

		return userRequest;
	}
}
