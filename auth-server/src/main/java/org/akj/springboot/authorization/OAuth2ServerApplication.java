package org.akj.springboot.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Springboot application - Entry point
 * @author Jamie Y L Zhang
 */

@SpringBootApplication(scanBasePackages = "org.akj.springboot")
@EnableDiscoveryClient
@EnableAuthorizationServer
@EnableJpaAuditing
public class OAuth2ServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OAuth2ServerApplication.class, args);
	}
	
}
