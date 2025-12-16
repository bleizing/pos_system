package com.bleizing.pos.model;

import com.bleizing.pos.enumeration.LovCategory;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "lovs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lov extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2653821771673906357L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Nonnull
	@Column(unique = true)
	private String code;
	
	@Nonnull
	@Column
	private String name;

	@Nonnull
	@Enumerated(EnumType.STRING)
	@Column
	private LovCategory category;
}
