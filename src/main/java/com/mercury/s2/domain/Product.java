package com.mercury.s2.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Product {

  @Id
  @GeneratedValue
  private Long id;

  public String category;
  public String productGroup;
  public String name;
  public Double price;
  public String origin;

  Product() {
  }

  public Product(String category, String productGroup, String name, Double price, String origin) {
    this.category = category;
    this.productGroup = productGroup;
    this.name = name;
    this.price = price;
    this.origin = origin;
  }

  public Long getId() {
    return id;
  }

  public String getCategory() {
    return category;
  }

  public String getGroup() {
    return productGroup;
  }

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
  }

  public String getOrigin() {
    return origin;
  }
}
