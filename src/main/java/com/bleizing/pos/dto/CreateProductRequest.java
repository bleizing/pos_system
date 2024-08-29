package com.bleizing.pos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.micrometer.common.lang.Nullable;
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
public class CreateProductRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5450168943370796476L;

	@NotBlank
	@Schema(example = "Test3")
	private String name;
	
	@NotBlank
	@Schema(example = "T3")
	private String code;
	
	@NotNull
	@DecimalMin(value = "0.0")
	@Schema(example = "125000")
	private BigDecimal price;
	
	@NotBlank
	@Schema(example = "product/EF52FA4C0E9BDD90104E35482CDEC896C1872CA59613B681B03AA87C03B74CD3")
	private String image;

	@Nullable
	private String storeCode;
}
