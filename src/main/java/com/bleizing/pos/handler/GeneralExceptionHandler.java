package com.bleizing.pos.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bleizing.pos.error.ApiError;
import com.bleizing.pos.error.TokenRequiredException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.BAD_REQUEST)
				.message(error)
				.debugMessage(ex.getMessage())
				.build());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exception(Exception ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.code("9999")
				.message("ERROR")
				.debugMessage(ex.getMessage())
				.build());
    }
	
	@ExceptionHandler(TokenRequiredException.class)
	public ResponseEntity<Object> tokenRequiredException(TokenRequiredException ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.code("1000")
				.message("ERROR")
				.debugMessage(ex.getMessage())
				.build());
    }
}
