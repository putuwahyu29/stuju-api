package com.webapp.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webapp.models.Publication;
import com.webapp.models.Publisher;
import com.webapp.models.Type;
import com.webapp.repositories.PublicationRepo;

@Service
@Transactional
public class PublicationService {

  @Autowired
  private PublicationRepo publicationRepo;

  public Publication save(Publication publication) {
    if (publication.getId() != null) {
      Publication currentPublication = publicationRepo.findById(publication.getId()).get();
      currentPublication.setName(publication.getName());
    }
    return publicationRepo.save(publication);
  }

  public Publication findOne(Long id) {
    Optional<Publication> publication = publicationRepo.findById(id);
    if (!publication.isPresent()) {
      return null;
    }
    return publication.get();
  }

  public Iterable<Publication> findAll() {
    return publicationRepo.findAll();
  }

  public Iterable<Publication> findAllPagination(int offset, int limit) {
    return publicationRepo.findAll(PageRequest.of(offset, limit));
  }

  public void removeOne(Long id) {
    publicationRepo.deleteById(id);
  }

  public void removeAll() {
    publicationRepo.deleteAll();
  }

  public void addPublisher(Publisher publisher, Long publicationId) {
    Publication publication = findOne(publicationId);
    if (publication == null) {
      throw new RuntimeException("Publication ID" + publicationId + "not found");
    }
    publication.getPublishers().add(publisher);
    save(publication);
  }

  public void changeType(Type type, Long publicationId) {
    Publication publication = findOne(publicationId);
    if (publication == null) {
      throw new RuntimeException("Publication ID" + publicationId + "not found");
    }
    publication.setType(type);
  }

  public Publication findByPublicationName(String name) {
    return publicationRepo.findPublicationByName(name);
  }

  public List<Publication> findByPublicationNameLike(String name) {
    return publicationRepo.findPublicationByNameLike("%" + name + "%");
  }

  public List<Publication> findByType(Long typeId) {
    return publicationRepo.findPublicationByType(typeId);
  }

  public Iterable<Publication> findByName(String name, Pageable pageable) {
    return publicationRepo.findByNameContains(name, pageable);
  }

  public List<Publication> findMyPublication(String username) {
    return publicationRepo.findMyPublication(username);
  }
}
