package com.bleizing.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.SysParam;

@Repository
public interface SysParamRepository extends JpaRepository<SysParam, Long> {
	Optional<SysParam> findByCodeAndActiveTrue(String code);
}
