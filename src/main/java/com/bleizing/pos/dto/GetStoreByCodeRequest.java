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
public class GetStoreByCodeRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8789573953551512204L;
	
	@NotBlank
	@Schema(example = "S1")
	private String code;
}
