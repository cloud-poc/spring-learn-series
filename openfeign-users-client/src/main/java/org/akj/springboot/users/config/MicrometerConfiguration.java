package org.akj.springboot.users.config;

import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MicrometerConfiguration {

    @Bean
    public MicrometerCapability micrometerCapacity(MeterRegistry meterRegistry) {
        return new MicrometerCapability(meterRegistry);
    }
}
