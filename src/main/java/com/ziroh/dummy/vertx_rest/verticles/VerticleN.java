package com.ziroh.dummy.vertx_rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("STARTED: "+getClass().getName()+
      "\n\tTHREAD: "+Thread.currentThread().getName()+
      "\n\tCONFIG: "+config().toString());
    startPromise.complete();
  }
}
