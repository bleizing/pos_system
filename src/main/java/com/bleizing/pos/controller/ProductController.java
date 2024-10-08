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
import com.bleizing.pos.dto.CreateProductRequest;
import com.bleizing.pos.dto.CreateProductReseponse;
import com.bleizing.pos.dto.DeleteProductRequest;
import com.bleizing.pos.dto.DeleteProductResponse;
import com.bleizing.pos.dto.GetProductResponse;
import com.bleizing.pos.dto.UpdateProductRequest;
import com.bleizing.pos.dto.UpdateProductResponse;
import com.bleizing.pos.service.ProductService;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Product", description = "Product Controller")
@RestController
@RequestMapping("/product")
@SecurityRequirement(name = "Authorization")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/get")
	@Authenticated
	@AccessControl
	public GetProductResponse get(@Nullable @RequestParam(value = "code", required = false) String code, HttpServletRequest servletRequest) throws Exception {
		return productService.get((Long) servletRequest.getAttribute(VariableConstant.STORE_ID.getValue()), code);
	}
	
	@PostMapping("/create")
	@Authenticated
	@AccessControl
	public CreateProductReseponse create(@Valid @RequestBody CreateProductRequest request, HttpServletRequest servletRequest) throws Exception {
		return productService.create(request, (Long) servletRequest.getAttribute(VariableConstant.STORE_ID.getValue()), (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
	
	@PutMapping("/update")
	@Authenticated
	@AccessControl
	public UpdateProductResponse update(@Valid @RequestBody UpdateProductRequest request, HttpServletRequest servletRequest) {
		return productService.update(request, (Long) servletRequest.getAttribute(VariableConstant.STORE_ID.getValue()), (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
	
	@DeleteMapping("/delete")
	@Authenticated
	@AccessControl
	public DeleteProductResponse delete(@Valid @RequestBody DeleteProductRequest request, HttpServletRequest servletRequest) {
		return productService.delete(request, (Long) servletRequest.getAttribute(VariableConstant.STORE_ID.getValue()), (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
}
