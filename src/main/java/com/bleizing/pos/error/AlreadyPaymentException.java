package com.bleizing.pos.error;

public class AlreadyPaymentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3077395727384570904L;

	public AlreadyPaymentException(String message) {
		super(message);
	}	
}
