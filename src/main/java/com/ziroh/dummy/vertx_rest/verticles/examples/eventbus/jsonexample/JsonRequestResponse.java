package com.ziroh.dummy.vertx_rest.verticles.examples.eventbus.jsonexample;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonRequestResponse extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  // Better to keep these as separate files
  // Static classes for example visibility purposes
  static class RequestVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(RequestVerticle.class.getName());
    static final String ADDRESS = "event.bus.example";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      // Can send strings, vertx/custom objects through the event bus
      final JsonObject message = new JsonObject()
        .put("message", "EVENT BUS: Hello!!")
        .put("version", 1);
      log.debug("SENDING REQUEST MESSAGE: {}", message);
      eventBus.<JsonArray>request(ADDRESS, message,
        reply -> log.debug("RESPONSE: {}", reply.result().body()));
    }
  }

  static class ResponseVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(ResponseVerticle.class.getName());
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<JsonObject>consumer(RequestVerticle.ADDRESS, message -> {
        log.debug("RECEIVED MESSAGE: {}", message.body());
        message.reply(new JsonArray().add("one").add("two").add("three"));
      });
    }
  }
}
