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
public class GetProductDetailResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7656167352054209960L;
	
	private String name;
	private String code;
	private BigDecimal price;
	private String image;
	private String storeCode;
}
