package com.bleizing.pos.error;

public class OutOfStockException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7064196011941290420L;

	public OutOfStockException(String message) {
		super(message);
	}
}
