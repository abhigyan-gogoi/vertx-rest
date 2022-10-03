package com.ziroh.dummy.vertx_rest.verticles.examples.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusPoint2Point extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }

  static class Sender extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      // Send message every second
      vertx.setPeriodic(1000, id -> vertx.eventBus()
        .send(Sender.class.getName(), "Message body"));
    }
  }

  static class Receiver extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Sender.class.getName(),
        message -> {
          log.debug("RECEIVED MESSAGE: {}", message.body());
//          System.out.println("rando");
      });
    }
  }
}
