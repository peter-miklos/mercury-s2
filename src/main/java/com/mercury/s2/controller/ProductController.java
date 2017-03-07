package com.mercury.s2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercury.s2.domain.Product;

@RestController
public class ProductController {

  Product product = new Product();

  @GetMapping(value = "/product/{product_id}")
  public Product getProduct(@PathVariable Long product_id){

    product.setId(product_id);
    product.setCategory("category1");
    product.setGroup("group1");
    product.setName("Test product");
    product.setPrice(11.97);
    product.setOrigin("UK");

    return product;
  }

}
