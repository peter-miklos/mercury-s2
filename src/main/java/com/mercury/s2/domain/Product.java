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
  public String group;
  public String name;
  public Double price;
  public String origin;

  Product() {
  }

  public Product(String category, String group, String name, Double price, String origin) {
    this.category = category;
    this.group = group;
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
    return group;
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
