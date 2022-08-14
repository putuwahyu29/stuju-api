package com.webapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.models.User;
import com.webapp.repositories.UserRepo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserRepo userRepo;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public Iterable<User> findAll() {
    return userRepo.findAll();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{username}")
  public Optional<User> getUserByUsername(@PathVariable("username") String username) {
    return userRepo.findByUsername(username);
  }

}
