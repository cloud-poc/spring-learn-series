package org.akj.springboot.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("springdoc.api-docs.enabled")
public class OpenApiConfig {

    final SpringDocProperties springDocProperties;

    public OpenApiConfig(SpringDocProperties springDocProperties) {
        this.springDocProperties = springDocProperties;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components())
                .info(new Info().title(springDocProperties.getServiceName())
                        .version(springDocProperties.getVersion())
                        .license(new License().name(springDocProperties.getLicenseName()).url(springDocProperties.getLicenseUrl()))
                        .contact(new Contact().name(springDocProperties.getContactName()).email(springDocProperties.getContactEmail())
                                .url(springDocProperties.getContactUrl()))
                        .termsOfService(springDocProperties.getTermsOfService())
                        .description(springDocProperties.getDescription()));
    }
}
