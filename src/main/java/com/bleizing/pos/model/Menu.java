package com.bleizing.pos.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menus")
@Getter
@Setter
@Builder
public class Menu extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7624384562768692328L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Nonnull
	private String name;
	
	@Nonnull
	@Column(unique = true)
	private String code;
	
	@Nonnull
	private String path;
}
