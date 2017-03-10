package com.mercury.s2.service.product;

import com.mercury.s2.domain.Product;
import com.mercury.s2.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.Optional;
import java.util.Collection;

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
  public Product update(Product product, Product input) {
    product.setProductCategory(input.getProductCategory());
    product.setProductGroup(input.getProductGroup());
    product.setProductName(input.getProductName());
    product.setProductPrice(input.getProductPrice());
    product.setProductOrigin(input.getProductOrigin());

    return productRepository.saveAndFlush(product);
  }

  @Override
  public Optional<Product> getProductById(Long id) {
    return Optional.ofNullable(productRepository.findOne(id));
  }

  @Override
  public Collection<Product> getAllProducts() {
    return productRepository.findAll(new Sort("productName"));
  }
}
