package com.bleizing.pos.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5893080609203621147L;

	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
}
