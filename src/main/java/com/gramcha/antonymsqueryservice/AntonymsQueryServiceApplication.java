package com.gramcha.antonymsqueryservice;

import com.gramcha.verticle.WebServerVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient     // Register this service as uerka client to eureka server
@ComponentScan(basePackages = "com.gramcha.*")
public class AntonymsQueryServiceApplication {

    @Autowired
    private WebServerVerticle webServerVerticle;

    public static void main(String[] args) {
        SpringApplication.run(AntonymsQueryServiceApplication.class, args);
    }

    @PostConstruct
    public void startWebServer() {
        Vertx.vertx().deployVerticle(webServerVerticle);
    }
}
