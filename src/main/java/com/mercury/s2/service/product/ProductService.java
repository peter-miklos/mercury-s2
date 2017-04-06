package com.mercury.s2.service.product;

import com.mercury.s2.domain.Product;

import java.util.Optional;
import java.util.Collection;

public interface ProductService {

  Product create(Product input);

  Product update(Product product, Product input);

  Optional<Product> getProductById(Long id);

  Collection<Product> getAllProducts();

  String delete(Long id);

}
