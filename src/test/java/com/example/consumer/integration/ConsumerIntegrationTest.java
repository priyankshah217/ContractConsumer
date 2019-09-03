package com.example.consumer.integration;

import com.example.consumer.entity.Posts;
import com.example.consumer.entity.Entertainment;
import com.example.consumer.repository.EntertainmentRepository;
import com.example.consumer.services.TweetService;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith( {SpringExtension.class})
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "app.url=http://localhost:8090"})
class ConsumerIntegrationTest {

  @Autowired
  TweetService tweetService;
  private WireMockServer wireMockServer;
  @Autowired
  private EntertainmentRepository entertainmentRepository;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @BeforeEach
  void setUp() {
    wireMockServer = new WireMockServer(8090);
    wireMockServer.start();
  }

  @Test
  public void testTweetService() {
    final String expectedTweet =
        "Oh, my God! If you say that one more time, I’m going to break up with you!";
    wireMockServer.stubFor(get(urlEqualTo("/tweet/"))
        .willReturn(aResponse()
            .withBodyFile("tweet.json")
            .withStatus(200)
            .withHeader("Content-Type", "application/json")));

    Entertainment entertainment = new Entertainment();
    entertainment.setShowID("1");
    entertainment.setCategoryName("Non-Fictions");
    entertainment.setShowName("Friends");
    entertainmentRepository.save(entertainment);

    final ResponseEntity<Posts> forEntity =
        testRestTemplate.getForEntity("/posts/1", Posts.class);

    assertThat(Objects.requireNonNull(forEntity.getBody()).getTweets().get(0))
        .isEqualTo(expectedTweet);
  }

  @AfterEach
  public void teardown() {
    wireMockServer.stop();
  }
}
