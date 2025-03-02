package com.globetrotter.application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globetrotter.application.Service.ChallengeService;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

  @Autowired
  private ChallengeService challengeService;

  @PostMapping("/invite")
  public ResponseEntity<String> generateInviteLink(@RequestParam String username) {
    return ResponseEntity.ok(challengeService.createInvite(username));
  }

  @GetMapping("/validate")
  public ResponseEntity<Integer> validateInvite(@RequestParam String inviteToken) {
    return ResponseEntity.ok(challengeService.getInviterScore(inviteToken));
  }
}

