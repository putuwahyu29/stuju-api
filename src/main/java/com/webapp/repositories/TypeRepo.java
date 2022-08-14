package com.webapp.repositories;


import com.webapp.models.Type;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepo extends JpaRepository<Type, Long> {
  Page<Type> findByNameContaining(String name, Pageable pageable);
  
}
