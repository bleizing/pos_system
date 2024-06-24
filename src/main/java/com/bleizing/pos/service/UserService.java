package com.bleizing.pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.dto.LoginRequest;
import com.bleizing.pos.dto.LoginResponse;
import com.bleizing.pos.model.User;
import com.bleizing.pos.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public LoginResponse login(LoginRequest request) throws Exception {
		log.info("req = {}", request);
		
		User user = userRepository.findByEmailAndActiveTrue(request.getEmail()).orElseThrow(() -> new Exception("Not found"));
		return LoginResponse.builder().id(user.getId()).build();
	}
}
