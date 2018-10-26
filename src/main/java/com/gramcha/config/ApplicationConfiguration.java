package com.gramcha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by gramachandran on 26/10/18.
 */
@Configuration
public class ApplicationConfiguration {
    @Autowired
    private Environment environment;

    public String getApplicationName() {
        return environment.getProperty("spring.application.name");
    }

    public int getApplicationPort() {
        return environment.getProperty("server.port", Integer.class);
    }
}
