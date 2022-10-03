package com.ziroh.dummy.vertx_rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleA extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("STARTED: "+getClass().getName());
    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      // This is an Async callback
      System.out.println("DEPLOYED: "+ VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });
    startPromise.complete();
  }
}
