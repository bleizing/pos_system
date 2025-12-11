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
public class GetCartWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8994967096456855898L;
	
	private String productCode;
	private String productName;
	private int quantity;
}
