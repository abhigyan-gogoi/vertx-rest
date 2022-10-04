package com.ziroh.dummy.vertx_rest;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class FuturePromiseVerticle {

  private static final Logger log = LoggerFactory.getLogger(FuturePromiseVerticle.class);

  @Test
  void promise_success(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    log.debug("START: ");
    vertx.setTimer(500, id -> {
      promise.complete("SUCCESS");
      log.debug("SUCCESS: ");
      context.completeNow();
    });
    log.debug("END");
  }

  @Test
  void promise_failure(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    log.debug("START: ");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("FAILED!!"));
      log.debug("FAILED: ");
      context.completeNow();
    });
    log.debug("END");
  }

  @Test
  void future_success (Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    log.debug("START: ");
    vertx.setTimer(500, id -> {
      promise.complete("SUCCESS");
      log.debug("TIMER DONE: ");
      context.completeNow();
    });
    final Future<String> future = promise.future();
    future.onSuccess(result -> {
      log.debug("RESULT: {}", result);
      context.completeNow();
    })
      .onFailure(context::failNow);
  }
}
