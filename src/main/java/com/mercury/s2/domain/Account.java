package com.mercury.s2.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name="accounts")
public class Account {

  @Id
  @GeneratedValue
  private Long id;
  private String username;
  private String password;

  public Account() {
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
