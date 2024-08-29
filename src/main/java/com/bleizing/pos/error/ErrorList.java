package com.bleizing.pos.error;

public enum ErrorList {
	TOKEN_INVALID("1000", "Token Invalid"),
	TOKEN_REQUIRED("1001", "Token Must Be Filled"),
	PATH_INVALID("1100", "Path Invalid"),
	PERMISSION_INVALID("1101", "Permission Invalid"),
	ROLE_INVALID("1102", "Role Invalid"),
	SYS_PARAM_INVALID("1103", "Sys Param Invalid"),
	FORBIDDEN_ACCESS("1201", "Forbidden Access"),
	USER_STORE_UNMATCH("2001", "User Unmatch with Store"),
	DATA_NOT_FOUND("3001", "Data Not Found"),
	DATA_EXISTS("3002", "Data Already Exists"),
	EMAIL_PASSWORD_INVALID("5000", "Email or Password Wrong"),
	SYS_EXCEPTION("9999", "System Exception");
	
	private final String code;
	private final String description;
	
	private ErrorList(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
