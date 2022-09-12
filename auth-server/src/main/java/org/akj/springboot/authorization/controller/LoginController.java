package org.akj.springboot.authorization.controller;

import org.akj.springboot.authorization.service.UserService;
import org.akj.springboot.authorization.vo.UserLoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vi/auth/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity login(@Valid @RequestBody UserLoginRequest userLoginRequest) {

    }
}
