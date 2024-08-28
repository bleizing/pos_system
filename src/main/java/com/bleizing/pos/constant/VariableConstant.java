package com.bleizing.pos.constant;

import lombok.Getter;

@Getter
public enum VariableConstant {
	USER_ID("userId"),
	STORE_ID("storeId"),
	;

	private final String value;
	
	VariableConstant(String value) {
		this.value = value;
	}
}
