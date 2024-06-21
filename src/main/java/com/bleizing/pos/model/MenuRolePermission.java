package com.bleizing.pos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menu_role_permissions")
@Getter
@Setter
@Builder
public class MenuRolePermission extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5165711213693645256L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="menu_id", nullable=false)
	private Menu menu;
	
	@ManyToOne
	@JoinColumn(name="role_id", nullable=false)
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="permission_id", nullable=false)
	private Permission permission;
}
