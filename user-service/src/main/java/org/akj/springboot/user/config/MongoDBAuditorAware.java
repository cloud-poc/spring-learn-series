package org.akj.springboot.user.config;

import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MongoDBAuditorAware implements ReactiveAuditorAware<String> {
    @Override
    public Mono<String> getCurrentAuditor() {
        return Mono.just("system");
    }
}
