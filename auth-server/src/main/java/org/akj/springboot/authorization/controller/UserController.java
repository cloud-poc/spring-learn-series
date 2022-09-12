package org.akj.springboot.authorization.controller;

import org.akj.springboot.authorization.domain.entity.User;
import org.akj.springboot.authorization.mapper.UserMapstructMapper;
import org.akj.springboot.authorization.service.UserService;
import org.akj.springboot.authorization.vo.UserRegistrationRequest;
import org.akj.springboot.authorization.vo.UserRegistrationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    private final UserMapstructMapper userMapstructMapper;

    public UserController(UserService userService, UserMapstructMapper userMapstructMapper) {
        this.userService = userService;
        this.userMapstructMapper = userMapstructMapper;
    }

    @PostMapping
    public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest){
        User user = userMapstructMapper.convert(userRegistrationRequest);
        user = userService.add(user);

        return userMapstructMapper.convert(user);
    }
}
