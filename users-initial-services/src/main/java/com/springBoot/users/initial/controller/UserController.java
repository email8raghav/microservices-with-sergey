package com.springBoot.users.initial.controller;

import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springBoot.users.initial.entity.UserEntity;
import com.springBoot.users.initial.model.CreateUserRequestModel;
import com.springBoot.users.initial.model.CreateUserResponseModel;
import com.springBoot.users.initial.model.UserResponseModel;
import com.springBoot.users.initial.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Environment env;

	@Autowired
	private UserService userService;

	@GetMapping("/status/check")
	public String status() {
		return "working" + " on port number: " + env.getProperty("local.server.port")
		+ " with tocken: " + env.getProperty("token.secret");
	}

	/*
	 * Create User for the very first time
	 */
	@PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel createUserRequestModel) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  
		UserEntity userEntity = userService.createUser(createUserRequestModel);
		CreateUserResponseModel createUserResponseModel = modelMapper.map(userEntity, CreateUserResponseModel.class);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}")
				.buildAndExpand(createUserResponseModel.getUserId()).toUri();

		return ResponseEntity.created(uri).body(createUserResponseModel);

	}
	
	/*
	 * Get User by userId
	 */
	@GetMapping(value = "/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CreateUserResponseModel> getUser(@PathVariable("userId") String userId){
		
		UserEntity userEntity = userService.getUserByUserId(userId);
		ModelMapper modelMapper = new ModelMapper();
		CreateUserResponseModel returnValue = modelMapper.map(userEntity, CreateUserResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	/*
	 * Get All albums of a user on behalf of publicUserId
	 * 
	 * communication between microservices users-ws<=>albums-ws using Rest Template
	 * 
	 * For more information visit appsdeveloperblog sergey karpolov
	 */
	@GetMapping(value = "/{userId}/albums1", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserResponseModel> getAllAlbums1(@PathVariable("userId") String userId){
		
		UserResponseModel allAlbumsByUserId = userService.getAllAlbumsByUserIdRestTemplate(userId);
		return ResponseEntity.status(HttpStatus.OK).body(allAlbumsByUserId);
	}
	
	/*
	 * Get All albums of a user on behalf of publicUserId
	 * 
	 * communication between microservices users-ws<=>albums-ws using Feign
	 * 
	 */
	@GetMapping(value = "/{userId}/albums2", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserResponseModel> getAllAlbums2(@PathVariable("userId") String userId){
		
		UserResponseModel allAlbumsByUserId = userService.getAllAlbumsByUserIdFeign(userId);
		return ResponseEntity.status(HttpStatus.OK).body(allAlbumsByUserId);
	}
	
	
}


