package com.example.consumer.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Entertainment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  String showID;

  String categoryName;

  @Column(unique = true)
  String showName;
}
