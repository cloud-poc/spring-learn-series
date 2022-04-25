package org.akj.springboot.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication(scanBasePackages = "org.akj.springboot")
@EnableReactiveMongoAuditing(auditorAwareRef = "mongoDBAuditorAware")
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
