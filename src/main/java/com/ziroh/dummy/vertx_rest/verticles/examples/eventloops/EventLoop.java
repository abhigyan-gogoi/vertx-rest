package com.ziroh.dummy.vertx_rest.verticles.examples.eventloops;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoop extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(EventLoop.class);

  public static void main(String[] args) {
    final Vertx vertx = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
        // Use more event loop pool size than verticle instances
        .setEventLoopPoolSize(4)
    );
//    vertx.deployVerticle(new EventLoop());
    vertx.deployVerticle(EventLoop.class.getName(),
      new DeploymentOptions()
        .setInstances(3)
    );
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("STARTED: {}", getClass().getName());
    // DO NOT DO: blocking operations inside a verticle
    Thread.sleep(5000);
  }
}
