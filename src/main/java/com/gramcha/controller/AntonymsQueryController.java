package com.gramcha.controller;

import com.gramcha.config.ApplicationConfiguration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

/**
 * Created by gramachandran on 26/10/18.
 */
@Component
public class AntonymsQueryController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    public Router getRouter(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/antonyms/:word").handler(this::antonyms);
        router.get("/info").handler(this::info);
        return router;
    }

    public void antonyms(RoutingContext routingContext){
        String word = routingContext.request().getParam("word");
        HttpServerResponse response = routingContext.response();
        response
                .putHeader("Content-Type", "text")
                .end(word);
    }
    public void info(RoutingContext routingContext){

        HttpServerResponse response = routingContext.response();
        response
                .putHeader("Content-Type", "application/json")
                .end(Json.encodePrettily(discoveryClient.getInstances(applicationConfiguration.getApplicationName())));
    }

}
