package org.akj.springboot.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("spring.doc.enabled")
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
                        .license(new License().name("apache-2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .contact(new Contact().name("Jamie Zhang").email("akjamie.zhang@outlook.com").url("https://akjamie.github.io"))
                        .termsOfService("it-meta.space@2022")
                        .description(springDocProperties.getDescription()));
    }
}
