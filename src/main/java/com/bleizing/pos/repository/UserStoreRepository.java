package com.bleizing.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.UserStore;

@Repository
public interface UserStoreRepository extends JpaRepository<UserStore, Long> {

}
