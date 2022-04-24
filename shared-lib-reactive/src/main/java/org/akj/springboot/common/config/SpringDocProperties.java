package org.akj.springboot.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.doc")
@Data
public class SpringDocProperties {
    private boolean enabled;

    private String serviceName;

    private String description;

    private String version;

    private String basePath;
}
