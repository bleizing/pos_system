package com.bleizing.pos.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2158641359336996377L;

	@NotBlank
	@Schema(example = "T1")
	private String productCode;
	
	@NotNull
	@DecimalMin(value = "0")
	@Schema(example = "1")
	private int quantity;
}
