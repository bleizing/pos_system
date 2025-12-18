package com.bleizing.pos.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePaginationResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8834675952604719164L;
	
	private int page;
	private int pageSize;
	private int totalSize;
	private int totalPage;
}
