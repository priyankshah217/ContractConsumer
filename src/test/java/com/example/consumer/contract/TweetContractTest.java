package com.example.consumer.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.amazonaws.util.IOUtils;
import com.example.consumer.entity.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArrayMinLike;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "TweetProvider", providerType = ProviderType.SYNCH, port = "1234")
public class TweetContractTest {

  @Pact(provider = "TweetProvider", consumer = "TweetConsumer")
  public RequestResponsePact createTweetsPact(PactDslWithProvider pactDslWithProvider) {
    Map headers = MapUtils.putAll(new HashMap<>(), new String[] {
        "Content-Type", "application/json"
    });
    return pactDslWithProvider
        .uponReceiving("Receiving Tweets")
        .path("/tweet/")
        .method("Get")
        .willRespondWith()
        .headers(headers)
        .status(200)
        .body(newJsonArrayMinLike(1,
            lambdaDslJsonArray -> lambdaDslJsonArray
                .object(lambdaDslObject -> lambdaDslObject
                    .stringType("userID", "1")
                    .stringType("userName", "@Pablo229")
                    .stringType("tweetDescription", "#ChuckNorris:Chuck Norris hosting is 101% uptime guaranteed.")
                )).build())
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "createTweetsPact")
  void testConsumer(MockServer mockServer) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    final String expectedResponse = "[\n" +
        " {\n" +
        "\"userID\": \"1\",\n" +
        "\"userName\": \"@Pablo229\",\n" +
        "\"tweetDescription\": \"#ChuckNorris:Chuck Norris hosting is 101% uptime guaranteed.\"\n" +
        "},\n" +
        "{\n" +
        "\"userID\": \"2\",\n" +
        "\"userName\": \"@Michael229\",\n" +
        "\"tweetDescription\": \"#Yoda:Pain, suffering, death I feel. Something terrible has " +
        "happened. Young Skywalker is in pain. Terrible pain\"\n" +
        "}]";
    final Tweet[] expectedTweets = objectMapper.readValue(expectedResponse, Tweet[].class);
    final List<Tweet> expectedTweetList = Arrays.asList(expectedTweets);

    final HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/tweet/")
        .execute()
        .returnResponse();

    final String responseAsString = IOUtils.toString(httpResponse.getEntity().getContent());
    System.out.println(responseAsString);
    final Tweet[] actualTweets = objectMapper.readValue(responseAsString, Tweet[].class);
    final List<Tweet> actualTweetList = Arrays.asList(actualTweets);
    assertThat(actualTweetList.get(0)).isEqualTo(expectedTweetList.get(0));
  }
}
