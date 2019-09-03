package com.example.consumer.services;

import com.example.consumer.api.TweetClient;
import com.example.consumer.entity.Posts;
import com.example.consumer.entity.Entertainment;
import com.example.consumer.entity.Tweet;
import com.example.consumer.exceptions.ShowDoesNotExists;
import com.example.consumer.repository.EntertainmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class TweetServiceTest {
  @Mock
  private EntertainmentRepository entertainmentRepository;

  @Mock
  private TweetClient tweetClient;

  @InjectMocks
  private TweetService tweetService;

  @BeforeEach
  void setUp() {
    initMocks(this);
  }

  @Test
  void getTweetsByIds() {
    final String expectedTweet = "This is my first tweet";

    Entertainment entertainment = new Entertainment();
    entertainment.setShowID("1");
    entertainment.setCategoryName("Non-Fictions");
    entertainment.setShowName("Friends");
    when(entertainmentRepository.findById(any())).thenReturn(java.util.Optional.of(entertainment));

    Tweet tweet = new Tweet();
    tweet.setUserID(null);
    tweet.setUserName("FirstUser");
    tweet.setTweetDescription("#Friends:This is my first tweet");
    List<Tweet> tweetList = Collections.singletonList(tweet);
    when(tweetClient.getAllTweets()).thenReturn(tweetList);

    final Posts expectedPosts = tweetService.getTweetsByIds("1");
    assertThat(expectedPosts.getTweets().get(0)).isEqualTo(expectedTweet);

    doThrow(ShowDoesNotExists.class).when(entertainmentRepository).findById("99");
    assertThatThrownBy(() -> tweetService.getTweetsByIds("99"));
  }
}