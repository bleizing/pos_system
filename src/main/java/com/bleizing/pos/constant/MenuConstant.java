package com.bleizing.pos.constant;

import lombok.Getter;

@Getter
public enum MenuConstant {
	SYSTEM("System", "SYSTEM", "/sys"),
	STORE("Store", "STORE", "/store"),
	PRODUCT("Product", "PRODUCT", "/product"),
	STORAGE("Storage", "STORAGE", "/storage"),
	;

	

	private final String name;
	private final String code;
	private final String path;
	
	MenuConstant(String name, String code, String path) {
		this.name = name;
		this.code = code;
		this.path = path;
	}
}
