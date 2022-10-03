package com.ziroh.dummy.vertx_rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("STARTED: "+getClass().getName()+" on thread "+Thread.currentThread().getName());
    startPromise.complete();
  }
}
