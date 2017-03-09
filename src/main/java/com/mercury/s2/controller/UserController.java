package com.mercury.s2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mercury.s2.domain.User;
import com.mercury.s2.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  private final UserRepository userRepository;

  UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/users/new")
  public User userSubmit(@RequestBody User input) {
    User result = this.userRepository.save(
      new User(input.username, new BCryptPasswordEncoder().encode(input.password))
    );
    return result;
  }
}
