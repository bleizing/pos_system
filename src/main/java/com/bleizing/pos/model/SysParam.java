package com.bleizing.pos.model;

import java.io.Serializable;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sys_params")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysParam extends BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 69352108397503307L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Nonnull
	@Column
	private String name;
	
	@Nonnull
    @Column(unique = true)
	private String code;
	
	@Nonnull
	@Column
	private String value;
	
	@Nonnull
	@Column
	private String description;
}
