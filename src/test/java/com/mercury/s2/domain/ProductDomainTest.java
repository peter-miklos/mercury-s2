package com.mercury.s2.domain;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class ProductDomainTest {

  private Product product;

  @Before
  public void setUp() {
    product = new Product();
  }

  @Test
  public void getIdReturnsId() throws Exception {
    assertEquals("Id returned", product.getId(), null);
  }

  @Test
  public void setProductCategorySetsCategoryAndGetProductCategoryReturnsCategory() throws Exception {
    String category = "test category";
    product.setProductCategory(category);
    assertEquals("Category value returned", product.getProductCategory(), category);
  }

  @Test
  public void setProductGroupSetsGroupAndGetProductGroupReturnsGroup() throws Exception {
    String group = "test group";
    product.setProductGroup(group);
    assertEquals("Group value returned", product.getProductGroup(), group);
  }

  @Test
  public void setProductNameSetsNameAndGetProductNameReturnsName() throws Exception {
    String name = "test product name";
    product.setProductName(name);
    assertEquals("Name value returned", product.getProductName(), name);
  }

  @Test
  public void setProductPriceSetsPriceAndGetProductPriceReturnsPrice() throws Exception {
    Double price = 15.99;
    product.setProductPrice(price);
    assertEquals("Price value returned", product.getProductPrice(), price);
  }

  @Test
  public void setProductOriginSetsOriginAndGetProductOriginReturnsOrigin() throws Exception {
    String origin = "UK";
    product.setProductOrigin(origin);
    assertEquals("Origin value returned", product.getProductOrigin(), origin);
  }
}
