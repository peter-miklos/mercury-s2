package com.mercury.s2.domain;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    @Size(min = 5, max = 30)
    private String productCategory;

    @Column
    @NotNull
    @Size(min = 5, max = 30)
    private String productGroup;

    @Column
    @NotNull
    @Size(min = 5, max = 30)
    private String productName;

    @Column
    @NotNull
    @DecimalMin(value = "0.01")
    private Double productPrice;

    @Column
    @NotNull
    @Size(min = 2, max = 30)
    private String productOrigin;

  public Product() {
  }

  public Long getId() {
    return id;
  }

  public String getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(String productCategory) {
    this.productCategory = productCategory;
  }

  public String getProductGroup() {
    return productGroup;
  }

  public void setProductGroup(String productGroup) {
    this.productGroup = productGroup;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Double getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(Double productPrice) {
    this.productPrice = productPrice;
  }

  public String getProductOrigin() {
    return productOrigin;
  }

  public void setProductOrigin(String productOrigin) {
    this.productOrigin = productOrigin;
  }
}
