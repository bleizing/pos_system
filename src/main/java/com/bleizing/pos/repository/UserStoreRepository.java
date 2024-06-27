package com.bleizing.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.UserStore;

@Repository
public interface UserStoreRepository extends JpaRepository<UserStore, Long> {
	Optional<UserStore> findByUserIdAndActiveTrue(Long userId);
	Optional<UserStore> findByUserIdAndStoreIdAndActiveTrue(Long userId, Long storeId);
}