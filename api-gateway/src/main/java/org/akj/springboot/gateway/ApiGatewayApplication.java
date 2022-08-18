package org.akj.springboot.gateway;

import org.akj.springboot.gateway.config.ReactiveLoadBalancerConfig;
import org.akj.springboot.gateway.loadbalancer.ReactiveRoundRobinLoadBalancer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

@SpringBootApplication(scanBasePackages = "org.akj.springboot")
@EnableDiscoveryClient
@LoadBalancerClients(defaultConfiguration = ReactiveLoadBalancerConfig.class)
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
