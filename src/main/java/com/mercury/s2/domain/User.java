package com.mercury.s2.domain;

import org.json.simple.JSONObject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="users")
public class User {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  public User() {
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
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

  @Override
  public String toString() {
    return "User{" +
            "\"id\"=" + id +
            ", \"username\"=\"" + username.replaceFirst("@.*", "@***") +
            "\"}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("username", username.replaceFirst("@.*", "@***"));
    return json;
  }
}
