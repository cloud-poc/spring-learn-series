package org.akj.springboot.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

//@Configuration
public class WebfluxConfigure implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .maxAge(3l)
                .allowedOrigins("POST", "GET", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}
