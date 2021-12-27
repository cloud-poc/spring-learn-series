package org.akj.springboot.controller;

import org.akj.springboot.annotation.AutoIgnore;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AutoIgnore(value = "dummy")
public class HelloWorldController {

    @GetMapping("/hello")
    public ResponseEntity hello() {
        var headers = new HttpHeaders();
        headers.add("Responded", "HelloWorldController");
        return ResponseEntity.accepted().headers(headers).body("hello,  mates");
    }

}
