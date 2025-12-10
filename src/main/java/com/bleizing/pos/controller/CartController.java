package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bleizing.pos.annotation.AccessControl;
import com.bleizing.pos.annotation.Authenticated;
import com.bleizing.pos.constant.VariableConstant;
import com.bleizing.pos.dto.AddToCartRequest;
import com.bleizing.pos.dto.AddToCartResponse;
import com.bleizing.pos.service.CartService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Cart", description = "Cart Controller")
@RestController
@RequestMapping("/api/v1/cart")
@SecurityRequirement(name = "Authorization")
public class CartController {
	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	@Authenticated
	@AccessControl
	public AddToCartResponse addToCart(@Valid @RequestBody AddToCartRequest request, HttpServletRequest servletRequest) {
		return cartService.addToCart(request, (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
}
