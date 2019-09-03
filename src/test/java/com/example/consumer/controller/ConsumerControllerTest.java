package com.example.consumer.controller;

import com.example.consumer.entity.Posts;
import com.example.consumer.entity.Entertainment;
import com.example.consumer.services.EntertainmentService;
import com.example.consumer.services.TweetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
class ConsumerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EntertainmentService entertainmentService;

  @MockBean
  private TweetService tweetService;

  private ObjectMapper mapper;

  @Test
  void getPostByID() throws Exception {
    final List<String> expectedList = Arrays.asList(
        "Chuck Norris hosting is 101% uptime guaranteed.",
        "\"It works on my machine\" always holds true for Chuck Norris.",
        "Chuck Norris can read all encrypted data, because nothing can hide from Chuck Norris.",
        "Chuck Norris' programs occupy 150% of CPU, even when they are not executing.",
        "Chuck Norris knows the last digit of PI.");
    Posts posts = new Posts();
    posts.setTweets(expectedList);
    when(tweetService.getTweetsByIds(any())).thenReturn(posts);
    mockMvc.perform(get("/posts/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.tweets.[0]",
            new Is<>(equalTo("Chuck Norris hosting is 101% uptime guaranteed."))));
  }

  @Test
  void createShow() throws Exception {
    Entertainment entertainment = new Entertainment();
    entertainment.setShowID("1");
    entertainment.setCategoryName("Non-Fictions");
    entertainment.setShowName("Friends");
    when(entertainmentService.createShow(any())).thenReturn(entertainment);
    mapper = new ObjectMapper();
    final String requestBody = mapper.writeValueAsString(entertainment);
    mockMvc.perform(post("/show")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.showName", new Is<>(equalTo("Friends"))));
  }

  @Test
  void getShow() throws Exception {
    Entertainment entertainment1 = new Entertainment();
    entertainment1.setShowID("1");
    entertainment1.setCategoryName("Non-Fictions");
    entertainment1.setShowName("Friends");

    Entertainment entertainment2 = new Entertainment();
    entertainment2.setShowID("1");
    entertainment2.setCategoryName("Non-Fictions");
    entertainment2.setShowName("Friends");
    List<Entertainment> entertainmentList = Arrays.asList(entertainment1, entertainment2);

    when(entertainmentService.getAllShows()).thenReturn(entertainmentList);
    mockMvc.perform(get("/show")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$[0].categoryName", new Is<>(equalTo(entertainment1.getCategoryName()))))
        .andExpect(jsonPath("$[0].showName", new Is<>(equalTo(entertainment1.getShowName()))));
  }
}