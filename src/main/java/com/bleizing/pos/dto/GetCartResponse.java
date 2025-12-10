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
public class GetCartResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3816847336016868072L;
	
	private List<GetCartWrapper> carts;
}
