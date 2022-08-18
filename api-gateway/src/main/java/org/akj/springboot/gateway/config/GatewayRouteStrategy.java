package org.akj.springboot.gateway.config;

import lombok.Getter;

@Getter
public enum GatewayRouteStrategy {
    DEFAULT("default"),
    X_API_VERSION("X-API-VERSION");
    private String strategy;

    GatewayRouteStrategy(String strategy){
        this.strategy = strategy;
    }

}
