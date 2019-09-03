package com.example.consumer.api;

import com.example.consumer.entity.Tweet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "TweetClient", url = "${app.url}")
public interface TweetClient {
  @RequestMapping("/tweet/")
  List<Tweet> getAllTweets();
}
