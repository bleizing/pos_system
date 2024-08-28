package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bleizing.pos.annotation.AccessControl;
import com.bleizing.pos.annotation.Authenticated;
import com.bleizing.pos.constant.VariableConstant;
import com.bleizing.pos.dto.CreateStoreRequest;
import com.bleizing.pos.dto.CreateStoreResponse;
import com.bleizing.pos.dto.DeleteStoreRequest;
import com.bleizing.pos.dto.DeleteStoreResponse;
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
		return storeService.getStoreByUserLoggedIn((Long) servletRequest.getAttribute(VariableConstant.STORE_ID.getValue()), code);
	}
	
	@PostMapping("/create")
	@Authenticated
	@AccessControl
	public CreateStoreResponse create(@Valid @RequestBody CreateStoreRequest request, HttpServletRequest servletRequest) {
		return storeService.create(request, (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
	
	@GetMapping("/getAll")
	@Authenticated
	@AccessControl
	public GetAllStoreResponse getAll() {
		return storeService.getAll();
	}
	
	@PutMapping("/update")
	@Authenticated
	@AccessControl
	public UpdateStoreResponse update(@Valid @RequestBody UpdateStoreRequest request,  @Nullable @RequestParam(value = "code", required = false) String code, HttpServletRequest servletRequest) throws Exception {
		return storeService.update(request, code, (Long) servletRequest.getAttribute(VariableConstant.STORE_ID.getValue()), (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
	
	@DeleteMapping("/delete")
	@Authenticated
	@AccessControl
	public DeleteStoreResponse delete(@Valid @RequestBody DeleteStoreRequest request, HttpServletRequest servletRequest) {
		return storeService.delete(request, (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
}
