package com.mercury.s2.controller;

import com.mercury.s2.MercuryS2Application;
import com.mercury.s2.domain.User;
import com.mercury.s2.repository.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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
        this.user2 = new User();
    }
}