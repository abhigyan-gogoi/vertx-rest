package com.ziroh.dummy.vertx_rest.verticles.examples.workers;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(Worker.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("DEPLOYED WORKER VERTICLE: {}", WorkerVerticle.class.getName());
    startPromise.complete();
    Thread.sleep(1000);
    log.debug("BLOCKING DONE: {}", WorkerVerticle.class.getName());
  }
}
