package com.mercury.s2.domain;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class UserDomainTest {

  private User user;
  private String username, password;

  @Before
  public void setUp() {
    user = new User();
    username = "test@test.com";
    password = "Long password";
    user.setUsername(username);
    user.setPassword(password);
  }

  @Test
  public void setUsernameSetsUsernameAndGetUsernameReturnsUsername() throws Exception {
    assertEquals("username value returned", user.getUsername(), username);
  }

  @Test
  public void setPasswordSetsPasswordAndGetPasswordReturnsPassword() throws Exception {
    assertEquals("password value returned", user.getPassword(), password);
  }

  @Test
  public void toStringReturnsStringRepresentationOfUserDetails() throws Exception {
    String expectedValue = "User{id=null, username='test@***', passwordHash='Long passw'}";
    assertEquals("String representation returned", user.toString(), expectedValue);
  }

}
