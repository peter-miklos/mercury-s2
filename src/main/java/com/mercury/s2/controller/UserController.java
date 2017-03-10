package com.mercury.s2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mercury.s2.domain.User;
import com.mercury.s2.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  private final UserRepository userRepository;

  @Autowired
  UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/users/new")
  public String userSubmit(@RequestBody User input) {
    User user = new User();
    user.setUsername(input.getUsername());
    user.setPassword(new BCryptPasswordEncoder().encode(input.getPassword()));
    return userRepository.save(user).toString();
  }



}
