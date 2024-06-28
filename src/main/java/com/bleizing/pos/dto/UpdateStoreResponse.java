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
public class UpdateStoreResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5313638073839523256L;
	
	private boolean success;
}
