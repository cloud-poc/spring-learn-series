package org.akj.springboot.config.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfiguration {
    @Value("${spring.app.name:}")
    private String appName;

    @Value("${spring.app.version:}")
    private String appVersion;

    private SwaggerProperties swaggerProperties;

    public SwaggerConfiguration(SwaggerProperties swaggerProperties){
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket adminApi() {
        // OAS_30：OpenAPI Specification -  OAS
        return new Docket(
                DocumentationType.OAS_30)
                .enable(swaggerProperties.getEnabled())
                .apiInfo(getApiInfo())
                .groupName(swaggerProperties.getGroupName())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(appName)
                .description(swaggerProperties.getDescription())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(appVersion)
                .build();
    }

}
