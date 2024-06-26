package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bleizing.pos.annotation.AccessControl;
import com.bleizing.pos.annotation.Authenticated;
import com.bleizing.pos.dto.GetStoreByCodeResponse;
import com.bleizing.pos.service.StoreService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Store", description = "Store Controller")
@RestController
@Slf4j
@RequestMapping("/store")
@SecurityRequirement(name = "Authorization")
public class StoreController {
	@Autowired
	private StoreService storeService;
	
	@GetMapping("/getByCode")
	@Authenticated
	@AccessControl
	public ResponseEntity<GetStoreByCodeResponse> getStoreByCode(@RequestParam String code, HttpServletRequest servletRequest) {
		try {
			return new ResponseEntity<>(storeService.getStoreByCode(code), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
