package com.webapp.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeData {
  private Long id;

  @NotEmpty(message = "Nama harus diisi")
  private String name;
}
