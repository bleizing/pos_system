package com.bleizing.pos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.enumeration.LovCategory;
import com.bleizing.pos.model.Lov;
import com.google.common.base.Optional;


@Repository
public interface LovRepository extends JpaRepository<Lov, Long> {
	Optional<List<Lov>> findByCategoryAndActiveTrue(LovCategory category);
}
