package com.ziroh.dummy.vertx_rest.verticles.examples.workers;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(Worker.class);

  public static void main(String[] args) {
    final Vertx vertx = Vertx.vertx();
//    vertx.deployVerticle(new EventLoop());
    vertx.deployVerticle(Worker.class.getName(),
      new DeploymentOptions()
        .setInstances(1)
    );
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new WorkerVerticle(),
      new DeploymentOptions()
        .setWorker(true)
        .setWorkerPoolSize(1)
        .setWorkerPoolName("Worker-Example-Pool")
    );
    startPromise.complete();
    executeBlockingCode();
  }

  private void executeBlockingCode() {
    // Schedules code in worker thread pool
    // OK to do blocking calls here
    // Blocking handler runs in worker thread
    vertx.executeBlocking(event -> {
      log.debug("EXECUTING BLOCKING CODE: {}", Worker.class.getName());
      try {
        Thread.sleep(500);
//        event.fail("FORCED FAILURE");
        event.complete();
      } catch (InterruptedException e) {
        log.error("FAILED: {}", e);
        event.fail(e);
      }
      // 2nd handler runs after blocking handler
      // Runs in Event Loop thread
    }, result -> {
        // Check for event success/fail
        if (result.succeeded()){
          log.debug("BLOCKING CALL SUCCEEDED");
        } else{
          log.debug("BLOCKING CALL FAILED: ", result.cause());
        }
      });
  }
}
