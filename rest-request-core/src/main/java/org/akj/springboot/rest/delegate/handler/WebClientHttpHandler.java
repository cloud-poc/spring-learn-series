package org.akj.springboot.rest.delegate.handler;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.rest.delegate.bean.MethodMetaData;
import org.akj.springboot.rest.delegate.bean.ReactiveMethodMetaData;
import org.akj.springboot.rest.delegate.bean.ServerInfo;
import org.akj.springboot.rest.delegate.exception.DataAccessException;
import org.akj.springboot.rest.delegate.exception.ResourceAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class WebClientHttpHandler implements HttpHandler {
    private ServerInfo serverInfo;
    private WebClient client;

    public WebClientHttpHandler(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        this.initWebClient();
    }

    private void initWebClient() {
        String url = serverInfo.getUrl();
        log.info("init the WebClient, server baseUrl: {}", url);
        client = WebClient.create(url);
    }

    @Override
    public Object invoke(MethodMetaData methodMetaData) {
        Assert.isTrue(methodMetaData instanceof ReactiveMethodMetaData, "methodMetaData is not in reactive mode");
        ReactiveMethodMetaData reactiveMethodMetaData = (ReactiveMethodMetaData) methodMetaData;

        MultiValueMap<String, String> queryParamMap = new LinkedMultiValueMap<>();
        reactiveMethodMetaData.getParams().entrySet()
                .forEach(s -> queryParamMap.put(s.getKey(), List.of(String.valueOf(s.getValue()))));

        WebClient.RequestBodySpec request = client.method(HttpMethod.valueOf(reactiveMethodMetaData.getMethod().name()))
                .uri(uriBuilder -> uriBuilder.path(reactiveMethodMetaData.getUrl()).queryParams(queryParamMap)
                        .build(reactiveMethodMetaData.getPathVariables()))
                .accept(MediaType.APPLICATION_JSON);

        if (reactiveMethodMetaData.getReactiveBody() != null) {
            Assert.notNull(reactiveMethodMetaData.getReactiveBodyType(), "reactive request body class type is empty");
            request.body(reactiveMethodMetaData.getReactiveBody(), reactiveMethodMetaData.getReactiveBodyType());
        }
        WebClient.ResponseSpec response = request
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.METHOD_NOT_ALLOWED.value(), clientResponse -> {
                    log.error(clientResponse.toString());
                    return Mono.error(new ResourceAccessException(clientResponse.statusCode().value() + " -> Method is " +
                            "not allowed, please check the URL"));
                }).onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(), clientResponse -> {
                    log.error(clientResponse.toString());
                    return Mono.error(new ResourceAccessException(clientResponse.statusCode().value() + " -> Resource is " +
                            "not found, please check the URL"));
                })
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.error(clientResponse.toString());
                    return Mono.error(new DataAccessException(clientResponse.statusCode().value() + " -> Resource is " +
                            "not found, please check the URL"));
                });

        response.onStatus(HttpStatus::is2xxSuccessful, rsp -> {
            if (reactiveMethodMetaData.isFlux()) {
                rsp.bodyToFlux(reactiveMethodMetaData.getActualReturnType());
            } else {
                rsp.bodyToMono(reactiveMethodMetaData.getActualReturnType());
            }

            return null;
        });
        return null;
    }
}

