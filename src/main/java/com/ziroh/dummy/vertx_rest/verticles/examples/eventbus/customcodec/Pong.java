package com.ziroh.dummy.vertx_rest.verticles.examples.eventbus.customcodec;

public class Pong {
  private Integer id;

  public Pong(){
    // Default Constructor
  }

  public Pong(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Pong{" +
      "id=" + id +
      '}';
  }
}
