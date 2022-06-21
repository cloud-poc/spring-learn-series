package org.akj.springboot.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "springdoc.info")
@Data
public class SpringDocProperties {
    private String serviceName;

    private String description;

    private String version;

    private String licenseName;

    private String licenseUrl;

    private String contactName;

    private String contactEmail;

    private String contactUrl;

    private String termsOfService;
}
