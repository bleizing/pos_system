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
public class GetAllProductResponse implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -4450955576495933223L;

	private List<GetAllProductWrapper> products;
}
