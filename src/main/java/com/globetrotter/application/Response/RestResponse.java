package com.globetrotter.application.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse {

  private String message;
  private String status;
  private String data;
  private String imageUrl;
  private Integer cityId;

  public RestResponse(String message, String status, String data) {
    this.message = message;
    this.status = status;
    this.data = data;
  }

  public RestResponse(String data) {
    this.data = data;
  }

}
