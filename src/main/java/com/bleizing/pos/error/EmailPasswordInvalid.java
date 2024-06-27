package com.bleizing.pos.error;

public class EmailPasswordInvalid extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3897296731501036521L;

	public EmailPasswordInvalid(String message) {
		super(message);
	}
}
