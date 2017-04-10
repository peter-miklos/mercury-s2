package com.mercury.s2.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercury.s2.MercuryS2Application;
import com.mercury.s2.domain.User;
import com.mercury.s2.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MercuryS2Application.class)
@WebAppConfiguration
public class UserControllerTest {

    private UserController userController;
    private User user1;
    private User user2;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() throws  Exception {
        this.mvc = webAppContextSetup(webApplicationContext).build();
        this.userRepository.deleteAllInBatch();

        this.user1 = new User();
        this.user1.setUsername("test1@test.com");
        this.user1.setPassword(("Password-1"));
        this.user2 = new User();
    }

    @Test
    public void createUser() throws Exception {
        this.mvc.perform(post("/api/v1/users/new")
                .contentType(contentType)
                .content(json(user1)))
                .andExpect(status().isOk());
//                .andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$.productCategory", is(product2.getProductCategory())));
    }

    protected byte[] json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(o);
    }
}