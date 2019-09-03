package com.example.consumer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Posts {
  @JsonProperty("tweets")
  public List<String> tweets = null;
}