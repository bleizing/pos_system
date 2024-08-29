package com.bleizing.pos.error;

public class DataNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6005788597601055807L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
