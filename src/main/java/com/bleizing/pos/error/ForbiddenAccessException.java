package com.bleizing.pos.error;

public class ForbiddenAccessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3571812752408940388L;

	public ForbiddenAccessException(String message) {
		super(message);
	}
}
