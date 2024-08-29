package com.bleizing.pos.error;

public class TokenInvalidException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -306280287233351188L;

	public TokenInvalidException(String message) {
		super(message);
	}
}
