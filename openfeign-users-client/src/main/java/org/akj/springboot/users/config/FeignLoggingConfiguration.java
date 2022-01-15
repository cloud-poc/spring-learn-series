package org.akj.springboot.users.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLoggingConfiguration {

    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

}
