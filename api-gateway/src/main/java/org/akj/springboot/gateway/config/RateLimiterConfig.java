package org.akj.springboot.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {
    @Bean
    KeyResolver userKeyResolver() {
        return new RemoteAddressKeyResolver();
    }
}
