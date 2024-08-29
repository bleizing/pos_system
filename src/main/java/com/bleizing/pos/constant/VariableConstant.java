package com.bleizing.pos.constant;

import lombok.Getter;

@Getter
public enum VariableConstant {
	USER_ID("userId"),
	STORE_ID("storeId"),
	SYS_PARAM("sysParam"),
	MENU_ID("menuId"),
	PERMISSION_ID("permissionId"),
	USER_ROLE("userRole"),
	MENU_ROLE_PERMISSION("menuRolePermission"),
	;

	private final String value;
	
	VariableConstant(String value) {
		this.value = value;
	}
}
