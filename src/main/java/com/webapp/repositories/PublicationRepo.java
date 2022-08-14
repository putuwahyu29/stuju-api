package com.webapp.repositories;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.models.Publication;

public interface PublicationRepo extends JpaRepository<Publication, Long> {

  @Query("SELECT p FROM Publication p WHERE p.name = :name")
  public Publication findPublicationByName(@PathParam("name") String name);

  @Query("SELECT p FROM Publication p WHERE p.name LIKE :name")
  public List<Publication> findPublicationByNameLike(@PathParam("name") String name);

  @Query("SELECT p FROM Publication p WHERE p.type.id = :typeId")
  public List<Publication> findPublicationByType(@PathParam("typeId") Long typeId);

  Page<Publication> findByNameContains(String name, Pageable pageable);

  @Query("SELECT p FROM Publication p WHERE p.updatedBy = :username")
  public List<Publication> findMyPublication(@PathParam("username") String username);
}
