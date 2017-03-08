package com.mercury.s2.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Product {

  @Id
  @GeneratedValue
  private Long id;

  public String productCategory;
  public String productGroup;
  public String productName;
  public Double productPrice;
  public String productOrigin;

  Product() {
  }

  public Product(String productCategory, String productGroup, String productName, Double productPrice, String productOrigin) {
    this.productCategory = productCategory;
    this.productGroup = productGroup;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productOrigin = productOrigin;
  }

  public Long getId() {
    return id;
  }

  public String getProductCategory() {
    return productCategory;
  }

  public String getProductGroup() {
    return productGroup;
  }

  public String getProductName() {
    return productName;
  }

  public Double getProductPrice() {
    return productPrice;
  }

  public String getProductOrigin() {
    return productOrigin;
  }
}
