package com.example.consumer.services;

import com.example.consumer.entity.Entertainment;
import com.example.consumer.repository.EntertainmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class EntertainmentServiceTest {

  @Mock
  private EntertainmentRepository entertainmentRepository;

  @InjectMocks
  private EntertainmentService entertainmentService;

  @BeforeEach
  void setUp() {
    initMocks(this);
  }

  @Test
  void createShow() {
    Entertainment entertainment = new Entertainment();
    entertainment.setShowID("1");
    entertainment.setCategoryName("Non-Fictions");
    entertainment.setShowName("Friends");
    when(entertainmentRepository.save(any())).thenReturn(entertainment);
    assertThat(entertainmentService.createShow(entertainment).getShowName()).isEqualTo(entertainment.getShowName());
  }

  @Test
  void getAllShows() {
    Entertainment entertainment = new Entertainment();
    entertainment.setShowID(null);
    entertainment.setCategoryName("Non-Fictions");
    entertainment.setShowName("Friends");
    List<Entertainment> entertainmentList = Collections.singletonList(entertainment);
    when(StreamSupport.stream(entertainmentRepository.findAll().spliterator(), false).collect(Collectors.toList())).thenReturn(entertainmentList);
    assertThat(entertainmentService.getAllShows().size()).isEqualTo(1);
    assertThat(entertainmentService.getAllShows().get(0)).isEqualTo(entertainment);
  }
}