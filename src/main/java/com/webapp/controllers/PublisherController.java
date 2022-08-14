package com.webapp.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.webapp.dto.PublisherData;
import com.webapp.dto.ResponseData;
import com.webapp.models.Publisher;
import com.webapp.services.PublisherService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/publisher")

public class PublisherController {
  @Autowired
  private PublisherService publisherService;

  @Autowired
  private ModelMapper modelMapper;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<ResponseData<Publisher>> create(@Valid @RequestBody PublisherData publisherData,
      Errors errors) {
    ResponseData<Publisher> responseData = new ResponseData<>();
    if (errors.hasErrors()) {
      for (ObjectError error : errors.getAllErrors()) {
        responseData.getMessages().add(error.getDefaultMessage());
      }
      responseData.setStatus(false);
      responseData.setPayload(null);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }
    Publisher publisher = modelMapper.map(publisherData, Publisher.class);
    responseData.setStatus(true);
    responseData.setPayload(publisherService.save(publisher));
    return ResponseEntity.ok(responseData);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping
  public Iterable<Publisher> findAll() {
    return publisherService.findAll();
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping("/{id}")
  public Publisher findOne(@PathVariable("id") Long id) {
    return publisherService.findOne(id);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PutMapping
  public ResponseEntity<ResponseData<Publisher>> update(@Valid @RequestBody PublisherData publisherData,
      Errors errors) {
    ResponseData<Publisher> responseData = new ResponseData<>();
    if (errors.hasErrors()) {
      for (ObjectError error : errors.getAllErrors()) {
        responseData.getMessages().add(error.getDefaultMessage());
      }
      responseData.setStatus(false);
      responseData.setPayload(null);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }
    Publisher publisher = modelMapper.map(publisherData, Publisher.class);
    responseData.setStatus(true);
    responseData.setPayload(publisherService.save(publisher));
    return ResponseEntity.ok(responseData);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("delete/{id}")
  public void removeOne(@PathVariable("id") Long id) {
    publisherService.removeOne(id);
  }
}
