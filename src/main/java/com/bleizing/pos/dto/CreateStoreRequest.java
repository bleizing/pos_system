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
public class CreateStoreRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8862861707765991410L;
	
	@NotBlank
	@Schema(example = "Toko C")
	private String name;
	
	@NotBlank
	@Schema(example = "S3")
	private String code;
}
