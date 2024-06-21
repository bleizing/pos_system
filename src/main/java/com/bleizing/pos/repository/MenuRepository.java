package com.bleizing.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
