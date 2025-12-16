package com.bleizing.pos.model;

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
@Table(name = "carts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 359824579860759607L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	@Builder.Default
	@Column(name = "is_payment", nullable = false)
	private boolean payment = false;

	@Builder.Default
	@Column(name = "is_complete", nullable = false)
	private boolean complete = false;
	
	@Column
	private String address;
	
	@Column
	private String phone;
}
