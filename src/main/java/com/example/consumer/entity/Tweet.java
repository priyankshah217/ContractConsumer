package com.example.consumer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet implements Serializable {
  @JsonProperty("userID")
  private String userID;

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("tweetDescription")
  private String tweetDescription;
}
