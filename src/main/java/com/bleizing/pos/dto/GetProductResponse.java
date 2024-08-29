package com.bleizing.pos.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1349227553713772387L;
	
	private List<GetProductWrapper> products;
}
