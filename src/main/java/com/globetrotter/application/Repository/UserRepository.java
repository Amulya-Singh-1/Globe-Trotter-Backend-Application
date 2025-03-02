package com.globetrotter.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.globetrotter.application.Models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

}

