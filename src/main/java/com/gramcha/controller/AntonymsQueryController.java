package com.gramcha.controller;

import com.gramcha.config.ApplicationConfiguration;
import com.gramcha.services.DatamuseClientService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by gramachandran on 26/10/18.
 */
@Component
public class AntonymsQueryController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    private DatamuseClientService datamuseClientService;


    public void antonyms(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String word = routingContext.request().getParam("word");
        Future<String> futureResult = datamuseClientService.getAntonyms(word);
        futureResult.setHandler(result -> {
            if (result.succeeded()) {
                response.end(result.result());
            } else {
                routingContext.fail(result.cause());
            }
        });
//        response
//                .end(word);
    }

    public void info(RoutingContext routingContext) {

        HttpServerResponse response = routingContext.response();
        response
                .putHeader("Content-Type", "application/json")
                .end(Json.encodePrettily(discoveryClient.getInstances(applicationConfiguration.getApplicationName())));
    }

}
