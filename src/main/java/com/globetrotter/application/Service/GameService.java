package com.globetrotter.application.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globetrotter.application.Models.CityDetails;
import com.globetrotter.application.Models.User;
import com.globetrotter.application.Repository.CityDetailsRepository;
import com.globetrotter.application.Repository.UserRepository;
import com.globetrotter.application.Response.RestResponse;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

  @Autowired
  private CityDetailsRepository cityDetailsRepository;

  @Autowired
  private UserRepository userRepository;

  private final Logger logger = LoggerFactory.getLogger(GameService.class);

  public RestResponse fetchRandomQuestion() {
    RestResponse response = new RestResponse();
    try {
      Optional<CityDetails> randomQuestion = cityDetailsRepository.findRandomCity();
      if(randomQuestion.isPresent()) {
        List<String> clues = randomQuestion.get().getClues();
        response = new RestResponse("Random Question Loaded", "Successful",
            clues.get(0), randomQuestion.get().getImageUrl(), randomQuestion.get().getId() );
      } else {
        logger.warn("No city details were found in database.");
      }
    } catch (Exception e) {
      logger.error("Failed to fetch random question : " + e);
    }
    return response;
  }

  public boolean checkAnswer(int cityId, String answer) {
    boolean isCorrect = false;
    try {
      isCorrect = cityDetailsRepository.findById(cityId)
          .map(cityDetail -> cityDetail.getCity().equalsIgnoreCase(answer))
          .orElse(false);
    } catch (Exception e) {
      logger.error("Caught exception while checking answer : ", e);
    }
    return isCorrect;
  }

  public String getUserStats(String username) {
    try {
      if(userRepository.existsByUsername(username)) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
          int score = user.get().getScore();
          return "Your current score is: " + score;
        }
      } else {

      }
    } catch (Exception e) {
      logger.error("Caught exception while checking answer : ", e);
    }
    return "Stats feature coming soon!";
  }
}

