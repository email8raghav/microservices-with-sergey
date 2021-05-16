package com.springBoot.users.initial.exception;


public class UserNotFoundException extends RuntimeException {
	
	
	private static final long serialVersionUID = -971708534130483012L;
	
	public UserNotFoundException(String message){
		super(message);
	}

}
