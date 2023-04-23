package com.webapp.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.webapp.models.Publisher;
import com.webapp.repositories.PublisherRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PublisherService {
  @Autowired
  private PublisherRepo publisherRepo;

  public Publisher save(Publisher publisher) {
    return publisherRepo.save(publisher);
  }

  public Publisher findOne(Long id) {
    Optional<Publisher> publisher = publisherRepo.findById(id);
    if (!publisher.isPresent()) {
      return null;
    }
    return publisher.get();
  }

  public Iterable<Publisher> findAll() {
    return publisherRepo.findAll();
  }

  public void removeOne(Long id) {
    publisherRepo.deleteById(id);
  }

  public List<Publisher> findByName(String name) {
    return publisherRepo.findByNameContains(name);
  }
}
