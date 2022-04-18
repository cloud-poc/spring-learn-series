package org.akj.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.akj.springboot.domain.User;
import org.akj.springboot.domain.UserStatus;
import org.akj.springboot.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("${spring.application.name:''}/v1")
//@CrossOrigin
//@CrossOrigin(origins = "http://localhost:10000", maxAge = 3600)
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    @Operation(summary = "retrieve all users")
    public Flux<User> getAll(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "userName", required = false) String userName) {
        if (Strings.isBlank(userName)) {
            validate(pageNo, pageSize);
            Pageable page = PageRequest.of(pageNo, pageSize).withSort(Sort.Direction.ASC, "userName");
            return this.userRepository.findAllUserWithPagination(page);
        } else {
            return this.userRepository.findByUserName(userName);
        }
    }

    private void validate(Integer pageNo, Integer pageSize) {
        Assert.notNull(pageNo, "pageNo must not be empty.");
        Assert.notNull(pageSize, "pageSize must not be empty.");

        Assert.isTrue(pageNo >= 0, "the range of pageNo is [0,]");
        Assert.isTrue(pageSize >= 0 && pageSize <= 1000, "the range of pageNo is [0, 1000]");
    }

    @Operation(summary = "get user details")
    @GetMapping("/users/{uid}")
    public Mono<User> getUserDetails(@Valid @NotEmpty @PathVariable("uid") String userId) {

        return this.userRepository.findById(userId);
    }

    @Operation(summary = "register user")
    @PostMapping("/users")
    public Mono<User> addUser(@Valid @RequestBody User user) {
        // in case of unexpected data change
        user.setId(null);
        return this.userRepository.save(user);
    }

    @Operation(summary = "delete user")
    @DeleteMapping("/users/{uid}")
    public Mono<ResponseEntity<Void>> removeUser(@PathVariable("uid") String userId) {
        return this.userRepository.findById(userId).flatMap(user -> {
            user.setUserStatus(UserStatus.INACTIVE);
            return this.userRepository.save(user).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "update user")
    @PutMapping("/users/{uid}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("uid") String userId,
                                                 @Valid @RequestBody Mono<User> user) {
        return this.userRepository.findById(userId)
                .flatMap(u -> {
                    return user.map(uo -> {
                        uo.setId(u.getId());
                        uo.setCreateBy(u.getCreateBy());
                        uo.setCreateDate(u.getCreateDate());
                        uo.setLastModifiedBy(u.getLastModifiedBy());
                        uo.setLastModifiedDate(u.getLastModifiedDate());
                        return uo;
                    });
                }).flatMap(u -> {
                    return this.userRepository.save(u);
                })
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
