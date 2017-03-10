package com.mercury.s2.service.product;

import com.mercury.s2.domain.Product;
import com.mercury.s2.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    product.setProductCategory(input.getProductCategory());
    product.setProductGroup(input.getProductGroup());
    product.setProductName(input.getProductName());
    product.setProductPrice(input.getProductPrice());
    product.setProductOrigin(input.getProductOrigin());

    return productRepository.save(product);
  }

  @Override
  public Product update(Long productId, Product input) {
    Product product = this.productRepository.findOne(productId);
    product.setProductCategory(input.getProductCategory());
    product.setProductGroup(input.getProductGroup());
    product.setProductName(input.getProductName());
    product.setProductPrice(input.getProductPrice());
    product.setProductOrigin(input.getProductOrigin());

    return this.productRepository.saveAndFlush(product);
  }
}
