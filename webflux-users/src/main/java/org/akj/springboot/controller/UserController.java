package org.akj.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.akj.springboot.domain.User;
import org.akj.springboot.domain.UserStatus;
import org.akj.springboot.repository.UserRepository;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    @Operation(deprecated = true, summary = "retrieve all users")
    public Flux<User> getAll(@Valid @Range(min = 0) @RequestParam("pageNo") int pageNo,
                             @Valid @Range(min = 1, max = 1000) @RequestParam("pageSize") int pageSize) {
        Pageable page = PageRequest.of(pageNo, pageSize).withSort(Sort.Direction.ASC, "userName");
        return this.userRepository.findAllUserWithPagination(page);
    }

//    @Operation(summary = "retrieve user by userName")
//    @GetMapping("/users?userName={userName}")
//    public Mono<User> findUserByUserName(@Valid @NotNull @RequestParam("userName") String userName) {
//
//
//        return this.userRepository.findByUserName(userName);
//    }

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
                                                 @Valid @RequestBody User user) {
        return this.userRepository.findById(userId)
                .flatMap(u -> {
                    user.setId(userId);
                    user.setLastModifiedDate(u.getLastModifiedDate());
                    user.setCreateDate(u.getCreateDate());
                    user.setCreateBy(u.getCreateBy());
                    user.setLastModifiedBy(u.getLastModifiedBy());
                    return this.userRepository.save(user);
                })
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
