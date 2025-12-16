package com.bleizing.pos.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUpdateRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1373226270398317409L;

	@Nonnull
	@Schema(example = "123")
	private String invoiceNumber;
	
	@Nonnull
	@Schema(example = "success")
	private String status;
}
