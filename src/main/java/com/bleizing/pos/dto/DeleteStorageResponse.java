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
public class DeleteStorageResponse implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 4011342002995542257L;

	private boolean success;
}
