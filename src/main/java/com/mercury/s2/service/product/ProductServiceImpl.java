package com.mercury.s2.service.product;

import com.mercury.s2.domain.Product;
import com.mercury.s2.repository.ProductRepository;

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
    List<Product> productList = productRepository.findAll(new Sort("productName"));
    return productRepository.findAll(new Sort("productName"));
  }

  @Override
  public String delete(Long id) {
    productRepository.delete(id);
    return "Product (id: " + id + ") successfully deleted";
  }

  private Product addProudctFeatures(Product product, Product input) {
    product.setProductCategory(input.getProductCategory());
    product.setProductGroup(input.getProductGroup());
    product.setProductName(input.getProductName());
    product.setProductPrice(input.getProductPrice());
    product.setProductOrigin(input.getProductOrigin());

    return product;
  }
}
