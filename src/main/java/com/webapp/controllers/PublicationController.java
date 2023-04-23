package com.webapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.dto.ResponseData;
import com.webapp.dto.SearchData;
import com.webapp.models.Publication;
import com.webapp.models.Publisher;
import com.webapp.models.Type;
import com.webapp.services.PublicationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/publication")
public class PublicationController {

  @Autowired
  private PublicationService publicationService;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<ResponseData<Publication>> create(@Valid @RequestBody Publication publication, Errors errors) {
    ResponseData<Publication> responseData = new ResponseData<>();
    if (errors.hasErrors()) {
      for (ObjectError error : errors.getAllErrors()) {
        responseData.getMessages().add(error.getDefaultMessage());
      }
      responseData.setStatus(false);
      responseData.setPayload(null);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }
    responseData.setStatus(true);
    responseData.setPayload(publicationService.save(publication));
    return ResponseEntity.ok(responseData);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping
  public Iterable<Publication> findAll() {
    return publicationService.findAll();
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping("view/{username}")
  public List<Publication> findMyPublication(@PathVariable String username) {
    return publicationService.findMyPublication(username);
  }

  @GetMapping("/pagination/{offset}/{limit}")
  public Iterable<Publication> findAllPagination(@PathVariable int offset, @PathVariable int limit) {
    return publicationService.findAllPagination(offset, limit);
  }

  @GetMapping("/{id}")
  public Publication findOne(@PathVariable("id") Long id) {
    return publicationService.findOne(id);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PutMapping
  public ResponseEntity<ResponseData<Publication>> update(@Valid @RequestBody Publication publication, Errors errors) {
    ResponseData<Publication> responseData = new ResponseData<>();
    if (errors.hasErrors()) {
      for (ObjectError error : errors.getAllErrors()) {
        responseData.getMessages().add(error.getDefaultMessage());
      }
      responseData.setStatus(false);
      responseData.setPayload(null);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }
    responseData.setStatus(true);
    responseData.setPayload(publicationService.save(publication));
    return ResponseEntity.ok(responseData);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @DeleteMapping("delete/{id}")
  public void removeOne(@PathVariable("id") Long id) {
    publicationService.removeOne(id);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("deleteall")
  public void removeAll() {
    publicationService.removeAll();
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping("/addpublisher/{id}")
  public void addPublisher(@RequestBody Publisher publisher, @PathVariable("id") Long publicationId) {
    publicationService.addPublisher(publisher, publicationId);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping("/changetype/{id}")
  public void changeType(@RequestBody Type type, @PathVariable("id") Long publicationId) {
    publicationService.changeType(type, publicationId);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping("/search/name")
  public Publication getPublicationByName(@RequestBody SearchData searchData) {
    return publicationService.findByPublicationName(searchData.getSearchKey());
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping("/search/namelike")
  public List<Publication> getPublicationByNamelike(@RequestBody SearchData searchData) {
    return publicationService.findByPublicationNameLike(searchData.getSearchKey());
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping("/search/type/{typeId}")
  public List<Publication> getPublicationByType(@PathVariable("typeId") Long typeId) {
    return publicationService.findByType(typeId);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping("/search/{size}/{page}")
  public Iterable<Publication> findByName(@RequestBody SearchData searchData, @PathVariable("size") int size,
      @PathVariable("page") int page) {
    Pageable pageable = PageRequest.of(page, size);
    return publicationService.findByName(searchData.getSearchKey(), pageable);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping("/search/{size}/{page}/{sort}")
  public Iterable<Publication> findByName(@RequestBody SearchData searchData, @PathVariable("size") int size,
      @PathVariable("page") int page, @PathVariable("sort") String sort) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
    if (sort.equals("desc")) {
      pageable = PageRequest.of(page, size, Sort.by("id").descending());
    }
    return publicationService.findByName(searchData.getSearchKey(), pageable);
  }

}
