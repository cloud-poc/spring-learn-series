package org.akj.springboot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureWebTestClient
class UserServiceRouterFunctionApplicationIntegrationTest extends Specification {
    @Autowired
    WebTestClient webTestClient

    def "validate user-service-v2 started and health status is UP"() {
        expect:
        webTestClient.get()
                .uri(URI.create("/actuator/health"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/vnd.spring-boot.actuator.v3+json")
                .expectBodyList(Map<String, String>).contains(Map.of("status", "UP"));
    }
}
