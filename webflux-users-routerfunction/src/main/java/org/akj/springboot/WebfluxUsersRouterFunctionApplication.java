package org.akj.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class WebfluxUsersRouterFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxUsersRouterFunctionApplication.class, args);
	}

}
