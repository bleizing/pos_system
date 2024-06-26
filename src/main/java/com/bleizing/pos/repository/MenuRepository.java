package com.bleizing.pos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	Optional<List<Menu>> findByPathAndActiveTrue(String path);
}
