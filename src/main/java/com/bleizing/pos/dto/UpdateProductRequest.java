package com.bleizing.pos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
public class UpdateProductRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5793168332819816435L;
	
	@NotBlank
	@Schema(example = "T2")
	private String code;

	@Nullable
	@Schema(example = "Test2")
	private String name;
	
	@Nullable
	@DecimalMin(value = "0.1")
	@Schema(example = "100000")
	private BigDecimal price;
	
	@Nullable
	@Schema(example = "product/EF52FA4C0E9BDD90104E35482CDEC896C1872CA59613B681B03AA87C03B74CD3")
	private String image;
	
	@NotNull
	@Min(value = 0)
	@Schema(example = "5")
	private int stock;
}
