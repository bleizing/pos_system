package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bleizing.pos.annotation.AccessControl;
import com.bleizing.pos.annotation.Authenticated;
import com.bleizing.pos.dto.CreateStoreRequest;
import com.bleizing.pos.dto.CreateStoreResponse;
import com.bleizing.pos.dto.GetAllStoreResponse;
import com.bleizing.pos.dto.GetStoreByUserLoggedInResponse;
import com.bleizing.pos.dto.UpdateStoreRequest;
import com.bleizing.pos.dto.UpdateStoreResponse;
import com.bleizing.pos.service.StoreService;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Store", description = "Store Controller")
@RestController
@RequestMapping("/store")
@SecurityRequirement(name = "Authorization")
public class StoreController {
	@Autowired
	private StoreService storeService;
	
	@GetMapping("/get")
	@Authenticated
	@AccessControl
//	@Parameters({
//		@Parameter(in = ParameterIn.QUERY, name = "code", schema = @Schema(type = "string", defaultValue = "S2"), required = false)
//	})
	public GetStoreByUserLoggedInResponse getStoreByUserLoggedIn(
//			@Parameter(
//            name =  "code",
//            description  = "Store code",
//            example = "S2",
//            required = false)
            @Nullable @RequestParam(value = "code", required = false) String code, HttpServletRequest servletRequest) throws Exception {
		return storeService.getStoreByUserLoggedIn((Long) servletRequest.getAttribute("storeId"), code);
	}
	
	@PostMapping("/create")
	@Authenticated
	@AccessControl
	public CreateStoreResponse createStore(@Valid @RequestBody CreateStoreRequest request, HttpServletRequest servletRequest) {
		return storeService.createStore(request, (Long) servletRequest.getAttribute("userId"));
	}
	
	@GetMapping("/getAll")
	@Authenticated
	@AccessControl
	public GetAllStoreResponse getAllStore() {
		return storeService.getAllStore();
	}
	
	@PutMapping("/update")
	@Authenticated
	@AccessControl
	public UpdateStoreResponse updateStore(@Valid @RequestBody UpdateStoreRequest request,  @Nullable @RequestParam(value = "code", required = false) String code, HttpServletRequest servletRequest) throws Exception {
		return storeService.updateStore(request, code, (Long) servletRequest.getAttribute("storeId"));
	}
}
