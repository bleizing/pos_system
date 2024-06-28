package com.bleizing.pos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<List<Product>> findByStoreIdAndActiveTrue(Long storeId);
	Optional<Product> findByCodeAndActiveTrue(String code);
}
