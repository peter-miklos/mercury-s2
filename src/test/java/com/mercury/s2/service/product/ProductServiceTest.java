package com.mercury.s2.service.product;

import org.junit.Test;
import org.junit.Before;
import java.util.List;
import java.util.ArrayList;

import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import com.mercury.s2.repository.ProductRepository;
import com.mercury.s2.domain.Product;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

  private ProductService productService;
  private ProductRepository productRepositoryMock;
  private Product productMock, originalProduct, inputMock;

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

  @Test
  public void getAllProductsCallsFindAllOnProductRepoAndGetAnEmptyArray() throws Exception {
    when(productRepositoryMock.findAll()).thenReturn(null);
    assertEquals("Empty array returned", productService.getAllProducts().isEmpty(), true);
  }

  @Test
  public void deleteProductDeletesTheRequiredProductAndReturnsAMessage() throws Exception {
    Mockito.doNothing().when(productRepositoryMock).delete(Long.valueOf(111));
    String message = "{\"message\":\"Product (id: 111) has been successfully deleted\"}";
    assertEquals("Confirmation returned", productService.delete(Long.valueOf(111)).toString(), message);
  }

  @Test
  public void getAllProductsCallsFindAllOnProductRepoAndGetASetOfProducts() throws Exception {
    Product product1 = mock(Product.class);
    when(product1.getProductName()).thenReturn("test product 1");
    Product product2 = mock(Product.class);
    when(product2.getProductName()).thenReturn("test product 2");
    List<Product> products = new ArrayList<Product>();
    products.add(product1);
    products.add(product2);
    when(productRepositoryMock.findAll(any(Sort.class))).thenReturn(products);

    assertEquals("List of proudcts returned", productService.getAllProducts().contains(product1), true);
    assertEquals("List of proudcts returned", productService.getAllProducts().contains(product2), true);
  }

}
