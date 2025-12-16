package com.bleizing.pos.error;

public class CartNotExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5767746541593626699L;

	public CartNotExistException(String message) {
		super(message);
	}
}
