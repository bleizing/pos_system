package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bleizing.pos.annotation.AccessControl;
import com.bleizing.pos.annotation.Authenticated;
import com.bleizing.pos.service.SystemService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "System", description = "System Controller")
@RestController
@RequestMapping("/sys")
@SecurityRequirement(name = "Authorization")
public class SystemController {
	@Autowired
	private SystemService systemService;
	
	@GetMapping("/initData")
	@Authenticated
	@AccessControl
	public String initData() {
		return systemService.initData();
	}
}