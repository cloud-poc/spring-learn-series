package org.akj.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
@EnableDiscoveryClient
public class UserServiceRouterFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceRouterFunctionApplication.class, args);
	}

}
