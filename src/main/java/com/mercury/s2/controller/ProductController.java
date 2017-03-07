package com.mercury.s2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import com.mercury.s2.domain.Product;

@RestController
public class ProductController {

  Product product;
  ArrayList<Product> products = new ArrayList<Product>();

  @ExceptionHandler(IllegalArgumentException.class)
  void handleBadRequests(HttpServletResponse response) throws IOException {
      response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with a valid product ID");
  }

  @GetMapping(value = "/product/{product_id}")
  public Product getProduct(@PathVariable Long product_id) {
    product = new Product();

    for(Product productElem : products) {
      if(productElem.getId() == product_id) {
        product = productElem;
      }
    }

    if (product_id == null || product.getId() == null) {
      throw new IllegalArgumentException();
    }

    return product;
  }

  @GetMapping(value = "/products")
  public ArrayList<Product> getProducts() {
    return products;
  }

  @PostMapping(value = "/product")
  public Product productSubmit(@RequestBody Product input) {
    products.add(input);
    return input;
  }

}
