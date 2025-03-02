package com.globetrotter.application.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.globetrotter.application.Repository.UserRepository;
import com.globetrotter.application.Response.RestResponse;
import com.globetrotter.application.Security.JwtUtil;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);

  public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
      JwtUtil jwtUtil, AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  public RestResponse authenticate(String username, String password) {
    RestResponse response = new RestResponse();
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if( ! passwordEncoder.matches(password, userDetails.getPassword())) {
        response = new RestResponse("Invalid password", "Failed", null);
      }
      Authentication authenticate = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));
      if(authenticate.isAuthenticated()) {
        response = new RestResponse("Authentication Successful", "Authenticated",
            jwtUtil.generateToken(userDetails));
      }
    } catch (Exception e) {
      logger.error("Caught exception while authenticating : " + e);
    }
    return response;
  }
}
