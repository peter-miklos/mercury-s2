package com.mercury.s2.service.product;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.mercury.s2.repository.ProductRepository;
import com.mercury.s2.domain.Product;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

  private ProductService productService;
  private ProductRepository productRepositoryMock;
  private Product productMock;
  private Product inputMock;

  @Before
  public void setUp() {
    productMock = mock(Product.class);
    inputMock = mock(Product.class);
    when(inputMock.getProductName()).thenReturn("test product");
    productRepositoryMock = mock(ProductRepository.class);
    when(productRepositoryMock.save(any(Product.class))).thenReturn(inputMock);
    productService = new ProductServiceImpl(productRepositoryMock);
  }

  @Test
  public void createCallsSaveOnProductRepositoryAndReturnsTheProduct() throws Exception {
    productMock = productService.create(inputMock);
    assertEquals("Returned product has its name", productMock.getProductName(), "test product");
  }

}
