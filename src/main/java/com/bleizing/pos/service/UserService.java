package com.bleizing.pos.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.constant.ErrorConstant;
import com.bleizing.pos.constant.VariableConstant;
import com.bleizing.pos.dto.LoginRequest;
import com.bleizing.pos.dto.LoginResponse;
import com.bleizing.pos.error.DataNotFoundException;
import com.bleizing.pos.error.EmailPasswordInvalid;
import com.bleizing.pos.error.ErrorList;
import com.bleizing.pos.error.ForbiddenAccessException;
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
		User user = userRepository.findByEmailAndActiveTrue(request.getEmail()).orElseThrow(() -> new EmailPasswordInvalid(ErrorConstant.CREDENTIAL_INCORRECT.getDescription()));
		if (!PasswordUtil.validatePassword(request.getPassword(), user.getPassword())) {
			throw new EmailPasswordInvalid(ErrorConstant.CREDENTIAL_INCORRECT.getDescription());
		}
		
		Long storeId = 0L;
		if (!userRoleRepository.findByUserIdAndActiveTrue(user.getId()).orElseThrow(() -> new DataNotFoundException(ErrorConstant.USER_ROLE_NOT_FOUND.getDescription())).getRole().getName().equals("SUPERADMIN")) {
			Optional<UserStore> userStoreOptional = userStoreRepository.findByUserIdAndActiveTrue(user.getId());
			if (userStoreOptional.isPresent()) {
				storeId = userStoreOptional.get().getStore().getId();
			} else {
				storeId = -1L;
			}
		}
		
		HashMap<String, Object> claims = new HashMap<>();
		claims.put(VariableConstant.USER_ID.getValue(), Long.valueOf(user.getId()));
		claims.put(VariableConstant.STORE_ID.getValue(), Long.valueOf(storeId));
		
		String token = jwtService.generateToken(claims, user.getEmail());
		
		return LoginResponse.builder()
				.accessToken(token)
				.expiredIn(jwtService.getExpirationTime())
				.build();
	}
	
	@Logged
	public User getUserLoggedIn(Long userId) {
		return userRepository.findByIdAndActiveTrue(userId).orElseThrow(() -> new ForbiddenAccessException(ErrorList.FORBIDDEN_ACCESS.getDescription()));
	}
}
