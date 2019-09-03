package com.example.consumer.services;

import com.example.consumer.entity.Entertainment;
import com.example.consumer.repository.EntertainmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EntertainmentService {
  private EntertainmentRepository entertainmentRepository;

  @Autowired
  EntertainmentService(EntertainmentRepository entertainmentRepository) {
    this.entertainmentRepository = entertainmentRepository;
  }

  public Entertainment createShow(Entertainment entertainment) {
    return entertainmentRepository.save(entertainment);
  }

  public List<Entertainment> getAllShows() {
    return StreamSupport.stream(entertainmentRepository.findAll().spliterator(), false).collect(Collectors.toList());
  }
}
