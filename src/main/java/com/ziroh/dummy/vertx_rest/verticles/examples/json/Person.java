package com.ziroh.dummy.vertx_rest.verticles.examples.json;

public class Person {
  private Integer id;
  private String name;
  private Boolean vertxUse;

  public Person() {
    // Default Constructor for Jaackson
  }

  public Person(Integer id, String name, Boolean vertxUse) {
    this.id = id;
    this.name = name;
    this.vertxUse = vertxUse;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getVertxUse() {
    return vertxUse;
  }

  public void setVertxUse(Boolean vertxUse) {
    this.vertxUse = vertxUse;
  }
}

