package com.mercury.s2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/api/v1")
public class ProductController {

  private final ProductRepository productRepository;

  ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  void handleBadRequests(HttpServletResponse response) throws IOException {
      response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with a valid product ID");
  }

  @RequestMapping(method = RequestMethod.GET, value = "/product/{product_id}")
  public Product getProduct(@PathVariable Long product_id) {
    if (product_id != null) {
      return this.productRepository.findOne(product_id);
    } else {
      throw new IllegalArgumentException();
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/products")
  public Collection<Product> getProducts() {
    return this.productRepository.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/product")
  public Product productSubmit(@RequestBody Product input) {
    Product product = new Product();
    product.setProductCategory(input.getProductCategory());
    product.setProductGroup(input.getProductGroup());
    product.setProductName(input.getProductName());
    product.setProductPrice(input.getProductPrice());
    product.setProductOrigin(input.getProductOrigin());

    return productRepository.save(product);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/product/{product_id}")
  public Product updateProduct(@PathVariable Long product_id, @RequestBody Product input) {
    if (product_id != null) {
      Product product = this.productRepository.findOne(product_id);
      product.setProductCategory(input.getProductCategory());
      product.setProductGroup(input.getProductGroup());
      product.setProductName(input.getProductName());
      product.setProductPrice(input.getProductPrice());
      product.setProductOrigin(input.getProductOrigin());

      Product result = this.productRepository.saveAndFlush(product);
      return result;
    } else {
      throw new IllegalArgumentException();
    }
  }

}
