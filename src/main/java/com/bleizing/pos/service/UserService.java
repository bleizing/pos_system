package com.bleizing.pos.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.dto.LoginRequest;
import com.bleizing.pos.dto.LoginResponse;
import com.bleizing.pos.model.User;
import com.bleizing.pos.repository.UserRepository;
import com.bleizing.pos.util.PasswordUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	
	@Logged
	public LoginResponse login(LoginRequest request) throws Exception {
		User user = userRepository.findByEmailAndActiveTrue(request.getEmail()).orElseThrow(() -> new Exception("Not found"));
		if (!PasswordUtil.validatePassword(request.getPassword(), user.getPassword())) {
			throw new Exception("Email or Password Invalid");
		}
		
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("id", Long.valueOf(user.getId()));
		
		String token = jwtService.generateToken(claims, user.getEmail());
		
		return LoginResponse.builder()
				.accessToken(token)
				.expiredIn(jwtService.getExpirationTime())
				.build();
	}
}
