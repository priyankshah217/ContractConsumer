package com.example.consumer.repository;

import com.example.consumer.entity.Entertainment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class EntertainmentRepositoryTest {

  @Autowired
  TestEntityManager testEntityManager;

  @Autowired
  EntertainmentRepository entertainmentRepository;

  @Test
  void createShow() {
    Entertainment entertainment = new Entertainment();
    entertainment.setShowID(null);
    entertainment.setCategoryName("Non-Fictions");
    entertainment.setShowName("Friends");
    entertainmentRepository.save(entertainment);
    final Entertainment savedEntertainment = testEntityManager.persistFlushFind(entertainment);
    assertThat(savedEntertainment).isEqualTo(entertainment);

    Entertainment duplicateEntertainment = new Entertainment();
    duplicateEntertainment.setShowID(null);
    duplicateEntertainment.setCategoryName("Non-Fictions");
    duplicateEntertainment.setShowName("Friends");

    assertThatThrownBy(() -> entertainmentRepository.save(duplicateEntertainment));
  }

  @Test
  void getAllShows() {
    Entertainment entertainment1 = new Entertainment();
    entertainment1.setShowID(null);
    entertainment1.setCategoryName("Non-Fictions");
    entertainment1.setShowName("Friends");
    testEntityManager.persistAndFlush(entertainment1);

    Entertainment entertainment2 = new Entertainment();
    entertainment2.setShowID(null);
    entertainment2.setCategoryName("Non-Fictions");
    entertainment2.setShowName("ChuckNorris");
    testEntityManager.persistAndFlush(entertainment2);

    final List<Entertainment> savedEntertainmentList = StreamSupport.stream(entertainmentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    assertThat(savedEntertainmentList.contains(entertainment1)).isTrue();
  }

}