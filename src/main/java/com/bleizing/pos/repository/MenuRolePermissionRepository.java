package com.bleizing.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.MenuRolePermission;

@Repository
public interface MenuRolePermissionRepository extends JpaRepository<MenuRolePermission, Long> {
	Optional<MenuRolePermission> findByRoleIdAndPermissionIdAndMenuIdAndActiveTrue(Long roleId, Long permissionId, Long MenuId);
}
