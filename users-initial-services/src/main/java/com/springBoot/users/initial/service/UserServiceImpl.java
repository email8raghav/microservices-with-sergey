package com.springBoot.users.initial.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springBoot.users.initial.entity.UserEntity;
import com.springBoot.users.initial.feign.AlbumServiceClient;
import com.springBoot.users.initial.model.AlbumResponseModel;
import com.springBoot.users.initial.model.CreateUserRequestModel;
import com.springBoot.users.initial.model.UserResponseModel;
import com.springBoot.users.initial.repository.UserRepository;

import feign.FeignException.FeignClientException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private Environment env;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AlbumServiceClient albumServiceClient;


	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	/*
	 * This method is internally called by authenticate method of Authentication
	 * filter to verify credentials against database.
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> optional = userRepository.findByEmail(email);
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			// import org.springframework.security.core.userdetails.User;//This user
			// implements UserDetails.
			// return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
			// true, true, true, true, new ArrayList<>());
			return userEntity;
		} else
			throw new UsernameNotFoundException(email + " user not exits !!!");

	}

	/*
	 * Creating a new user for the very first time.
	 * 
	 */
	@Override
	public UserEntity createUser(CreateUserRequestModel createUserRequestModel) {

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = mapper.map(createUserRequestModel, UserEntity.class);
		userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setEncryptedPassword(encoder.encode(createUserRequestModel.getPassword()));

		return userRepository.saveAndFlush(userEntity);

	}

	/*
	 * Get UserEntity on behalf of userId.
	 * 
	 */
	@Override
	public UserEntity getUserByUserId(String userId) {

		Optional<UserEntity> optional = userRepository.findByUserId(userId);
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			return userEntity;
		} else
			throw new UsernameNotFoundException(userId + " user not exits !!!");
	}

	/*
	 * Microservices communication using Rest Template
	 * Also Useful in Test 
	 */

	public UserResponseModel getAllAlbumsByUserIdRestTemplate(String userId) {

		UserEntity userEntity = this.getUserByUserId(userId);

		ModelMapper mapper = new ModelMapper();
		UserResponseModel userResponseModel = mapper.map(userEntity, UserResponseModel.class);

		// String albumUrl = String.format("http://albums-ws/users/%s/albums",
		// publicUserId);
		String albumUrl = String.format(env.getProperty("albums.url"), userId);

		ResponseEntity<List<AlbumResponseModel>> albumResponse = restTemplate.exchange(albumUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AlbumResponseModel>>() {
				});

		List<AlbumResponseModel> albumList = albumResponse.getBody();
		userResponseModel.setAlbums(albumList);

		return userResponseModel;
	}

	/*
	 * Basic Error handling with Feign using try & catch block
	 * 
	 */
	public UserResponseModel getAllAlbumsByUserIdFeignWithExceptionHandling(String userId) {

		UserEntity userEntity = this.getUserByUserId(userId);

		ModelMapper mapper = new ModelMapper();
		UserResponseModel userResponseModel = mapper.map(userEntity, UserResponseModel.class);
		
		List<AlbumResponseModel> albumList = null;
		try {
			albumList = albumServiceClient.getAlbums(userId);
		} catch (FeignClientException e) {
			logger.error(e.getLocalizedMessage());
		}
		
		logger.info("" + albumList);
		
		userResponseModel.setAlbums(albumList);
		return userResponseModel;
		
	}
	
	/*
	 * Microservices communication using Feign with advanced Exception handling using FeignErrorDecoder
	 * 
	 */
	public UserResponseModel getAllAlbumsByUserIdFeign(String userId) {

		UserEntity userEntity = this.getUserByUserId(userId);

		ModelMapper mapper = new ModelMapper();
		UserResponseModel userResponseModel = mapper.map(userEntity, UserResponseModel.class);
		
		List<AlbumResponseModel> albumList = albumServiceClient.getAlbums(userId);
		userResponseModel.setAlbums(albumList);
		
		return userResponseModel;
		
	}


}
