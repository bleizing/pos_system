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
public class GetAllProductWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8554188935760581253L;
	
	private String name;
	private String store;
	private BigDecimal price;
	private String image;
}
