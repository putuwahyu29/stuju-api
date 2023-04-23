package com.webapp.repositories;

import java.util.List;

import com.webapp.models.Publisher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher, Long> {
  List<Publisher> findByNameContains(String name);

}
