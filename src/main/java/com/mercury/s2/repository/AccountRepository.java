package com.mercury.s2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.mercury.s2.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByUsername(String username);
}
