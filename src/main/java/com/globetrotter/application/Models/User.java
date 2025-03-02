package com.globetrotter.application.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Column(unique = true, nullable = false)
  private String username;

  @NotEmpty
  private String password;

  @Email
  private String email;

  @Column(nullable = false)
  private int score;

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

//  public User(String username, int score) {
//    this.username = username;
//    this.score = score;
//  }
}

