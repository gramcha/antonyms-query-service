package com.gramcha.verticle;

import com.gramcha.config.ApplicationConfiguration;
import com.gramcha.controller.AntonymsQueryController;
import com.gramcha.controller.ApplicationRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

/**
 * Created by gramachandran on 26/10/18.
 */
@Component
public class WebServerVerticle extends AbstractVerticle {

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ApplicationRouter applicationRouter;

    @Override
    public void start() throws Exception {
        super.start();

        vertx.createHttpServer().requestHandler(applicationRouter.getRouter(vertx)::accept).listen(applicationConfiguration.getApplicationPort());
//        vertx.createHttpServer().requestHandler(router()::accept).listen(applicationConfiguration.getApplicationPort());
    }

//    private Router router() {
//        Router router = Router.router(vertx);
//
//        router.route("/info").handler(routingContext -> {
//
//            HttpServerResponse response = routingContext.response();
//
//            response.putHeader("Content-Type", "application/json");
//            response.end(Json.encodePrettily(discoveryClient.getInstances(applicationConfiguration.getApplicationName())));
//        });
//
//        return router;
//    }
}
