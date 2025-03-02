package com.globetrotter.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globetrotter.application.Models.CityDetails;

import java.util.Optional;

@Repository
public interface CityDetailsRepository extends JpaRepository<CityDetails, Integer> {

  @Query(value = "SELECT * FROM city_details ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
  Optional<CityDetails> findRandomCity();

}
