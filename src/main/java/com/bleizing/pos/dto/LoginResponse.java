package com.bleizing.pos.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1119226738511373466L;
	
	private Long id;
}
