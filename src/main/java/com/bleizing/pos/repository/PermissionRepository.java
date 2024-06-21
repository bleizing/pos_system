package com.bleizing.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
