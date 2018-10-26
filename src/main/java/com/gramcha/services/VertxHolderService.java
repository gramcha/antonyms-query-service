package com.gramcha.services;

import io.vertx.core.Vertx;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by gramachandran on 26/10/18.
 */
@Service
public class VertxHolderService {
    private Vertx vertx = null;

    public void init(Vertx vertxCore) {
        if (vertx == null)
            vertx = vertxCore;
    }

    public Vertx getVertx() {
        return vertx;
    }
}
