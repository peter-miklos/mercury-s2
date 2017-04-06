package com.mercury.s2.service.product;

import com.mercury.s2.domain.Product;
import org.json.simple.JSONObject;

import java.util.Optional;
import java.util.Collection;

public interface ProductService {

  Product create(Product input);

  Product update(Product product, Product input);

  Optional<Product> getProductById(Long id);

  Collection<Product> getAllProducts();

  JSONObject delete(Long id);

}
