package com.ziroh.dummy.vertx_rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("STARTED: {}"+getClass().getName());
    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      // This is an Async callback
      log.debug("DEPLOYED: {}"+ VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });
    startPromise.complete();
  }
}
