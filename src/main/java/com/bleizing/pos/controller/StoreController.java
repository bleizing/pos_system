package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bleizing.pos.dto.GetStoreByCodeRequest;
import com.bleizing.pos.dto.GetStoreByCodeResponse;
import com.bleizing.pos.service.StoreService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Store", description = "Store Controller")
@RestController
@Slf4j
@RequestMapping("/store")
@SecurityRequirement(name = "Authorization")
public class StoreController {
	@Autowired
	private StoreService storeService;
	
	@PostMapping("/getByCode")
	public ResponseEntity<GetStoreByCodeResponse> getStoreByCode(@Valid @RequestBody GetStoreByCodeRequest request, HttpServletRequest servletRequest) {
		log.info("req = {}", request);
		log.info("servlet req = {}", servletRequest.getHeader("Authorization"));
		try {
			return new ResponseEntity<>(storeService.getStoreByCode(request), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
