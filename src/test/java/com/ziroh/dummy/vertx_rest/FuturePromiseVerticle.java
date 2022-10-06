package com.ziroh.dummy.vertx_rest;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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
    });
    final Future<String> future = promise.future();
    future.onSuccess(result -> {
      log.debug("RESULT: {}", result);
      context.completeNow();
    })
      .onFailure(context::failNow);
  }

  @Test
  void future_failure (Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    log.debug("START: ");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("FAILED!!"));
      log.debug("TIMER DONE: ");
    });
    final Future<String> future = promise.future();
    future.onSuccess(context::failNow)
      .onFailure(error -> {
        log.debug("RESULT: ", error);
        context.completeNow();
      });
  }

  @Test
  void future_map (Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    log.debug("START: ");
    vertx.setTimer(500, id -> {
      promise.complete("SUCCESS");
      log.debug("TIMER DONE: ");
      context.completeNow();
    });
    final Future<String> future = promise.future();
    future
      .map(asString -> {
        log.debug("Map string to JSON object");
        return new JsonObject().put("key", asString);
      })
      .map(jsonObject -> new JsonArray().add(jsonObject))
      .onSuccess(result -> {
        log.debug("RESULT: {} of type {}", result, result.getClass().getSimpleName());
        context.completeNow();
      })
      .onFailure(context::failNow);
  }

  @Test
  void future_coordination (Vertx vertx, VertxTestContext context) {
    vertx.createHttpServer()
      .requestHandler(request -> log.debug("{}", request))
      .listen(10_000)
      .compose(server -> {
        log.debug("Another task");
        return Future.succeededFuture(server);
      })
      .compose(server -> {
        log.debug("Even another task");
        return Future.succeededFuture(server);
      })
      .onFailure(context::failNow)
      .onSuccess(server -> {
        log.debug("Server started on port {}", server.actualPort());
        context.completeNow();
      });
  }

  @Test
  void future_composition (Vertx vertx, VertxTestContext context) {
    var one = Promise.<Void>promise();
    var two = Promise.<Void>promise();
    var three = Promise.<Void>promise();

    var futureOne = one.future();
    var futureTwo = two.future();
    var futureThree = three.future();

    // All - requires all futures to succeed
    // Any - requires any one future to succeed
    // Join - before reporting result; all futures have to be returned (like all)
    CompositeFuture.all(futureOne, futureTwo, futureThree)
      .onFailure(context::failNow)
      .onSuccess(result -> {
        log.debug("SUCCESS: ");
        context.completeNow();
      });

    // Complete futures
    vertx.setTimer(500, id -> {
      one.complete();
      two.complete();
//      three.fail("THREE FAILED!!!");
      three.complete();
    });
  }
}
