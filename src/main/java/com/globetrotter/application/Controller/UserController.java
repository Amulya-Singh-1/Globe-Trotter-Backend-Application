package com.globetrotter.application.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globetrotter.application.Request.LoginRequest;
import com.globetrotter.application.Request.UserRequest;
import com.globetrotter.application.Response.RestResponse;
import com.globetrotter.application.Response.UserResponse;
import com.globetrotter.application.Security.JwtUtil;
import com.globetrotter.application.Service.AuthenticationService;
import com.globetrotter.application.Service.UserService;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final JwtUtil jwtUtil;

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private AuthenticationService authenticationService;

  public UserController(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
    if(ObjectUtils.isEmpty(userRequest)) {
      return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
    }
    return userService.saveUser(userRequest);
  }

  @PostMapping("/login")
  public ResponseEntity<RestResponse> loginUser(@RequestBody LoginRequest loginRequest) {
    logger.info("Login attempt for user: {}", loginRequest.getUsername());
    RestResponse response = new RestResponse();
    if (userService.validateUser(loginRequest.getUsername())) {
      response = authenticationService.authenticate(loginRequest.getUsername(),
          loginRequest.getPassword());
    }
    return ResponseEntity.ok(response);
  }

  @GetMapping("/score")
  public ResponseEntity<UserResponse> getUserScore(@RequestHeader("Authorization") String authToken) {
    UserResponse response = userService.getScore(authToken);
    return ResponseEntity.ok(response);
  }

}
