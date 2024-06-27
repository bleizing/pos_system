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
public class CreateStoreResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2271279741981206936L;
	
	private Long id;
}
