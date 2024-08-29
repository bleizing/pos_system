package com.bleizing.pos.error;

public class TokenRequiredException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6160409389858542444L;

	public TokenRequiredException(String message) {
		super(message);
	}
}
