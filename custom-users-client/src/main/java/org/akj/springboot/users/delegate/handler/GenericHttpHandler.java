package org.akj.springboot.users.delegate.handler;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.users.delegate.bean.MethodMetaData;
import org.akj.springboot.users.delegate.bean.ServerInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Slf4j
public class GenericHttpHandler implements HttpHandler {
    private ServerInfo serverInfo;
    private WebClient client;

    public GenericHttpHandler(ServerInfo serverInfo) {
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
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        String baseUrl = serverInfo.getUrl();
        String serviceUrl = methodMetaData.getUrl();

        //prepare the path variable
        if (serviceUrl.contains("{") && serviceUrl.contains("}")) {
            String[] urls = baseUrl.split("/");
            List<String> list = new ArrayList<>(urls.length);
            for (String url : urls) {
                if (url.contains("{") && url.contains("}")) {
                    String placeHolder = url.substring(url.indexOf("{"), url.lastIndexOf("}" + 1));
                    url = url.replace(placeHolder, (String) methodMetaData.getParams()
                            .get(placeHolder.substring(1, placeHolder.length() - 1)));
                    list.add(url);
                    continue;
                }
                list.add(url);
            }
            serviceUrl = Strings.join(list, '/');
        }

        switch (methodMetaData.getMethod().name()) {
            case "GET":
                // prepare the request url & parameters
                StringBuilder builder = new StringBuilder("");
                if (serviceUrl.startsWith("/")) serviceUrl = serviceUrl.substring(1, serviceUrl.length());
                builder.append(baseUrl).append("/").append(serviceUrl);
                Map<String, Object> params = methodMetaData.getParams();
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


                // prepare request
                HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(url)).setHeader("Content-type", "application/json");
                // handle http headers
                Optional.ofNullable(methodMetaData.getHeaders()).orElse(new HashMap<>()).entrySet().stream()
                        .forEach(item -> requestBuilder.header(item.getKey(), item.getValue()));
                HttpRequest request = requestBuilder.build();
                try {
                    HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300) {
                        return new JsonMapper().readValue(httpResponse.body(), methodMetaData.getReturnType());
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                break;
            default:
                throw new IllegalStateException("unknown request method received.");

        }
        return null;
    }
}
