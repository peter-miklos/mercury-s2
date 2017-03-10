package com.mercury.s2.service.user;

import com.mercury.s2.domain.User;
import com.mercury.s2.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User create(User input) {
    User user = new User();
    user.setUsername(input.getUsername());
    user.setPassword(new BCryptPasswordEncoder().encode(input.getPassword()));
    return userRepository.save(user);
  }

  // @Override
  // public User update(User input) {
  //
  // }

  // @Override
  // public String delete(Long id) {
  //
  // }
}
