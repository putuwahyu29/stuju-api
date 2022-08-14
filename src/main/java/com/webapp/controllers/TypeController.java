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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.dto.ResponseData;
import com.webapp.dto.TypeData;
import com.webapp.models.Type;
import com.webapp.services.TypeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/type")

public class TypeController {

  @Autowired
  private TypeService typeService;

  @Autowired
  private ModelMapper modelMapper;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<ResponseData<Type>> create(@Valid @RequestBody TypeData typeData, Errors errors) {
    ResponseData<Type> responseData = new ResponseData<>();
    if (errors.hasErrors()) {
      for (ObjectError error : errors.getAllErrors()) {
        responseData.getMessages().add(error.getDefaultMessage());
      }
      responseData.setStatus(false);
      responseData.setPayload(null);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }
    Type type = modelMapper.map(typeData, Type.class);

    responseData.setStatus(true);
    responseData.setPayload(typeService.save(type));
    return ResponseEntity.ok(responseData);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping
  public Iterable<Type> findAll() {
    return typeService.findAll();
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping("/{id}")
  public Type findOne(@PathVariable("id") Long id) {
    return typeService.findOne(id);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("delete/{id}")
  public void removeOne(@PathVariable("id") Long id) {
    typeService.removeOne(id);
  }

}
