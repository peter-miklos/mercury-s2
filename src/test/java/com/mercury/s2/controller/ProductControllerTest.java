package com.mercury.s2.controller;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import java.util.*;

import com.mercury.s2.service.product.ProductService;
import com.mercury.s2.service.user.UserService;
import com.mercury.s2.domain.Product;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(includeFilters = @Filter(classes = EnableWebSecurity.class))
public class ProductControllerTest {

  private ProductController productController;
  private Product product1;
  private Product product2;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ProductService productService;

  @MockBean
  private UserService userService;

  @Test
  public void allProductsReturnedIfRequested() throws Exception {
    this.mvc.perform(get("/api/v1/products").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
           .andExpect(status().isOk());
  }

}
