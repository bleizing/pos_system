package com.bleizing.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
