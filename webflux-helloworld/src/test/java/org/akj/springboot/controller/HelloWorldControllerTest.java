package org.akj.springboot.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = HelloWorldController.class)
class HelloWorldControllerTest {

    private WebTestClient webClient;

    @BeforeEach
    void initClient() {
        webClient = WebTestClient.bindToController(new HelloWorldController()).build();
    }

    @Test
    void hello() {
        String responseBody = webClient.get()
                .uri("/v1/hello")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertTrue(responseBody.contains("hello world"));
    }
}