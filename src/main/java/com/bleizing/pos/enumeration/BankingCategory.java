package com.bleizing.pos.enumeration;

public enum BankingCategory {

	QRIS("QRIS", "QRIS"),
	BCA_VA("BCA_VA", "BCA Virtual Account");
	
	private final String code;
	private final String name;
	
	private BankingCategory(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
