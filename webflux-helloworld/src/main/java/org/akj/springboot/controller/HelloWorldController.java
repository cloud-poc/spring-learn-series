package org.akj.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/v1")
@Slf4j
public class HelloWorldController {

    @GetMapping("/hello")
    public Mono<String> hello() throws UnknownHostException {
        return Mono.just("hello world from " + InetAddress.getLocalHost().getHostAddress());
    }

    @GetMapping(value = "/hello-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> helloFlux() {
        Flux<String> rsp = Flux.fromStream(IntStream.range(1, 50).mapToObj(i -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }

            return "stream-" + i;
        }));

        return rsp;
    }
}
