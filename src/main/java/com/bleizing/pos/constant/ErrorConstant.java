package com.bleizing.pos.constant;

import lombok.Getter;

@Getter
public enum ErrorConstant {
	BEARER_NULL("Bearer token is null"),
	TOKEN_INVALID("Token is invalid"),
	CODE_EMPTY("Code must be filled"),
	INVALID_NAME("Invalid file name"),
	
	CREDENTIAL_INCORRECT("Email or password incorrect"),
	UPLOAD_FAILED("uploadFile Error: "),
	DELETE_FAILED("deleteFile Error: "),
	
	PRODUCT_EXISTS("Product code already exists"),
	STORE_EXISTS("Store code already exists"),
	
	STORE_NOT_FOUND("Store not found"),
	PRODUCT_NOT_FOUND("Product not found"),
	STORES_NOT_FOUND("Stores not found"),
	PRODUCTS_NOT_FOUND("Products not found"),
	USER_ROLE_NOT_FOUND("User Role not found"),
	USER_STORE_NOT_FOUND("User Store not found"),
	;
	
	private final String description;
	
	ErrorConstant(String description) {
		this.description = description;
	}
}
