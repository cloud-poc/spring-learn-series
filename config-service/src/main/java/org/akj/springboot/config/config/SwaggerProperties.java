package org.akj.springboot.config.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.swagger")
public class SwaggerProperties {
    private Boolean enabled;
    private String groupName;
    private String basePackage;
    private String version;
    private String title;
    private String description;
    private String contactName;
    private String contactEmail;
    private String contactUrl;

}
