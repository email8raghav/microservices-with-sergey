package com.springBoot.users.initial.security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoot.users.initial.entity.UserEntity;
import com.springBoot.users.initial.model.LoginRequestModel;
import com.springBoot.users.initial.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private Environment env;

	private UserService userService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		this.env = env;
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

			/*
			 * authenticate method internally call loadUserByUsername(String email) then i
			 * return UserEntity as it implements UserDetails interface i return Our
			 * UserEntity object rather than Spring Core User as it also implement
			 * UserDetails
			 * 
			 */
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		log.info("yes successfull authentication is done!!!");
		/*
		 * As we return UserEntity from loadUserByUsername(String email)
		 * so we can cast our following object directly to UserEntity
		 * class type. 
		 */
	
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		log.info(userEntity.getUserId());

		log.info(env.getProperty("token.secret"));
		
		
		String token = Jwts.builder().setSubject(userEntity.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();

		log.info(token);
		
		String prefix = env.getProperty("authorization.token.header.prefix");
		res.addHeader("token", prefix+token);
		res.addHeader("userId", userEntity.getUserId());

	}

}
