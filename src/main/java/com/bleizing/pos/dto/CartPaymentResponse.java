package com.bleizing.pos.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartPaymentResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8781753030006029393L;
	
	private String invoiceNumber;
}
