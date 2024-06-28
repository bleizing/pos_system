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
public class DeleteProductResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439622477750556565L;
	
	private boolean success;
}
