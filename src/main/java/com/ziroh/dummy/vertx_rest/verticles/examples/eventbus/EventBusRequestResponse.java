package com.ziroh.dummy.vertx_rest.verticles.examples.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventBusRequestResponse extends AbstractVerticle {
  private static final Logger log = LogManager.getLogger(EventBusRequestResponse.class.getName());
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  // Better to keep these as separate files
  // Static classes for example visibility purposes
  static class RequestVerticle extends AbstractVerticle {
//    private static final Logger log = LoggerFactory.getLogger(RequestVerticle.class.getName());
//private static final Logger log = LogManager.getLogger(RequestVerticle.class.getName());
    static final String ADDRESS = "event.bus.example";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      // Can send strings, vertx/custom objects through the event bus
      final String message = "EVENT BUS: Hello!!";
      log.debug("SENDING REQUEST MESSAGE: {}", message);
      eventBus.<String>request(ADDRESS, message, reply -> log.debug("RESPONSE: {}", reply.result().body()));
    }
  }

  static class ResponseVerticle extends AbstractVerticle {
//    private static final Logger log = LoggerFactory.getLogger(ResponseVerticle.class.getName());
//private static final Logger log = LogManager.getLogger(ResponseVerticle.class.getName());
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(RequestVerticle.ADDRESS, message -> {
        log.debug("RECEIVED MESSAGE: {}", message.body());
        message.reply("RECEIVED MESSAGE :*");
      });
    }
  }
}
