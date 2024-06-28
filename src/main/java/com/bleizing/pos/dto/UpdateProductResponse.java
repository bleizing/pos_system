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
public class UpdateProductResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2443492287744228371L;
	
	private boolean success;
}
