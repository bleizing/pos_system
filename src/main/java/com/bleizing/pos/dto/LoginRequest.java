package com.bleizing.pos.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5893080609203621147L;

	@NotBlank
	@Schema(example = "superadmin@tes.com")
	@Email
	private String email;
	
	@NotBlank
	@Schema(example = "superadmin")
	private String password;
}
