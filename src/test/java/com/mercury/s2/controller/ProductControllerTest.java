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
  private Long product1Id;

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
    product1Id = product1.getId();
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
    this.mvc.perform(get("/api/v1/product/{id}", this.productList.get(0).getId()))
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

    @Test
    public void exceptionThrownIfCategoryIsMissingInProductCreation() throws Exception {
        product1.setProductCategory(null);

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfCategoryIsInvalidInProductCreation() throws Exception {
        product1.setProductCategory("Cate");

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfGroupIsMissingInProductCreation() throws Exception {
      product1.setProductGroup(null);

      this.mvc.perform(post("/api/v1/product")
              .contentType(contentType)
              .content(json(product1)))
              .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfGroupIsInvalidInProductCreation() throws Exception {
        product1.setProductGroup("Grou");

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfNameIsMissingInProductCreation() throws Exception {
        product1.setProductName(null);

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfNameIsInvalidInProductCreation() throws Exception {
        product1.setProductName("Prod");

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfPriceIsMissingInProductCreation() throws Exception {
        product1.setProductPrice(null);

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfPriceIsInvalidInProductCreation() throws Exception {
        product1.setProductPrice(0.0001);

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfOriginIsMissingInProductCreation() throws Exception {
        product1.setProductOrigin(null);

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfOriginIsInvalidInProductCreation() throws Exception {
        product1.setProductOrigin("A");

        this.mvc.perform(post("/api/v1/product")
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct() throws Exception {
        System.out.println(product1Id);
        product1.setProductCategory("Category 9");
        product1.setProductGroup("Product group 9");
        product1.setProductName("Product 9");
        product1.setProductPrice(33.97);
        product1.setProductOrigin("Hungary");
        System.out.println(product1Id);

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.productCategory", is(product1.getProductCategory())))
                .andExpect(jsonPath("$.productGroup", is(product1.getProductGroup())))
                .andExpect(jsonPath("$.productName", is(product1.getProductName())))
                .andExpect(jsonPath("$.productPrice", is(product1.getProductPrice())))
                .andExpect(jsonPath("$.productOrigin", is(product1.getProductOrigin())));

    }

    @Test
    public void errorMsgReturnedIfProductNotFoundInProductUpdate() throws Exception {
        Long productId = this.productList.get(0).getId() - Long.valueOf(1);
        try {
            this.mvc.perform(put("/api/v1/product/{id}", productId)
                    .contentType(contentType)
                    .content(json(product1)));
            fail("Excception has not been thrown");
        } catch (NestedServletException e) {
            assertEquals(e.getCause().getMessage(), "Product(id: " + productId + ") not found");
        }
    }

    @Test
    public void exceptionThrownIfCategoryIsMissingInProductUpdate() throws Exception {
        product1.setProductCategory(null);

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfCategoryIsInvalidInProductUpdate() throws Exception {
        product1.setProductCategory("Cate");

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfGroupIsMissingInProductUpdate() throws Exception {
        product1.setProductGroup(null);

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfGroupIsInvalidInProductUpdate() throws Exception {
        product1.setProductGroup("Grou");

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfNameIsMissingInProductUpdate() throws Exception {
        product1.setProductName(null);

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfNameIsInvalidInProductUpdate() throws Exception {
        product1.setProductName("Prod");

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfPriceIsMissingInProductUpdate() throws Exception {
        product1.setProductPrice(null);

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfPriceIsInvalidInProductUpdate() throws Exception {
        product1.setProductPrice(0.0001);

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfOriginIsMissingInProductUpdate() throws Exception {
        product1.setProductOrigin(null);

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exceptionThrownIfOriginIsInvalidInProductUpdate() throws Exception {
        product1.setProductOrigin("A");

        this.mvc.perform(put("/api/v1/product/{id}", product1Id)
                .contentType(contentType)
                .content(json(product1)))
                .andExpect(status().isBadRequest());
    }

    protected byte[] json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(o);
    }

}
