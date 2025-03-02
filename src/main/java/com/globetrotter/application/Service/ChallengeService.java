package com.globetrotter.application.Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ChallengeService {
  private final Map<String, Integer> inviteStore = new HashMap<>();

  public String createInvite(String username) {
    String token = UUID.randomUUID().toString();
    inviteStore.put(token, (int) (Math.random() * 100));
    return "https://globetrotter.vercel.app/challenge?token=" + token;
  }

  public int getInviterScore(String inviteToken) {
    return inviteStore.getOrDefault(inviteToken, 0);
  }

}

