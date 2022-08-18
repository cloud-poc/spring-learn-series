package org.akj.springboot.gateway.loadbalancer;

import io.micrometer.core.instrument.util.StringUtils;
import org.akj.springboot.gateway.config.GatewayRouteStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ReactiveRoundRobinLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    private static final Log log = LogFactory.getLog(RoundRobinLoadBalancer.class);
    public static final String EUREKA_METADATA_API_VERSIONS = "versions";
    final AtomicInteger position;

    final String serviceId;

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public ReactiveRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
    }

    public ReactiveRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId, int seedPosition) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
    }

    @SuppressWarnings("rawtypes")
    @Override
    // see original
    // https://github.com/Netflix/ocelli/blob/master/ocelli-core/
    // src/main/java/netflix/ocelli/loadbalancer/RoundRobinLoadBalancer.java
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);

        String apiVersion = ((DefaultRequest<RequestDataContext>) request).getContext().getClientRequest().getHeaders().getFirst(GatewayRouteStrategy.X_API_VERSION.getStrategy());
        // handler for api version
        if (StringUtils.isNotBlank(apiVersion)) {
            return supplier.get(request).next()
                    .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, apiVersion));
        }

        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, null));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances, String filter) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, filter);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, String filter) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }

        ServiceInstance instance = null;
        if (StringUtils.isNotBlank(filter)) {
            while (true) {
                instance = choose(instances);
                if (instance.getMetadata().containsKey(EUREKA_METADATA_API_VERSIONS)
                        && instance.getMetadata().get(EUREKA_METADATA_API_VERSIONS).equalsIgnoreCase(filter)) {
                    break;
                }
            }
            return new DefaultResponse(instance);
        }

        return new DefaultResponse(instance = choose(instances));
    }

    private ServiceInstance choose(List<ServiceInstance> instances) {
        int pos = Math.abs(this.position.incrementAndGet());
        ServiceInstance instance = instances.get(pos % instances.size());

        return instance;
    }
}
