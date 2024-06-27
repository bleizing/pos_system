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
public class GetStoreByUserLoggedInResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7328368655937723502L;
	
	private String name;
	private String code;
}
