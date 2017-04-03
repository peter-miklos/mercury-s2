package com.mercury.s2.controller;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.http.MediaType;
import java.util.*;
import java.nio.charset.Charset;

import com.mercury.s2.service.product.ProductService;
import com.mercury.s2.service.user.UserService;
import com.mercury.s2.domain.Product;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(includeFilters = @Filter(classes = EnableWebSecurity.class))
public class ProductControllerTest {

  private ProductController productController;
  private Product product1;
  private Product product2;
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ProductService productService;

  @MockBean
  private UserService userService;

  @Test
  public void allProductsReturnedIfRequested() throws Exception {
    product1 = new Product();
    product2 = new Product();
    product2.setProductName("Product 2");
    product1.setProductPrice(1.99);
    Collection<Product> allProducts = new ArrayList<Product>();
    allProducts.add(product1);
    allProducts.add(product2);
    given(this.productService.getAllProducts()).willReturn(allProducts);
    this.mvc.perform(get("/api/v1/products"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(contentType))
           .andExpect(jsonPath("$", hasSize(2)))
           .andExpect(jsonPath("$[0].productPrice", is(1.99)))
           .andExpect(jsonPath("$[1].productName", is("Product 2")));
  }

  @Test
  public void emptyArrayReturnedIfThereIsNoProduct() throws Exception {
    given(this.productService.getAllProducts()).willReturn(new ArrayList<Product>());
    this.mvc.perform(get("/api/v1/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(0)));
  }

}
