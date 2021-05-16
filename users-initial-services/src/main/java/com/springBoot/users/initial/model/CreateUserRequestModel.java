package com.springBoot.users.initial.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	
	@NotNull(message = "firstName can not be null")
	@Size(min = 3, max = 50, message = "First Name must be between 2 to 50 character only")
	private String firstName;
	
	@NotNull(message = "lastName can not be null")
	@Size(min = 1, max = 50, message = "Last tName must be between 1 to 50 character only")
	private String lastName;

	@NotNull(message = "email can not be null")
	@Email(message = "must be a valid email")
	private String email;
	
	@NotNull(message = "Password can not be null")
	@Size(min = 2, max = 32, message = "password must be between 2 to 32 character only")
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
