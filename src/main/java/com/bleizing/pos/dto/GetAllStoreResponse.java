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
public class GetAllStoreResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5858881991763187552L;
	
	private List<GetAllStoreWrapper> stores;
}
