package org.akj.springboot.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public ResponseEntity hello() {
        var headers = new HttpHeaders();

        int indicator = new Random().nextInt(100);
        if (indicator % 2 == 0) throw new IllegalStateException("Simulated Exception raised");

        return ResponseEntity.accepted().headers(headers).body("hello,  mates");
    }

}
