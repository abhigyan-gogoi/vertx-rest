package com.ziroh.dummy.vertx_rest.verticles.examples.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusPublishSubscriber extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Publisher());
    vertx.deployVerticle(new SubscriberOne());
    vertx.deployVerticle(SubscriberTwo.class.getName(), new DeploymentOptions()
      .setInstances(2));
  }

  public static class Publisher extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(1000, id -> vertx.eventBus()
        .publish(Publisher.class.getName(), "Message for subscribers"));
    }
  }

  public static class SubscriberOne extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(SubscriberOne.class.getName());
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus()
        .<String>consumer(Publisher.class.getName(), message -> log.debug("RECEIVED: {}", message.body()));
    }
  }

  public static class SubscriberTwo extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(SubscriberTwo.class.getName());
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus()
        .<String>consumer(Publisher.class.getName(), message -> log.debug("RECEIVED: {}", message.body()));
    }
  }
}
