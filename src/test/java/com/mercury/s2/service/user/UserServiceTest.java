package com.mercury.s2.service.user;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.mercury.s2.repository.UserRepository;
import com.mercury.s2.domain.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

  private UserService userService;
  private UserRepository userRepositoryMock;
  private User inputMock;
  private User userMock;

  @Before
  public void setUp() {
    inputMock = mock(User.class);
    userMock = mock(User.class);
    when(inputMock.getUsername()).thenReturn("test@test.com");
    when(inputMock.getPassword()).thenReturn("hashedPassword");
    userRepositoryMock = mock(UserRepository.class);
    when(userRepositoryMock.save(any(User.class))).thenReturn(inputMock);
    userService = new UserServiceImpl(userRepositoryMock);
  }

  @Test
  public void createUserCallsSaveOnUserRepositoryAndReturnsTheUser() throws Exception {
    userMock = userService.create(inputMock);
    assertEquals("Returned user has username of test@test.com", userMock.getUsername(), "test@test.com");
    assertEquals("Returned user has password of hashedPassword", userMock.getPassword(), "hashedPassword");
  }
}
