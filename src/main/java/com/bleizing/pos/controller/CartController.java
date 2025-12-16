package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bleizing.pos.annotation.AccessControl;
import com.bleizing.pos.annotation.Authenticated;
import com.bleizing.pos.constant.VariableConstant;
import com.bleizing.pos.dto.AddToCartRequest;
import com.bleizing.pos.dto.AddToCartResponse;
import com.bleizing.pos.dto.CartPaymentRequest;
import com.bleizing.pos.dto.CartPaymentResponse;
import com.bleizing.pos.dto.GetCartResponse;
import com.bleizing.pos.dto.PaymentUpdateRequest;
import com.bleizing.pos.dto.PaymentUpdateResponse;
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
	public AddToCartResponse add(@Valid @RequestBody AddToCartRequest request, HttpServletRequest servletRequest) {
		return cartService.add(request, (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
	
	@GetMapping("/get")
	@Authenticated
	@AccessControl
	public GetCartResponse get(HttpServletRequest servletRequest) {
		return cartService.get((Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
	
	@PostMapping("/payment")
	@Authenticated
	@AccessControl
	public CartPaymentResponse payment(@Valid @RequestBody CartPaymentRequest request, HttpServletRequest servletRequest) {
		return cartService.payment(request, (Long) servletRequest.getAttribute(VariableConstant.USER_ID.getValue()));
	}
	
	@PostMapping("/payment-update")
	@Authenticated
	@AccessControl
	public PaymentUpdateResponse paymentUpdate(@Valid @RequestBody PaymentUpdateRequest request, HttpServletRequest servletRequest) {
		return cartService.paymentUpdate(request);
	}
}
