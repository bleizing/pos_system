package com.bleizing.pos.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProductRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4642864474185166058L;
	
	@NotBlank
	@Schema(example = "T2")
	private String code;
}
