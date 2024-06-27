package com.bleizing.pos.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1119226738511373466L;
	
	private String accessToken;
	private Long expiredIn;
	private String storeCode;
}
