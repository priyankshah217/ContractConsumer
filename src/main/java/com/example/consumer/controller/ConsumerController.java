package com.example.consumer.controller;

import com.example.consumer.entity.Posts;
import com.example.consumer.entity.Entertainment;
import com.example.consumer.services.EntertainmentService;
import com.example.consumer.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("/")
public class ConsumerController {
  private EntertainmentService entertainmentService;
  private TweetService tweetService;

  @Autowired
  public ConsumerController(TweetService tweetService, EntertainmentService entertainmentService) {
    this.tweetService = tweetService;
    this.entertainmentService = entertainmentService;
  }

  @GetMapping("/posts/{showId}")
  public Posts getPostByID(@PathVariable String showId) {
    return tweetService.getTweetsByIds(showId);
  }

  @PostMapping("/show")
  public ResponseEntity<Entertainment> createShow(@RequestBody Entertainment entertainment) {
    final Entertainment savedEntertainment = entertainmentService.createShow(entertainment);
    return ResponseEntity.ok(savedEntertainment);
  }

  @GetMapping("/show")
  public List<Entertainment> getShow(Entertainment user) {
    return entertainmentService.getAllShows();
  }

}
