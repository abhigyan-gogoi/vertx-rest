package com.ziroh.dummy.vertx_rest;

import com.ziroh.dummy.vertx_rest.server.Server;
import io.vertx.core.Vertx;

public class Main {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Server());
  }
}
