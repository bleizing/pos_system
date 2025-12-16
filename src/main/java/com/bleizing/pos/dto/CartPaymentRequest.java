package com.bleizing.pos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bleizing.pos.annotation.EnumValidator;
import com.bleizing.pos.enumeration.BankingCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartPaymentRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8351351652744010226L;
	
	@Schema(example = "Jakarta")
	private String address;
	
	@Schema(example = "08123456789")
	private String phone;
	
	@EnumValidator(enumClass = BankingCategory.class)
	@Schema(example = "QRIS")
	private String paymentMethod;
	
	@DecimalMin(value = "0.1")
	@Schema(example = "250000")
	private BigDecimal totalPrice;
}
