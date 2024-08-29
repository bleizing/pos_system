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
public class UploadStorageResponse implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 5433165590709558575L;

	private String filename;
}
