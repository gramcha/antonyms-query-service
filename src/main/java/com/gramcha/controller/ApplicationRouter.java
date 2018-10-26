package com.gramcha.controller;

import com.gramcha.config.ApplicationConfiguration;
import com.gramcha.services.VertxHolderService;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by gramachandran on 26/10/18.
 */
@Component
public class ApplicationRouter {

    @Autowired
    private AntonymsQueryController antonymsQueryController;

    @Autowired
    private VertxHolderService vertxHolderService;

    public Router getRouter(Vertx vertx) {
        vertxHolderService.init(vertx);
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/antonyms/:word").handler(antonymsQueryController::antonyms);
        router.get("/info").handler(antonymsQueryController::info);
        return router;
    }
}
