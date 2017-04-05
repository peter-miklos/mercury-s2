package com.mercury.s2.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.NoSuchElementException;

import com.mercury.s2.domain.Product;
import com.mercury.s2.service.product.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  void handleBadRequests(HttpServletResponse response) throws IOException {
      response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with a valid product ID");
  }

  @RequestMapping(method = RequestMethod.GET, value = "/product/{productId}")
  public Product getProduct(@PathVariable Long productId) {
    Product product = productService.getProductById(productId)
          .orElseThrow(() -> new NoSuchElementException(String.format("Product(id: %s) not found", productId)));
    return product;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/products")
  public Collection<Product> getProducts() {
    return productService.getAllProducts();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/product")
  public Product productSubmit(@Validated @RequestBody Product input) {
    return productService.create(input);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/product/{productId}")
  public Product updateProduct(@PathVariable Long productId, @Validated @RequestBody Product input) {
    Product product = productService.getProductById(productId)
          .orElseThrow(() -> new NoSuchElementException(String.format("Product(id: %s) not found", productId)));
    return productService.update(product, input);
  }

}
