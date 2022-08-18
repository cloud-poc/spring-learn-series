package org.akj.springboot.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    private static final String DEFAULT_ALLOW_ORIGINS = "*";

    private static final String DEFAULT_ALLOW_HEADERS = "*";
    private static final List<String> DEFAULT_ALLOW_METHODS = List.of("GET", "POST");
    @Value("${cors.access-control-allow-origins:}#{T(java.util.Collections).emptyList()}")
    List<String> allowedOrigins;

    @Value("${cors.access-control-allow-origin-pattern:*}")
    String allowedOriginPattern;
    @Value("${cors.access-control-allow-methods:}#{T(java.util.Collections).emptyList()}")
    List<String> allowedMethods;

    @Value("${cors.access-control-allow_headers:}#{T(java.util.Collections).emptyList()}")
    List<String> allowedHeaders;

    @Value("${cors.access-control-allow_credentials:false}")
    boolean allowCredentials;
    private long maxAge = 3600;

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setMaxAge(maxAge);
        corsConfig.setAllowedMethods(CollectionUtils.isEmpty(allowedMethods) ? DEFAULT_ALLOW_METHODS : allowedMethods);
        corsConfig.setAllowCredentials(allowCredentials);

        if (allowCredentials) {
            corsConfig.addAllowedOriginPattern(allowedOriginPattern);
        } else {
            corsConfig.setAllowedOrigins(CollectionUtils.isEmpty(allowedOrigins) ? Arrays.asList(DEFAULT_ALLOW_ORIGINS) : allowedOrigins);
        }

        corsConfig.setAllowedHeaders(CollectionUtils.isEmpty(allowedHeaders) ? Arrays.asList(DEFAULT_ALLOW_HEADERS) : allowedHeaders);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

}
