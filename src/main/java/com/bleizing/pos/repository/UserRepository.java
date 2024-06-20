package com.bleizing.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bleizing.pos.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
