package com.globetrotter.application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globetrotter.application.Models.CityDetails;
import com.globetrotter.application.Service.InternalUseService;

import java.util.List;

@RestController
@RequestMapping("/internal")
public class InternalUseController {

  @Autowired
  private InternalUseService internalUseService;

  @PostMapping("/addQuestions")
  public ResponseEntity<List<CityDetails>> addCities(@RequestBody List<CityDetails> cityDetails) {
    return ResponseEntity.ok(internalUseService.saveCities(cityDetails));
  }

}
