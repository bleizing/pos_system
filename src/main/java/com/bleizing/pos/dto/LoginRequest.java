package com.bleizing.pos.dto;

import java.io.Serializable;

import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5893080609203621147L;

	@Nonnull
	private String email;
	
	@Nonnull
	private String password;
}
