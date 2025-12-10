package com.bleizing.pos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	
	@Column(name = "total_price", precision=9, scale=2)
    private BigDecimal totalPrice;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_at")
	private LocalDateTime paymentAt;
}
