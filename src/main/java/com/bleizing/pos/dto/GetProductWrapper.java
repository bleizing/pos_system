package com.bleizing.pos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -508469930592521768L;
	
	private String name;
	private String code;
	private BigDecimal price;
}
