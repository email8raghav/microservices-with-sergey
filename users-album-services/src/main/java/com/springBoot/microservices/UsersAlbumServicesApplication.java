package com.springBoot.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UsersAlbumServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersAlbumServicesApplication.class, args);
	}

}
