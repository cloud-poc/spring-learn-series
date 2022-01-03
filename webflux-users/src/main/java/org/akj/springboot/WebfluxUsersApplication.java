package org.akj.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing(auditorAwareRef = "mongoDBAuditorAware")
//@EnableDiscoveryClient
public class WebfluxUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxUsersApplication.class, args);
    }

}
