package com.bleizing.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.MenuRolePermission;

@Repository
public interface MenuRolePermissionRepository extends JpaRepository<MenuRolePermission, Long> {

}
