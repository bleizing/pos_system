package com.bleizing.pos.error;

public class DataExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9140541738307954912L;

	public DataExistsException(String message) {
		super(message);
	}
}
