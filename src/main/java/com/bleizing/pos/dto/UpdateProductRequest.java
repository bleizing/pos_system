package com.bleizing.pos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5793168332819816435L;
	
	@NotBlank
	@Schema(example = "T2")
	private String code;

	@Schema(example = "Toko B1")
	private String name;
	
	@Nullable
	@DecimalMin(value = "0.0")
	@Schema(example = "100000")
	private BigDecimal price;
}
