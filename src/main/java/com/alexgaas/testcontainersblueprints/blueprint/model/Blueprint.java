package com.alexgaas.testcontainersblueprints.blueprint.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BLUEPRINT")
@Entity
public class Blueprint {
  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "NAME")
  private String name;

  public Blueprint() {}

  public Blueprint(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}
