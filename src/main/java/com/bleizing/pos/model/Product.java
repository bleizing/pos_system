package com.bleizing.pos.model;

import java.math.BigDecimal;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 65982128621861651L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Nonnull
	@Column
	private String name;
	
	@Nonnull
	@Column(unique = true)
	private String code;
	
	@Column(precision=9, scale=2)
    private BigDecimal price;
	
	@ManyToOne
    @JoinColumn(name="store_id", nullable=false)
    private Store store;
}
