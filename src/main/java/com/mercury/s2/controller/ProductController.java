package com.mercury.s2.controller;

import org.json.simple.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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

  @RequestMapping(method = RequestMethod.DELETE, value = "/product/{productId}")
  public JSONObject deleteProduct(@PathVariable Long productId) {
    Product product = productService.getProductById(productId)
            .orElseThrow(() -> new NoSuchElementException(String.format("Product(id: %s) not found", productId)));
    return productService.delete(productId);
  }

}
