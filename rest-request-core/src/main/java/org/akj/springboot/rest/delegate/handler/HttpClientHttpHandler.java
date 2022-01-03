package org.akj.springboot.rest.delegate.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.rest.delegate.bean.MethodMetaData;
import org.akj.springboot.rest.delegate.bean.ServerInfo;
import org.akj.springboot.rest.delegate.exception.DataAccessException;
import org.akj.springboot.rest.delegate.exception.ResourceAccessException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

@Slf4j
public class HttpClientHttpHandler implements HttpHandler {
    private ServerInfo serverInfo;
    private WebClient client;
    private ObjectMapper mapper;

    public HttpClientHttpHandler(ServerInfo serverInfo, ObjectMapper mapper) {
        this.serverInfo = serverInfo;
        this.mapper = mapper;
        this.initWebClient();
    }

    private void initWebClient() {
        String url = serverInfo.getUrl();
        log.info("init the WebClient, server baseUrl: {}", url);
        client = WebClient.create(url);
    }

    @Override
    public Object invoke(MethodMetaData methodMetaData) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        String baseUrl = serverInfo.getUrl();
        String serviceUrl = methodMetaData.getUrl();

        serviceUrl = prepareHttpPathVariables(methodMetaData, serviceUrl);
        String url = prepareRequestUrl(methodMetaData, baseUrl, serviceUrl);

        log.info("start to execute request..");

        switch (methodMetaData.getMethod().name()) {
            case "GET":
                return doGet(methodMetaData, client, url);
            case "POST":
                return doPost(methodMetaData, client, url);
            case "PUT":
                return doPut(methodMetaData, client, url);
            case "DELETE":
                return doDelete(methodMetaData, client, url);
            default:
                throw new IllegalStateException("unknown request method received.");
        }
    }

    private Object doDelete(MethodMetaData methodMetaData, HttpClient client, String url) {
        HttpRequest.Builder requestBuilder = null;

        requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url)).setHeader("Content-type", "application/json").DELETE();
        HttpRequest httpRequest = requestBuilder.build();

        return doRequest(methodMetaData, client, httpRequest);
    }

    private Object doRequest(MethodMetaData methodMetaData, HttpClient client, HttpRequest httpRequest) {
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300) {
                if (methodMetaData.getReturnType().isAssignableFrom(void.class)) {
                    return true;
                }
                if (methodMetaData.getReturnType().isAssignableFrom(List.class) &&
                        methodMetaData.getActualReturnType() != null) {
                    return mapper.readValue(httpResponse.body(), TypeFactory.defaultInstance()
                            .constructCollectionType((Class<? extends Collection>) methodMetaData.getReturnType(),
                                    methodMetaData.getActualReturnType()));
                }
                return mapper.readValue(httpResponse.body(), methodMetaData.getReturnType());
            } else {
                throw new DataAccessException(httpResponse.body());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ResourceAccessException(e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new ResourceAccessException(e);
        }
    }

    private Object doPut(MethodMetaData methodMetaData, HttpClient client, String url) {
        HttpRequest.Builder requestBuilder = null;
        try {
            requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url)).setHeader("Content-type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(methodMetaData.getBody())));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e);
        }

        return doRequest(methodMetaData, client, requestBuilder.build());
    }

    @SneakyThrows
    private Object doPost(MethodMetaData methodMetaData, HttpClient client, String url) {
        HttpRequest.Builder requestBuilder = null;
        try {
            requestBuilder = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(methodMetaData.getBody())))
                    .uri(URI.create(url)).setHeader("Content-type", "application/json");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e);
        }
        HttpRequest request = requestBuilder.build();

        return doRequest(methodMetaData, client, request);
    }

    private String prepareHttpPathVariables(MethodMetaData methodMetaData, String serviceUrl) {
        //prepare the path variable
        if (serviceUrl.contains("{") && serviceUrl.contains("}")) {
            String[] urls = serviceUrl.split("/");
            List<String> list = new ArrayList<>(urls.length);
            for (String url : urls) {
                if (url.contains("{") && url.contains("}")) {
                    String placeHolder = url.substring(url.indexOf("{"), url.lastIndexOf("}") + 1);
                    url = url.replace(placeHolder, (String) methodMetaData.getPathVariables()
                            .get(placeHolder.substring(1, placeHolder.length() - 1)));
                    list.add(url);
                    continue;
                }
                list.add(url);
            }
            serviceUrl = Strings.join(list, '/');
        }
        return serviceUrl;
    }

    private Object doGet(MethodMetaData methodMetaData, HttpClient client, String url) {
        // prepare request
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url)).setHeader("Content-type", "application/json");
        prepareHttpHeaders(methodMetaData, requestBuilder);

        HttpRequest request = requestBuilder.build();
        return doRequest(methodMetaData, client, request);
    }

    private void prepareHttpHeaders(MethodMetaData methodMetaData, HttpRequest.Builder requestBuilder) {
        // handle http headers
        Optional.ofNullable(methodMetaData.getHeaders()).orElse(new HashMap<>()).entrySet().stream()
                .forEach(item -> requestBuilder.header(item.getKey(), item.getValue()));
    }

    private String prepareRequestUrl(MethodMetaData methodMetaData, String baseUrl, String serviceUrl) {
        // prepare the request url & parameters
        StringBuilder builder = new StringBuilder("");
        if (serviceUrl.startsWith("/")) serviceUrl = serviceUrl.substring(1, serviceUrl.length());
        builder.append(baseUrl).append("/").append(serviceUrl);
        Map<String, String> params = methodMetaData.getParams();
        if (params != null && params.size() > 0) {
            builder.append("?");
            params.entrySet().stream().filter(i -> i.getValue() != null).forEach(i -> {
                builder.append(i.getKey()).append("=")
                        .append(i.getValue()).append("&");
            });
        }
        String url = builder.toString();
        if (url.endsWith("&")) url = url.substring(0, url.length() - 1);
        log.info("request url: {}", url);
        return url;
    }
}
