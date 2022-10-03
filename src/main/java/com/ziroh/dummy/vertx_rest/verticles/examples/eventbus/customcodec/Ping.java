package com.ziroh.dummy.vertx_rest.verticles.examples.eventbus.customcodec;

public class Ping {
  private String message;
  private Boolean enabled;

  public Ping(){
    // Default Constructor
  }

  public Ping(String message, Boolean enabled) {
    this.message = message;
    this.enabled = enabled;
  }

  public String getMessage() {
    return message;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  @Override
  public String toString() {
    return "Ping{" +
      "message='" + message + '\'' +
      ", enabled=" + enabled +
      '}';
  }
}
