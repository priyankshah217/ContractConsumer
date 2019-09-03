package com.example.consumer.services;

import com.example.consumer.api.TweetClient;
import com.example.consumer.entity.Posts;
import com.example.consumer.entity.Entertainment;
import com.example.consumer.exceptions.ShowDoesNotExists;
import com.example.consumer.repository.EntertainmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TweetService {
  private TweetClient tweetClient;

  private EntertainmentRepository entertainmentRepository;


  @Autowired
  public TweetService(TweetClient tweetClient, EntertainmentRepository entertainmentRepository) {
    this.tweetClient = tweetClient;
    this.entertainmentRepository = entertainmentRepository;
  }

  public Posts getTweetsByIds(String filter) {
    String showName = null;
    if (filter.matches("\\d+")) {
      final Entertainment optionalEntertainment =
          entertainmentRepository.findById(filter).orElseThrow(() ->
              new ShowDoesNotExists("Show does not exists"));
      showName = optionalEntertainment.getShowName();
    }
    String finalShowName = Objects.requireNonNull(showName).toLowerCase();
    Posts posts = new Posts();
    final List<String> listOfTweets = tweetClient.getAllTweets()
        .stream()
        .filter(tweet -> tweet.getTweetDescription()
            .toLowerCase()
            .contains(finalShowName))
        .map(tweet -> tweet.getTweetDescription().split(":")[1])
        .collect(Collectors.toList());
    posts.setTweets(listOfTweets);
    return posts;
  }
}
