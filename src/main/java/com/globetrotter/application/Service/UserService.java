package com.globetrotter.application.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.globetrotter.application.Models.User;
import com.globetrotter.application.Repository.UserRepository;
import com.globetrotter.application.Request.UserRequest;
import com.globetrotter.application.Response.UserResponse;
import com.globetrotter.application.Security.JwtUtil;

import java.util.Optional;

@Service
public class UserService {

  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private UserDetailsService userDetailsService;

  public ResponseEntity<String> saveUser(UserRequest userRequest) {
    try {
      if (userRepository.existsByUsername(userRequest.getEmail())) {
        return new ResponseEntity<>("Email already in use", HttpStatus.NOT_ACCEPTABLE);
      }

      if (userRepository.existsByUsername(userRequest.getUsername())) {
        return new ResponseEntity<>("Username already in use", HttpStatus.NOT_ACCEPTABLE);
      }

      String encode = bCryptPasswordEncoder.encode(userRequest.getPassword());

      userRepository.save(new User(userRequest.getUsername(),
          bCryptPasswordEncoder.encode(userRequest.getPassword()), userRequest.getEmail()));

      UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getUsername());

      return new ResponseEntity<>("User saved successfully", HttpStatus.ACCEPTED);
    } catch (Exception e) {
      logger.error("Caught exception while saving user : ", e);
    }
    return new ResponseEntity<>("Error saving user", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public boolean validateUser(String username) {
    // Assuming user does not exist by default.
    boolean valid = false;
    try {
      valid = userRepository.findByUsername(username).isPresent();
    } catch (Exception e) {
      logger.error("Caught exception while validating user : ", e);
    }
    return valid;
  }

  public UserResponse getScore(String authToken) {
    if(!ObjectUtils.isEmpty(authToken) && authToken.startsWith("Bearer ")) {
      authToken = authToken.substring(7);
    }
    String username = JwtUtil.extractUsername(authToken);

    // Initial score to be returned. Assume 0 as default.
    int score = 0;
    try {
      score = userRepository.findByUsername(username)
          .map(User::getScore)
          .orElse(0);
    } catch (Exception e) {
      logger.error("Error fetching user stats", e);
    }
    return new UserResponse(username, score);
  }

  public void updateScore(String authToken, boolean isCorrect) {
    if(!ObjectUtils.isEmpty(authToken) && authToken.startsWith("Bearer ")) {
      authToken = authToken.substring(7);
    }
    String username = JwtUtil.extractUsername(authToken);

    // Increase user's current score by 10 if his answer is correct
    try {
      Optional<User> user = userRepository.findByUsername(username);
      user.ifPresent(updatedUser -> {
        int newScore = updatedUser.getScore() + (isCorrect ? 10 : 0);
        updatedUser.setScore(newScore);
        userRepository.save(updatedUser);
      });
    } catch (Exception e) {
      logger.error("Error updating user's score for {} : ", username, e);
    }
  }
}

