package com.mercury.s2.service.product;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import java.util.Optional;
import java.util.Collection;

import com.mercury.s2.repository.ProductRepository;
import com.mercury.s2.domain.Product;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

  private ProductService productService;
  private ProductRepository productRepositoryMock;
  private Product productMock;
  private Product originalProduct;
  private Product inputMock;
  private Optional<Product> foundProductMock;

  @Before
  public void setUp() {
    productMock = mock(Product.class);
    inputMock = mock(Product.class);
    originalProduct = mock(Product.class);
    when(inputMock.getProductName()).thenReturn("test product");
    productRepositoryMock = mock(ProductRepository.class);
    when(productRepositoryMock.save(any(Product.class))).thenReturn(inputMock);
    when(productRepositoryMock.saveAndFlush(any(Product.class))).thenReturn(inputMock);
    when(productRepositoryMock.findOne(Long.valueOf(111))).thenReturn(inputMock);
    when(productRepositoryMock.findOne(Long.valueOf(112))).thenReturn(null);
    productService = new ProductServiceImpl(productRepositoryMock);
  }

  @Test
  public void createCallsSaveOnProductRepositoryAndReturnsTheProduct() throws Exception {
    productMock = productService.create(inputMock);
    assertEquals("Returned product has its name", productMock.getProductName(), "test product");
  }

  @Test
  public void updateCallsSaveAndFlushOnProductRepositoryAndReturnsTheProduct() throws Exception {
    productMock = productService.update(originalProduct, inputMock);
    assertEquals("Returned product has its name", productMock.getProductName(), "test product");
  }

  @Test
  public void getProductByIdCallsFindOneOnProductRepoWithValidId() throws Exception {
    productMock = productService.getProductById(Long.valueOf(111)).get();
    assertEquals("Returned product has its name", productMock.getProductName(), "test product");
  }

  @Test
  public void getProductByIdCallsFindOneOnProductRepoWithInvalidId() throws Exception {
    assertEquals("Null is returned", productService.getProductById(Long.valueOf(112)).isPresent(), false);
  }



}
