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
import com.bleizing.pos.error.DataNotFoundException;
import com.bleizing.pos.error.EmailPasswordInvalid;
import com.bleizing.pos.error.ErrorList;
import com.bleizing.pos.error.ForbiddenAccessException;
import com.bleizing.pos.error.PathInvalidException;
import com.bleizing.pos.error.TokenInvalidException;
import com.bleizing.pos.error.TokenRequiredException;
import com.bleizing.pos.error.UserStoreUnmatchException;

@Order(Ordered.LOWEST_PRECEDENCE)
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
		ex.printStackTrace();
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.code(ErrorList.SYS_EXCEPTION.getCode())
				.message(ErrorList.SYS_EXCEPTION.getDescription())
				.debugMessage(ex.getMessage())
				.build());
    }
	
	@ExceptionHandler(TokenRequiredException.class)
	public ResponseEntity<Object> tokenRequiredException(TokenRequiredException ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.BAD_REQUEST)
				.code(ErrorList.TOKEN_REQUIRED.getCode())
				.message(ErrorList.TOKEN_REQUIRED.getDescription())
				.debugMessage(ex.getMessage())
				.build());
    }
	
	@ExceptionHandler(TokenInvalidException.class)
	public ResponseEntity<Object> tokenInvalidException(TokenInvalidException ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.BAD_REQUEST)
				.code(ErrorList.TOKEN_INVALID.getCode())
				.message(ErrorList.TOKEN_INVALID.getDescription())
				.debugMessage(ex.getMessage())
				.build());
    }
	
	@ExceptionHandler(PathInvalidException.class)
	public ResponseEntity<Object> pathInvalidException(PathInvalidException ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.BAD_REQUEST)
				.code(ErrorList.PATH_INVALID.getCode())
				.message(ErrorList.PATH_INVALID.getDescription())
				.debugMessage(ex.getMessage())
				.build());
    }
	
	@ExceptionHandler(ForbiddenAccessException.class)
	public ResponseEntity<Object> forbiddenAccessException(ForbiddenAccessException ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.FORBIDDEN)
				.code(ErrorList.FORBIDDEN_ACCESS.getCode())
				.message(ErrorList.FORBIDDEN_ACCESS.getDescription())
				.debugMessage(ex.getMessage())
				.build());
    }
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Object> dataNotFoundException(DataNotFoundException ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.code(ErrorList.DATA_NOT_FOUND.getCode())
				.message(ErrorList.DATA_NOT_FOUND.getDescription())
				.debugMessage(ex.getMessage())
				.build());
    }
	
	@ExceptionHandler(EmailPasswordInvalid.class)
	public ResponseEntity<Object> emailPasswordInvalid(EmailPasswordInvalid ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.code(ErrorList.EMAIL_PASSWORD_INVALID.getCode())
				.message(ErrorList.EMAIL_PASSWORD_INVALID.getDescription())
				.debugMessage(ex.getMessage())
				.build());
    }
	
	@ExceptionHandler(UserStoreUnmatchException.class)
	public ResponseEntity<Object> userStoreUnmatchException(UserStoreUnmatchException ex) {
		return buildResponseEntity(ApiError.builder()
				.status(HttpStatus.FORBIDDEN)
				.code(ErrorList.USER_STORE_UNMATCH.getCode())
				.message(ErrorList.USER_STORE_UNMATCH.getDescription())
				.debugMessage(ex.getMessage())
				.build());
    }
}
