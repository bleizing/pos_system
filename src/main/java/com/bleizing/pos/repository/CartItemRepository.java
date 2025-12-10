package com.bleizing.pos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.CartItem;
import com.google.common.base.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	public Optional<List<CartItem>> findByCartId(Long cartId);
}
