package com.springBoot.users.initial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.users.initial.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	
	public Optional<UserEntity> findByEmail(String email);
	
	public Optional<UserEntity> findByUserId(String userId);
	
	

}
