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
public class PaymentUpdateResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4177929694345593085L;
	
	private boolean success;
}
