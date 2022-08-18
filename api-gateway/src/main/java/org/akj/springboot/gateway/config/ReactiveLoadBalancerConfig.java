package org.akj.springboot.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.gateway.loadbalancer.ReactiveRoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

@Slf4j
public class ReactiveLoadBalancerConfig {
    private static final int REACTIVE_ROUNDROBIN_LOAD_BALANCE_ORDER = 193827468;

    @Bean
    @Primary
    @Order(REACTIVE_ROUNDROBIN_LOAD_BALANCE_ORDER)
    public ReactiveRoundRobinLoadBalancer gatewayRoundRobinLoadBalancer(Environment environment,
                                                                        LoadBalancerClientFactory loadBalancerClientFactory) {
        log.debug("Creating custom ReactiveRoundRobinLoadBalancer.");
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new ReactiveRoundRobinLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }

}
