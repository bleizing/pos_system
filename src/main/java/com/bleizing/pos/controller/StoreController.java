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

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
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
	@Parameters({
		@Parameter(in = ParameterIn.HEADER, name = "store-id", schema = @Schema(type = "string", defaultValue = "2", required = false))
	})
	public ResponseEntity<GetStoreByCodeResponse> getStoreByCode(@Parameter(
//            name =  "code",
//            description  = "Store code",
            example = "S2",
            required = true)
            @RequestParam String code, HttpServletRequest servletRequest) {
		return new ResponseEntity<>(storeService.getStoreByIdAndCode((Long) servletRequest.getAttribute("storeId"), code), HttpStatus.OK);
	}
}
