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
public class UpdateStoreRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4666256968119360521L;
	
	@NotBlank
	@Schema(example = "Toko B1")
	private String name;
}
