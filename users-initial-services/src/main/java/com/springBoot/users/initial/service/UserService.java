package com.springBoot.users.initial.service;

import com.springBoot.users.initial.entity.UserEntity;
import com.springBoot.users.initial.model.CreateUserRequestModel;
import com.springBoot.users.initial.model.UserResponseModel;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
	
	public UserEntity createUser(CreateUserRequestModel createUserRequestModel);
	
	public UserEntity getUserByUserId(String userId);
	
	//Microservice communication using Rest Template
	//Sender Microservice.
	public UserResponseModel getAllAlbumsByUserIdRestTemplate(String userId);
	
	//Microservice communication using Feign
	//Sender Microservice.
	public UserResponseModel getAllAlbumsByUserIdFeign(String userId);

}
