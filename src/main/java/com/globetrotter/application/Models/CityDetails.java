package com.globetrotter.application.Models;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city_details")
public class CityDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String city;
  private String country;
  private String imageUrl;

  @ElementCollection
  @CollectionTable(name = "city_clues", joinColumns = @JoinColumn(name = "city_id"))
  private List<String> clues;

  @ElementCollection
  @CollectionTable(name = "city_fun_fact", joinColumns = @JoinColumn(name = "city_id"))
  private List<String> funFact;

  @ElementCollection
  @CollectionTable(name = "city_trivia", joinColumns = @JoinColumn(name = "city_id"))
  private List<String> trivia;

  public CityDetails(String city, String country, List<String> clues, List<String> funFact,
      List<String> trivia) {
    this.city = city;
    this.country = country;
    this.clues = clues;
    this.funFact = funFact;
    this.trivia = trivia;
  }

}
