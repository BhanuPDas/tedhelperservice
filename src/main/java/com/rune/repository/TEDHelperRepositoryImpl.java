package com.rune.repository;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "TEDRequestsRepo")
@Entity
public class TEDHelperRepositoryImpl implements Serializable {

	private static final long serialVersionUID = 4952412050331302998L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// Requesting App
	@Column(name = "app_name_1")
	private String appName1;
	// Responding App
	@Column(name = "app_name_2")
	private String appName2;
	@Column(name = "request")
	private String request;
	@Column(name = "response")
	private String response;
	@Column(name = "error")
	private String error;
	@Column(name = "createdOn")
	private LocalDateTime createdOn;

}
