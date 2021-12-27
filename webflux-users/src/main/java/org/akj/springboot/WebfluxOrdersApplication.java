package org.akj.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing(auditorAwareRef = "mongoDBAuditorAware")
public class WebfluxOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxOrdersApplication.class, args);
    }

}
