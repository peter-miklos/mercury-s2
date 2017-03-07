package com.mercury.s2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with a non empty string as 'name'");
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
    product = new Product();
    product.setId(Long.valueOf(11));
    product.setCategory("category11");
    product.setGroup("group11");
    product.setName("Test product 11");
    product.setPrice(11.97);
    product.setOrigin("UK");

    products.add(product);

    product = new Product();
    product.setId(Long.valueOf(12));
    product.setCategory("category12");
    product.setGroup("group12");
    product.setName("Test product 12");
    product.setPrice(4.97);
    product.setOrigin("USA");

    products.add(product);

    return products;

  }

}
