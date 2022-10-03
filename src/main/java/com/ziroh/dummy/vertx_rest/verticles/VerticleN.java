package com.ziroh.dummy.vertx_rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleN extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("STARTED: {}"+getClass().getName()+
      "\n\tTHREAD: {}"+Thread.currentThread().getName()+
      "\n\tCONFIG: {}"+config().toString());
    startPromise.complete();
  }
}
