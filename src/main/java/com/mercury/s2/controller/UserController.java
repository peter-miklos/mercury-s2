package com.mercury.s2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.s2.domain.User;
import com.mercury.s2.service.user.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  private final UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/users/new")
  public String userSubmit(@RequestBody User input) {
    return userService.create(input).toString();
  }



}
