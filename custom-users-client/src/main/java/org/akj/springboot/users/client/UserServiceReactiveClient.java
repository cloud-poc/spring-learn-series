package org.akj.springboot.users.client;

import org.akj.springboot.rest.delegate.annotation.ReactiveWebClient;
import org.akj.springboot.rest.delegate.annotation.WebClient;
import org.akj.springboot.users.domain.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@ReactiveWebClient(url = "http://localhost:9001")
public interface UserServiceReactiveClient {

    @GetMapping("/v2/users")
    Flux<User> getAllUsers(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("userName") String userName);

    @PostMapping("/v2/users")
    Mono<User> addUser(@RequestBody Mono<User> user);

    @PutMapping("/v2/users/{uid}")
    Mono<User> updateUser(@PathVariable String uid, @RequestBody Mono<User> user);

    @GetMapping("/v2/users/{uid}")
    Mono<User> getUserDetails(@PathVariable String uid);

    @DeleteMapping("/v2/users/{uid}")
    Mono<Void> deleteUser(@PathVariable String uid);
}
