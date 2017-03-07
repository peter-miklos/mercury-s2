package com.mercury.s2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import com.mercury.s2.domain.Product;
import com.mercury.s2.repository.ProductRepository;

@RestController
public class ProductController {

  private final ProductRepository productRepository;

  ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  void handleBadRequests(HttpServletResponse response) throws IOException {
      response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with a valid product ID");
  }

  @GetMapping(value = "/product/{product_id}")
  public Product getProduct(@PathVariable Long product_id) {
    if (product_id != null) {
      return this.productRepository.findOne(product_id);
    } else {
      throw new IllegalArgumentException();
    }
  }

  @GetMapping(value = "/products")
  public Collection<Product> getProducts() {
    return this.productRepository.findAll();
  }

  @PostMapping(value = "/product")
  public Product productSubmit(@RequestBody Product input) {
    Product result = this.productRepository.save(new Product(input.category, input.productGroup, input.name, input.price, input.origin));
    return result;
  }

}
