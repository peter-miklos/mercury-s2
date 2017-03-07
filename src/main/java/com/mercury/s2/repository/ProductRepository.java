package com.mercury.s2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercury.s2.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
