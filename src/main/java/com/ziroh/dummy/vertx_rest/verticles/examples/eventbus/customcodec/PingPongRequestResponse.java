package com.ziroh.dummy.vertx_rest.verticles.examples.eventbus.customcodec;

import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingPongRequestResponse extends AbstractVerticle {
  private static final Logger log = LogManager.getLogger(PingPongRequestResponse.class.getName());
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle(), getAsyncLogOnErrorHandler());
    vertx.deployVerticle(new PongVerticle(), getAsyncLogOnErrorHandler());
  }

  private static Handler<AsyncResult<String>> getAsyncLogOnErrorHandler() {
    return ar -> {
      if (ar.failed()) {
        log.error("ERROR: ", ar.cause());
      }
    };
  }

  // Better to keep these as separate files
  // Static classes for example visibility purposes
  static class PingVerticle extends AbstractVerticle {
//    private static final Logger log = LoggerFactory.getLogger(RequestVerticle.class.getName());
//private static final Logger log = LogManager.getLogger(RequestVerticle.class.getName());
    static final String ADDRESS = PingVerticle.class.getName();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      // Can send strings, vertx/custom objects through the event bus
      final Ping message = new Ping("Ping", true);
      log.debug("SENDING REQUEST MESSAGE: {}", message);
      // Register only once; Preferably in the same verticle and near usage
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      eventBus.<Pong>request(ADDRESS, message, reply -> {
        if (reply.failed()){
          log.error("FAILED: ", reply.cause());
          return;
        }
        log.debug("RESPONSE: {}", reply.result().body());
      });
      startPromise.complete();
    }
  }

  static class PongVerticle extends AbstractVerticle {
//    private static final Logger log = LoggerFactory.getLogger(ResponseVerticle.class.getName());
//private static final Logger log = LogManager.getLogger(ResponseVerticle.class.getName());
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      // Register only once; Preferably in the same verticle and near usage
      eventBus.registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
      eventBus.<Ping>consumer(PingVerticle.ADDRESS, message -> {
        log.debug("RECEIVED MESSAGE: {}", message.body());
        message.reply(new Pong(0));
      }).exceptionHandler(error ->
        log.error("ERROR: ", error));
      startPromise.complete();
    }
  }
}
