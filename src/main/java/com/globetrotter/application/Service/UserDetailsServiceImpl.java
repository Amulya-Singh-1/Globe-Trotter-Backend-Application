package com.globetrotter.application.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.globetrotter.application.Models.User;
import com.globetrotter.application.Repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user = userRepository.findByUsername(username)
//        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//    return new org.springframework.security.core.userdetails.User(user.getUsername(),
//        "", Collections.emptyList());
//  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(user -> new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), new ArrayList<>()))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
