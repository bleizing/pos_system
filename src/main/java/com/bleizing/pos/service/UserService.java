package com.bleizing.pos.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.dto.LoginRequest;
import com.bleizing.pos.dto.LoginResponse;
import com.bleizing.pos.error.DataNotFoundException;
import com.bleizing.pos.error.EmailPasswordInvalid;
import com.bleizing.pos.error.ErrorList;
import com.bleizing.pos.model.User;
import com.bleizing.pos.model.UserStore;
import com.bleizing.pos.repository.UserRepository;
import com.bleizing.pos.repository.UserRoleRepository;
import com.bleizing.pos.repository.UserStoreRepository;
import com.bleizing.pos.util.PasswordUtil;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserStoreRepository userStoreRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Logged
	public LoginResponse login(LoginRequest request) throws Exception {
		User user = userRepository.findByEmailAndActiveTrue(request.getEmail()).orElseThrow(() -> new EmailPasswordInvalid(ErrorList.EMAIL_PASSWORD_INVALID.getDescription()));
		if (!PasswordUtil.validatePassword(request.getPassword(), user.getPassword())) {
			throw new EmailPasswordInvalid(ErrorList.EMAIL_PASSWORD_INVALID.getDescription());
		}
		
		Long storeId = 0L;
		if (!userRoleRepository.findByUserIdAndActiveTrue(user.getId()).orElseThrow(() -> new DataNotFoundException("User Role Not Found")).getRole().getName().equals("SUPERADMIN")) {
			UserStore userStore = userStoreRepository.findByUserIdAndActiveTrue(user.getId()).orElseThrow(() -> new DataNotFoundException("User Store Not Found"));
			storeId = userStore.getStore().getId();
		}
		
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("id", Long.valueOf(user.getId()));
		claims.put("storeId", Long.valueOf(storeId));
		
		String token = jwtService.generateToken(claims, user.getEmail());
		
		return LoginResponse.builder()
				.accessToken(token)
				.expiredIn(jwtService.getExpirationTime())
				.build();
	}
}
