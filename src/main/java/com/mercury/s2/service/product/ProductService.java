package com.mercury.s2.service.product;

import com.mercury.s2.domain.Product;

import java.util.Optional;
import java.util.Collection;

public interface ProductService {

  Product create(Product input);

  // Product update(Product input);
  //
  // Optional<Product> getProductById(Long id);
  //
  // Optional<Product> getUserByEmail(String email);
  //
  // Collection<Product> getAllProducts();

}