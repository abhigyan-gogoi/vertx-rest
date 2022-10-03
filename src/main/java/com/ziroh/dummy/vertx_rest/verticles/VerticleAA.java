package com.ziroh.dummy.vertx_rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleAA extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("STARTED: "+getClass().getName());
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    log.debug("STOPPED: {}"+getClass().getName());
    stopPromise.complete();
  }
}
