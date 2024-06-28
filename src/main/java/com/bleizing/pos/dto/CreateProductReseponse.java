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
public class CreateProductReseponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2700006294269849704L;
	
	private Long id;
}
