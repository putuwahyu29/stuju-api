package com.webapp.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_publisher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id", scope = Long.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "publications" })
public class Publisher implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Nama harus diisi")
  @Column(name = "publisher_name", length = 100)
  private String name;

  @NotEmpty(message = "NIM harus diisi")
  @Column(name = "publisher_nim", length = 9)
  private String nim;

  @NotEmpty(message = "Email harus diisi")
  @Column(name = "publisher_email", length = 100, nullable = false, unique = true)
  private String email;

  @ManyToMany(mappedBy = "publishers")
  // @JsonBackReference
  private Set<Publication> publications;

}
