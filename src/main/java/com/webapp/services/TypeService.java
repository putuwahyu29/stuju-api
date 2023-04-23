package com.webapp.services;

import java.util.Optional;

import javax.transaction.Transactional;

import com.webapp.models.Type;
import com.webapp.repositories.TypeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TypeService {
  @Autowired
  private TypeRepo typeRepo;

  public Type save(Type type) {
    if (type.getId() != null) {
      Type currentType = typeRepo.findById(type.getId()).get();
      currentType.setName(type.getName());

      type = currentType;
    }
    return typeRepo.save(type);
  }

  public Type findOne(Long id) {
    Optional<Type> type = typeRepo.findById(id);
    if (!type.isPresent()) {
      return null;
    }
    return type.get();
  }

  public Iterable<Type> findAll() {
    return typeRepo.findAll();
  }

  public void removeOne(Long id) {
    typeRepo.deleteById(id);
  }

  public Iterable<Type> findByName(String name, Pageable pageable) {
    return typeRepo.findByNameContaining(name, pageable);
  }

  public Iterable<Type> saveBatch(Iterable<Type> type) {
    return typeRepo.saveAll(type);
  }

}
