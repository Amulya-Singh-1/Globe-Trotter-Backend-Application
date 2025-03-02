package com.globetrotter.application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globetrotter.application.Response.RestResponse;
import com.globetrotter.application.Security.JwtUtil;
import com.globetrotter.application.Service.GameService;
import com.globetrotter.application.Service.UserService;

@RestController
@RequestMapping("/game")
public class GameController {

  @Autowired
  private GameService gameService;

  @Autowired
  private UserService userService;

  @GetMapping("/question")
  public ResponseEntity<RestResponse> getRandomQuestion() {
    return ResponseEntity.ok(gameService.fetchRandomQuestion());
  }

  @PostMapping("/submit")
  public ResponseEntity<String> submitAnswer(@RequestParam int cityId, @RequestParam String answer,
      @RequestHeader("Authorization") String authToken) {
    boolean isCorrect = gameService.checkAnswer(cityId, answer);
    userService.updateScore(authToken, isCorrect);
    return ResponseEntity.ok(isCorrect ? "Correct! 🎉" : "Incorrect! 😢");
  }

}

