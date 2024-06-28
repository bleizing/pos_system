package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bleizing.pos.annotation.AccessControl;
import com.bleizing.pos.annotation.Authenticated;
import com.bleizing.pos.dto.GetProductResponse;
import com.bleizing.pos.service.ProductService;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

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
		return productService.get((Long) servletRequest.getAttribute("storeId"), code);
	}
}
