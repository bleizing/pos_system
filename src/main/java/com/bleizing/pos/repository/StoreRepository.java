package com.bleizing.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	Optional<Store> findByCode(String code);
}
