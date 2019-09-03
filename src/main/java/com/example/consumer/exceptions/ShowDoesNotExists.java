package com.example.consumer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShowDoesNotExists extends RuntimeException {
  public ShowDoesNotExists(String message) {
    super(message);
  }
}
