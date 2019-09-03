package com.example.consumer.repository;

import com.example.consumer.entity.Entertainment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntertainmentRepository extends CrudRepository<Entertainment, String> {

}
