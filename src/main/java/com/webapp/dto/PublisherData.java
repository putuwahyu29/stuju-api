package com.webapp.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherData {
  @NotEmpty(message = "Nama harus diisi")
  private String name;

  @NotEmpty(message = "NIM harus diisi")
  private String nim;

  @NotEmpty(message = "Email harus diisi")
  private String email;
}
