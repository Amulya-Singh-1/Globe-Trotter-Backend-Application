package com.globetrotter.application.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globetrotter.application.Models.CityDetails;
import com.globetrotter.application.Repository.CityDetailsRepository;

import java.util.List;

@Service
public class InternalUseService {

  @Autowired
  private CityDetailsRepository cityDetailsRepository;

  public List<CityDetails> saveCities(List<CityDetails> cities) {
    return cityDetailsRepository.saveAll(cities);
  }

}
