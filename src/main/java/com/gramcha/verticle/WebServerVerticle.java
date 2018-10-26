package com.gramcha.verticle;

import com.gramcha.config.ApplicationConfiguration;
import com.gramcha.controller.ApplicationRouter;
import io.vertx.core.AbstractVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by gramachandran on 26/10/18.
 */
@Component
public class WebServerVerticle extends AbstractVerticle {

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    private ApplicationRouter applicationRouter;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.createHttpServer().requestHandler(applicationRouter.getRouter(vertx)::accept).listen(applicationConfiguration.getApplicationPort());
    }


}
