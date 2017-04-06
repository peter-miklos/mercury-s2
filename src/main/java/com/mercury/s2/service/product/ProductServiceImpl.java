package com.mercury.s2.service.product;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mercury.s2.domain.Product;
import com.mercury.s2.repository.ProductRepository;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.Optional;
import java.util.Collection;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product create(Product input) {
    Product product = new Product();
    return productRepository.save(addProudctFeatures(product, input));
  }

  @Override
  public Product update(Product product, Product input) {
    return productRepository.saveAndFlush(addProudctFeatures(product, input));
  }

  @Override
  public Optional<Product> getProductById(Long id) {
    return Optional.ofNullable(productRepository.findOne(id));
  }

  @Override
  public Collection<Product> getAllProducts() {
    return productRepository.findAll(new Sort("productName"));
  }

  @Override
  public JSONObject delete(Long id) {
    productRepository.delete(id);
    return getMessageInJson(id);
  }

  private Product addProudctFeatures(Product product, Product input) {
    product.setProductCategory(input.getProductCategory());
    product.setProductGroup(input.getProductGroup());
    product.setProductName(input.getProductName());
    product.setProductPrice(input.getProductPrice());
    product.setProductOrigin(input.getProductOrigin());

    return product;
  }

  private JSONObject getMessageInJson(Long id) {
    JSONObject json = new JSONObject();
    String message = "Product (id: " + id + ") has been successfully deleted";
    json.put("message", message);
    return json;
  }
}
