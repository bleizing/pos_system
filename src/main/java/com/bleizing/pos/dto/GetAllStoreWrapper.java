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
public class GetAllStoreWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6089112873457814633L;
	
	private String name;
	private String code;
}
