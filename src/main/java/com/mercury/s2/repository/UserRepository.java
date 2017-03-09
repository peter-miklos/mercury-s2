package com.mercury.s2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.mercury.s2.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
