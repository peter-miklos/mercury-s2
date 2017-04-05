package com.mercury.s2.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.*;
import java.nio.charset.Charset;

import com.mercury.s2.repository.ProductRepository;
import com.mercury.s2.domain.Product;
import com.mercury.s2.MercuryS2Application;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MercuryS2Application.class)
@WebAppConfiguration
public class ProductControllerTest {

  private ProductController productController;
  private Product product1;
  private Product product2;
  private List<Product> productList = new ArrayList<>();
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private ProductRepository productRepository;

  @Before
  public void setup() throws Exception {
    this.mvc = webAppContextSetup(webApplicationContext).build();
    this.productRepository.deleteAllInBatch();
    product1 = new Product();
    product2 = new Product();
    product1.setProductCategory("Category 1");
    product1.setProductGroup("Group 1");
    product1.setProductName("Product 1");
    product1.setProductPrice(1.99);
    product1.setProductOrigin("UK");
    product2.setProductCategory("Category 2");
    product2.setProductGroup("Group 2");
    product2.setProductName("Product 2");
    product2.setProductPrice(11.11);
    product2.setProductOrigin("Germany");

    this.productList.add(productRepository.save(product1));
    this.productList.add(productRepository.save(product2));
  }

  @Test
  public void allProductsReturnedIfRequested() throws Exception {
    this.mvc.perform(get("/api/v1/products"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(contentType))
           .andExpect(jsonPath("$", hasSize(2)))
           .andExpect(jsonPath("$[0].productPrice", is(1.99)))
           .andExpect(jsonPath("$[1].productName", is("Product 2")));
  }

  @Test
  public void emptyArrayReturnedIfThereIsNoProduct() throws Exception {
    this.productRepository.deleteAllInBatch();
    this.mvc.perform(get("/api/v1/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void detailsOfRequestedProductReturned() throws Exception {
    this.mvc.perform(get("/api/v1/product/" + this.productList.get(0).getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.productCategory", is("Category 1")))
            .andExpect(jsonPath("$.productGroup", is("Group 1")))
            .andExpect(jsonPath("$.productName", is("Product 1")))
            .andExpect(jsonPath("$.productPrice", is(1.99)))
            .andExpect(jsonPath("$.productOrigin", is("UK")));
  }

  @Test
  public void errorMsgReturnedIfProductNotFound() throws Exception {
    Long productId = this.productList.get(0).getId() - Long.valueOf(1);
    try {
      this.mvc.perform(get("/api/v1/product/{id}", productId));
      fail("Excception has not been thrown");
    } catch (NestedServletException e) {
      assertEquals(e.getCause().getMessage(), "Product(id: " + productId + ") not found");
    }
  }

  @Test
  public void createProduct() throws Exception {
    product2.setProductName("Product 3");

    this.mvc.perform(post("/api/v1/product")
            .contentType(contentType)
            .content(json(product2)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.productCategory", is(product2.getProductCategory())))
            .andExpect(jsonPath("$.productGroup", is(product2.getProductGroup())))
            .andExpect(jsonPath("$.productName", is(product2.getProductName())))
            .andExpect(jsonPath("$.productPrice", is(product2.getProductPrice())))
            .andExpect(jsonPath("$.productOrigin", is(product2.getProductOrigin())));

  }

//  @Test
//  public void throwsExceptionIfProductNameIsMissing() throws Exception {
//      product1.setProductName(null);
//
//      this.mvc.perform(post("/api/v1/product")
//              .contentType(contentType)
//              .content(json(product1)))
//              .andExpect(status().isOk());
//  }

  protected byte[] json(Object o) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(o);
  }

}
